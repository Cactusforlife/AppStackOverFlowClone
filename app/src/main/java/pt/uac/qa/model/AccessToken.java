package pt.uac.qa.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Patrício Cordeiro <patricio.cordeiro@gmail.com> on 06-12-2018.
 */
public class AccessToken {
    private String tokenType;
    private int expiresIn;
    private String accessToken;
    private String refreshToken;

    public static AccessToken fromJson(final JSONObject jsonObject) throws JSONException {
        final AccessToken accessToken = new AccessToken();

        accessToken.setTokenType(jsonObject.getString("token_type"));
        accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
        accessToken.setAccessToken(jsonObject.getString("access_token"));
        accessToken.setRefreshToken(jsonObject.getString("refresh_token"));

        return accessToken;
    }

    public JSONObject toJson() throws JSONException {
        final JSONObject jsonObject = new JSONObject();

        jsonObject.put("token_type", tokenType);
        jsonObject.put("expires_in", expiresIn);
        jsonObject.put("access_token", accessToken);
        jsonObject.put("refresh_token", refreshToken);

        return jsonObject;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
