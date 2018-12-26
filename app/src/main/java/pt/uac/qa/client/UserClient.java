package pt.uac.qa.client;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import pt.uac.qa.model.User;

/**
 * Created by Patrício Cordeiro <patricio.cordeiro@gmail.com> on 11-12-2018.
 */
public final class UserClient extends AuthenticatedClient {
    public UserClient(final Context context) {
        super(context);
    }

    public User getUserInfo() throws ClientException {
        try {
            final HttpResponse response = httpClient.get("/users/me");
            validateResponse(response);
            return User.fromJson(new JSONObject(response.getContent()));
        } catch (JSONException | IOException e) {
            throw new ClientException(e);
        }
    }
}
