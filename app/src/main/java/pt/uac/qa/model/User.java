package pt.uac.qa.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Patrício Cordeiro <patricio.cordeiro@gmail.com> on 11-12-2018.
 */
public class User {
    private String userId;
    private String name;
    private String email;

    public static User fromJson(final JSONObject jsonObject) throws JSONException {
        final User user = new User();

        user.setUserId(jsonObject.getString("userId"));
        user.setName(jsonObject.getString("name"));
        user.setEmail(jsonObject.getString("email"));

        return user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public JSONObject toJson() throws JSONException {
        final JSONObject jsonObject = new JSONObject();

        jsonObject.put("userId", userId);
        jsonObject.put("name", name);
        jsonObject.put("email", email);

        return jsonObject;
    }
}
