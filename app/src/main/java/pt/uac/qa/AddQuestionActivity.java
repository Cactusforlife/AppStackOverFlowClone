package pt.uac.qa;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pt.uac.qa.client.ClientException;
import pt.uac.qa.client.QuestionClient;
import pt.uac.qa.model.Question;

public class AddQuestionActivity extends AppCompatActivity {

    private Question question;
    private EditText addTitle;
    private EditText addBody;
    private EditText addTags;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        addTitle=findViewById(R.id.add_title);
        addBody=findViewById(R.id.add_body);
        addTags=findViewById(R.id.add_tags);
        add=findViewById(R.id.button_add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddQuestionActivity.AddQuestionTask().execute();
            }
        });
    }


    @SuppressLint("StaticFieldLeak")
    private final class AddQuestionTask extends AsyncTask<Void, Void, Question> {

        @Override
        protected Question doInBackground(Void... voids) {
            try {
                final QuestionClient questionClient = new QuestionClient(AddQuestionActivity.this);
                final JSONObject jsonObject = new JSONObject();

                jsonObject.put("title", addTitle.getText().toString());
                jsonObject.put("body", addBody.getText().toString());

                String s =  addTags.getText().toString();
                String[] words = s.split(",");

                JSONArray jsonArray = new JSONArray(words);
                jsonObject.put("tags",jsonArray );

                return questionClient.addQuestion(jsonObject);
            } catch (ClientException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        protected void onPostExecute(final Question result) {


            if (result == null) {
                Toast.makeText(AddQuestionActivity.this, "Error Adding this question for the server", Toast.LENGTH_LONG).show();
                return;
            }



            Intent intent = new Intent(AddQuestionActivity.this, QuestionsFragment.class);
            startActivityForResult(intent,0);
        }

    }
}
