package pt.uac.qa.model;

import android.annotation.SuppressLint;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Patrício Cordeiro <patricio.cordeiro@gmail.com> on 09-12-2018.
 */
public class Answer {

    private String answerId;
    private Question question;
    private User user;
    private String body;
    private Date datePublished;
    private Boolean correctAnswer;
    private int positiveVotes;
    private int negativeVotes;

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    public Boolean getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getPositiveVotes() {
        return positiveVotes;
    }

    public void setPositiveVotes(int positiveVotes) {
        this.positiveVotes = positiveVotes;
    }

    public int getNegativeVotes() {
        return negativeVotes;
    }

    public void setNegativeVotes(int negativeVotes) {
        this.negativeVotes = negativeVotes;
    }

    public static Answer fromJson(final JSONObject jsonObject) throws JSONException {

        @SuppressLint("SimpleDateFormat") final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Answer answer = new Answer();

        if(!jsonObject.isNull("answerId")) {
            answer.setAnswerId(jsonObject.getString("answerId"));
        }

        if(!jsonObject.isNull("question")){
            answer.setQuestion(Question.fromJson(jsonObject.getJSONObject("question")));
        }

        if(!jsonObject.isNull("user")){
            answer.setUser(User.fromJson(jsonObject.getJSONObject("user")));
        }

        if(!jsonObject.isNull("body")){
            answer.setBody(jsonObject.getString("body"));
        }


        try{
            answer.setDatePublished(dateFormat.parse(
                    jsonObject.getJSONObject("datePublished").getString("date")));
        } catch (ParseException e){
            throw new RuntimeException(e);
        }

        return answer;

    }

    public JSONObject toJson() {
        final JSONObject jsonObject = new JSONObject();

        // TODO: set the fields on the jsonObject object

        return jsonObject;
    }

    // TODO: create the necessary getters and setters
}
