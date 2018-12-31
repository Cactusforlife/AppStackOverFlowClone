package pt.uac.qa;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import pt.uac.qa.client.ClientException;
import pt.uac.qa.client.QuestionClient;

import pt.uac.qa.model.Question;


public class QuestionDisplay extends AppCompatActivity {


    private TextView titleView;
    private TextView bodyView;
    private TextView tagsView;
    private TextView userView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_display);

        titleView = findViewById(R.id.text_view_title);
        bodyView = findViewById(R.id.text_view_body);
        tagsView = findViewById(R.id.text_view_tags);
        userView = findViewById(R.id.text_view_user);

        new LoadQuestionTask().execute();

    }


    @SuppressLint("StaticFieldLeak")
    private final class LoadQuestionTask extends AsyncTask<Void, Void, Question> {
        @Override
        protected Question doInBackground(Void... voids) {
            try {
                final QuestionClient questionClient = new QuestionClient(QuestionDisplay.this);
                return questionClient.getQuestion(getIntent().getStringExtra(QuestionsFragment.QUESTIONID));
            } catch (ClientException e) {
                e.printStackTrace();
                return null;
            }
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        protected void onPostExecute(final Question result) {


            if (result == null) {
                Toast.makeText(QuestionDisplay.this, "Error fetching the questions from the server", Toast.LENGTH_LONG).show();
                return;
            }

            Question question = result;


            titleView.setText(question.getTitle());
            bodyView.setText(question.getBody());
            StringBuilder questionTags = new StringBuilder();

            tagsView.setText(questionTags.toString());
            userView.setText(question.getUser().getName());



        }
    }

}