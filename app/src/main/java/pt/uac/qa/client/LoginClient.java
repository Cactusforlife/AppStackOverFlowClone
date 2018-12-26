package pt.uac.qa.client;

import android.content.Context;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

import pt.uac.qa.QAApp;
import pt.uac.qa.model.AccessToken;

import static pt.uac.qa.client.HttpClient.MEDIA_TYPE_FORM;

/**
 * Created by Patrício Cordeiro <patricio.cordeiro@gmail.com> on 06-12-2018.
 */
public final class LoginClient {
    private static final String KEY_CLIENT_ID = "client_id";
    private static final String KEY_CLIENT_SECRET = "client_secret";

    private final HttpClient httpClient;
    private final String clientId;
    private final String clientSecret;

    public LoginClient(final Context context) {
        final QAApp app = (QAApp) context.getApplicationContext();
        this.httpClient = new HttpClient(context);
        this.clientId = app.getMetadata(KEY_CLIENT_ID);
        this.clientSecret = app.getMetadata(KEY_CLIENT_SECRET);
        setupClientAuthentication();
    }

    public AccessToken login(final String username, final String password) throws ClientException {
        try {
            // grant_type=password&scope=forum.usage+user.management&username=patricio.cordeiro%40gmail.com&password=123456
            final String passwordGrantType = String.format(
                    "grant_type=password&scope=forum.usage+user.management&username=%s&password=%s",
                    URLEncoder.encode(username, "UTF-8"),
                    URLEncoder.encode(password, "UTF-8"));

            final HttpResponse response = httpClient.post("/auth/access-token", passwordGrantType, MEDIA_TYPE_FORM);
            validateResponse(response);
            return AccessToken.fromJson(new JSONObject(response.getContent()));
        } catch (JSONException | IOException e) {
            throw new ClientException(e);
        }
    }

    public AccessToken refresh(final String refreshToken) throws ClientException {
        try {
            // grant_type=password&scope=forum.usage+user.management&username=patricio.cordeiro%40gmail.com&password=123456
            final String refreshTokenGrantType = String.format(
                    "grant_type=refresh_token&refresh_token=%s",
                    URLEncoder.encode(refreshToken, "UTF-8"));

            final HttpResponse response = httpClient.post("/auth/access-token", refreshTokenGrantType, MEDIA_TYPE_FORM);
            validateResponse(response);
            return AccessToken.fromJson(new JSONObject(response.getContent()));
        } catch (JSONException | IOException e) {
            throw new ClientException(e);
        }
    }

    private void setupClientAuthentication() {

        // Basic bW9iaWxlLmFwcDo1NDg0ZjBmNzRlNzJlZWI3OTY0NTA2YzA4ZjQyMzRkZA==
        final String basicAuthString = String.format("%s:%s", clientId, clientSecret);
        httpClient.addHeader("Authorization", "Basic " +
                Base64.encodeToString(basicAuthString.getBytes(), Base64.NO_WRAP));
    }

    private void validateResponse(final HttpResponse response) throws ClientException {
        final int statusCode = response.getStatusCode();

        if (response.getStatusCode() >= 400) {
            if (statusCode == 401) {
                throw new AuthorizationException(response.getContent());
            }

            throw new ClientException(response.getContent());
        }
    }
}
