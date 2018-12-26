package pt.uac.qa.model;

import android.annotation.SuppressLint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Patrício Cordeiro <patricio.cordeiro@gmail.com> on 09-12-2018.
 */
public class Question {
    // TODO: add necessary fields

    private String questionId;
    private String title;
    private User user;
    private List<String> tags;
    private Date datePublished;


    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public static Question fromJson(final JSONObject jsonObject) throws JSONException {


        // TODO: set the fields on the question object
        @SuppressLint("SimpleDateFormat") final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Question question = new Question();

        question.setQuestionId(jsonObject.getString("questionId"));

        question.setTitle(jsonObject.getString("title"));

        question.setUser(User.fromJson(jsonObject.getJSONObject("user")));

        final JSONArray jsonTagArray = jsonObject.getJSONArray("tags");
        final List<String> tags = new ArrayList<>();

        for(int i = 0; i < jsonTagArray.length();i++){
            tags.add(jsonTagArray.getString(i));
        }


        question.setTags(tags);



        try{
            question.setDatePublished(dateFormat.parse(
                    jsonObject.getJSONObject("datePublished").getString("date")));
        } catch (ParseException e){
            throw new RuntimeException(e);
        }

        return question;
    }

    public JSONObject toJson() {
        final JSONObject jsonObject = new JSONObject();

        // TODO: set the fields on the jsonObject object

        return jsonObject;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
