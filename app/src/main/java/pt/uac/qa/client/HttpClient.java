package pt.uac.qa.client;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import pt.uac.qa.QAApp;

/**
 * Created by Patrício Cordeiro <patricio.cordeiro@gmail.com> on 05-12-2018.
 */
public final class HttpClient {
    public static final String MEDIA_TYPE_APP_JSON = "application/json";
    public static final String MEDIA_TYPE_FORM = "application/x-www-form-urlencoded;charset=utf-8";

    private static final String KEY_BASE_URL = "base_url";
    private final Map<String, Set<String>> headers = new HashMap<>();
    private final String baseUrl;

    public HttpClient(final Context context) {
        final QAApp app = (QAApp) context.getApplicationContext();
        this.baseUrl = app.getMetadata(KEY_BASE_URL);
    }

    @SuppressWarnings("ConstantConditions")
    public void addHeader(final String name, final String value) {
        if (!headers.containsKey(name)) {
            headers.put(name, new LinkedHashSet<String>());
        }

        headers.get(name).add(value);
    }

    public HttpResponse get(final String partialUrl) throws IOException {
        return executeRequest("GET", partialUrl, null, null);
    }

    public HttpResponse post(final String partialUrl) throws IOException {
        return post(partialUrl, null);
    }

    public HttpResponse post(final String partialUrl, final String body) throws IOException {
        return post(partialUrl, body, MEDIA_TYPE_APP_JSON);
    }

    public HttpResponse post(final String partialUrl, final String body, final String mediaType) throws IOException {
        return executeRequest("POST", partialUrl, body, mediaType);
    }

    public HttpResponse put(final String partialUrl) throws IOException {
        return put(partialUrl, null);
    }

    public HttpResponse put(final String partialUrl, final String body) throws IOException {
        return put(partialUrl, body, MEDIA_TYPE_APP_JSON);
    }

    public HttpResponse put(final String partialUrl, final String body, final String mediaType) throws IOException {
        return executeRequest("PUT", partialUrl, body, mediaType);
    }

    public HttpResponse delete(final String partialUrl) throws IOException {
        return executeRequest("DELETE", partialUrl, null, null);
    }

    private HttpResponse executeRequest(final String httpMethod, final String partialUrl, final String body, final String mediaType) throws IOException {
        final HttpURLConnection connection = connect(partialUrl);

        try {
            connection.setRequestMethod(httpMethod);
            writeHeaders(connection);
            writeContent(connection, body, mediaType);
            return getResponse(connection);
        } finally {
            disconnect(connection);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void writeHeaders(final HttpURLConnection connection) {
        for (final String name : headers.keySet()) {
            for (final String value : headers.get(name)) {
                connection.addRequestProperty(name, value);
            }
        }
    }

    private void writeContent(final HttpURLConnection connection, final String content, final String mediaType) throws IOException {
        if (content == null)
            return;

        final byte[] data = content.getBytes("UTF-8");

        connection.setDoOutput(true);
        connection.addRequestProperty("Content-Type", mediaType);

        try (final OutputStream out = connection.getOutputStream()) {
            out.write(data, 0, data.length);
        }
    }

    private HttpResponse getResponse(final HttpURLConnection connection) throws IOException {
        final int statusCode = connection.getResponseCode();
        final String content = statusCode >= 200 && statusCode < 400
                ? consumeStream(connection.getInputStream())
                : consumeStream(connection.getErrorStream());

        return new HttpResponse(
            connection.getHeaderFields(),
            statusCode,
            connection.getResponseMessage(),
            content,
            "UTF-8",
            content.length()
        );
    }

    private String consumeStream(final InputStream stream) throws IOException {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            final StringBuilder buffer = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            return buffer.toString();
        }
    }

    private HttpURLConnection connect(final String partialUrl) throws IOException {
        final String fullUrl = baseUrl.endsWith("/")
                ? baseUrl + (partialUrl.startsWith("/")
                                ? partialUrl.substring(1)
                                : partialUrl)
                : baseUrl + "/" + (partialUrl.startsWith("/")
                                ? partialUrl.substring(1)
                                : partialUrl);

        final URL url = new URL(fullUrl);
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("Host", url.getHost());
        return connection;
    }

    private void disconnect(final HttpURLConnection connection) {
        if (connection != null) {
            connection.disconnect();
        }
    }
}
