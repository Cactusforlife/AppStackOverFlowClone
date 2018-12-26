package pt.uac.qa.client;

import android.content.Context;

import pt.uac.qa.QAApp;
import pt.uac.qa.model.AccessToken;

/**
 * Created by Patrício Cordeiro <patricio.cordeiro@gmail.com> on 11-12-2018.
 */
abstract class AuthenticatedClient {
    private final Context context;
    final HttpClient httpClient;

    AuthenticatedClient(final Context context) {
        this.context = context.getApplicationContext();
        this.httpClient = new HttpClient(context);
        httpClient.addHeader("Authorization",
                String.format("%s %s", getAccessToken().getTokenType(), getAccessToken().getAccessToken()));
    }

    void validateResponse(final HttpResponse response) throws ClientException {
        final int statusCode = response.getStatusCode();

        if (statusCode >= 400) {
            if (statusCode == 401) {
                refreshToken();
            } else {
                throw new ClientException(response.getContent());
            }
        }
    }

    private void refreshToken() throws ClientException {
        final QAApp app = (QAApp) context;
        final LoginClient loginClient = new LoginClient(app);
        final AccessToken token = loginClient.refresh(getAccessToken().getRefreshToken());

        app.setAccessToken(token);
    }

    private AccessToken getAccessToken() {
        return ((QAApp) context).getAccessToken();
    }
}
