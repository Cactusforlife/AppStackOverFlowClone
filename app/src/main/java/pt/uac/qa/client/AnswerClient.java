package pt.uac.qa.client;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import pt.uac.qa.model.AnswerResults;

/**
 * Created by Patrício Cordeiro <patricio.cordeiro@gmail.com> on 11-12-2018.
 */
public class AnswerClient extends AuthenticatedClient {
    public AnswerClient(final Context context) {
        super(context);
    }

    public AnswerResults getAnswers() throws ClientException {
        return getMyAnswers(new AnswerFilter());
    }

    public AnswerResults getMyAnswers(final AnswerFilter filter) throws ClientException {
        try {
            final String queryString = filter.toQueryString();
            final String url = queryString != null
                    ? "/my/answers?" + queryString
                    : "/my/answers";

            final HttpResponse response = httpClient.get(url);
            validateResponse(response);
            return AnswerResults.fromJson(new JSONObject(response.getContent()));
        } catch (JSONException | IOException e) {
            throw new ClientException(e);
        }
    }

}