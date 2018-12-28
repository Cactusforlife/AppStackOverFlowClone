package pt.uac.qa;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pt.uac.qa.client.ClientException;
import pt.uac.qa.client.QuestionClient;
import pt.uac.qa.client.QuestionFilter;
import pt.uac.qa.model.Question;
import pt.uac.qa.model.QuestionResults;

/**
 * Created by Patrício Cordeiro <patricio.cordeiro@gmail.com> on 10-12-2018.
 */
public final class MyQuestionsFragment extends Fragment {
    private QuestionFilter questionFilter = new QuestionFilter();
    private View questionContainer;
    private ProgressBar progressBar;
    private View pagination;
    private TextView pageView;
    private QuestionAdapter questionAdapter;

        @Nullable
        @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_questions, null);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        final ListView listView = view.findViewById(R.id.list_view);
        final Button prevButton = view.findViewById(R.id.prev_button);
        final Button nextButton = view.findViewById(R.id.next_button);

        listView.setAdapter((questionAdapter = new QuestionAdapter(getActivity())));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Question q = (Question) questionAdapter.getItem(position);
                final Intent intent = new Intent(getActivity(), EditQuestionActivity.class);
                intent.putExtra("questionId", q.getQuestionId());

                startActivity(intent);

            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousPage();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });

        questionContainer = view.findViewById(R.id.question_container);
        progressBar = view.findViewById(R.id.progress_bar);
        pagination = view.findViewById(R.id.pagination);
        pageView = view.findViewById(R.id.page_text_view);

        loadData();
    }

    private void showProgress(final boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            questionContainer.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            questionContainer.setVisibility(View.VISIBLE);
        }
    }

    private void previousPage() {
        // TODO: implement this
    }

    private void nextPage() {
        // TODO: implment this
    }

    private void loadData() {
        new LoadQuestionsTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private final class LoadQuestionsTask extends AsyncTask<Void, Void, QuestionResults> {
        @Override
        protected void onPreExecute() {
            showProgress(true);
        }

        @Override
        protected QuestionResults doInBackground(Void... voids) {
            try {
                final QuestionClient questionClient = new QuestionClient(getActivity());
                return questionClient.getMyQuestions(questionFilter);
            } catch (ClientException e) {
                e.printStackTrace();
                return null;
            }
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        protected void onPostExecute(final QuestionResults result) {
            showProgress(false);

            if (result == null) {
                Toast.makeText(getActivity(), "Error fetching the questions from the server", Toast.LENGTH_LONG).show();
                return;
            }

            if (result.getAttributes().getPagination().getTotalPages() == 1) {
                pagination.setVisibility(View.GONE);
            } else {
                pageView.setText(
                        String.format(Locale.getDefault(), "%d / %d",
                                result.getAttributes().getPagination().getCurrentPage(),
                                result.getAttributes().getPagination().getTotalPages()));
            }


            questionAdapter.loadQuestions(result.getData());
        }
    }

    private static final class QuestionAdapter extends BaseAdapter {

        private final List<Question> questionList = new ArrayList<>();
        private final LayoutInflater inflater;

        QuestionAdapter(final Context context) {
            this.inflater = LayoutInflater.from(context);
        }

        public void loadQuestions(final List<Question> questions) {
            questionList.clear();
            questionList.addAll(questions);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return questionList.size();
        }

        @Override
        public Object getItem(int position) {
            return questionList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Question question = (Question) getItem(position);
            final ViewHolder holder;

            if(convertView == null)
            {
                convertView = inflater.inflate(R.layout.question_list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.displayQuestion(question);

            return convertView;


        }
    }

    private static final class ViewHolder {

        TextView titleView;
        TextView userView;
        TextView dateView;

        ViewHolder(final View convertView) {

            titleView = convertView.findViewById(R.id.question_title);
            userView = convertView.findViewById(R.id.user);
            dateView = convertView.findViewById(R.id.question_date);

        }

        void displayQuestion(final Question question) {


            titleView.setText(question.getTitle());
            userView.setText(question.getUser().getName());
            dateView.setText(question.getDatePublished().toString());

        }
    }
}
