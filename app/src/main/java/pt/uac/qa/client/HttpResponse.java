package pt.uac.qa.client;

import java.util.List;
import java.util.Map;

/**
 * Created by Patrício Cordeiro <patricio.cordeiro@gmail.com> on 05-12-2018.
 */
public class HttpResponse {

    private final Map<String, List<String>> headers;
    private final int statusCode;
    private final String statusMessage;
    private final String content;
    private final String encoding;
    private final int contentLength;

    HttpResponse(
            Map<String, List<String>> headers,
            int statusCode,
            String statusMessage,
            String content,
            String encoding,
            int contentLength)
    {
        this.headers = headers;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.content = content;
        this.encoding = encoding;
        this.contentLength = contentLength;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public String getContent() {
        return content;
    }

    public String getEncoding() {
        return encoding;
    }

    public int getContentLength() {
        return contentLength;
    }
}
