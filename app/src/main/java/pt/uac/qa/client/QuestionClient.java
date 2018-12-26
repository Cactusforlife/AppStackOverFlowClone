package pt.uac.qa.client;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import pt.uac.qa.model.QuestionResults;

/**
 * Created by Patrício Cordeiro <patricio.cordeiro@gmail.com> on 11-12-2018.
 */
public class QuestionClient extends AuthenticatedClient {
    public QuestionClient(final Context context) {
        super(context);
    }

    public QuestionResults getQuestions() throws ClientException {
        return getQuestions(new QuestionFilter());
    }

    public QuestionResults getQuestions(final QuestionFilter filter) throws ClientException {
        try {
            final String queryString = filter.toQueryString();
            final String url = queryString != null
                    ? "/questions?" + queryString
                    : "/questions";

            final HttpResponse response = httpClient.get(url);
            validateResponse(response);
            return QuestionResults.fromJson(new JSONObject(response.getContent()));
        } catch (JSONException | IOException e) {
            throw new ClientException(e);
        }
    }
}
