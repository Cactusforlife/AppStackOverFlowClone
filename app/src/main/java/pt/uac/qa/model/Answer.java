package pt.uac.qa.model;

import org.json.JSONObject;

/**
 * Created by Patrício Cordeiro <patricio.cordeiro@gmail.com> on 09-12-2018.
 */
public class Answer {
    // TODO: add necessary fields

    public static Answer fromJson() {
        final Answer answer = new Answer();

        // TODO: set the field on the answer object

        return answer;
    }

    public JSONObject toJson() {
        final JSONObject jsonObject = new JSONObject();

        // TODO: set the fields on the jsonObject object

        return jsonObject;
    }

    // TODO: create the necessary getters and setters
}
