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

import pt.uac.qa.client.AnswerClient;
import pt.uac.qa.client.AnswerFilter;
import pt.uac.qa.client.ClientException;
import pt.uac.qa.model.Answer;
import pt.uac.qa.model.AnswerResults;

/**
 * Created by Patrício Cordeiro <patricio.cordeiro@gmail.com> on 10-12-2018.
 */
public final class MyAnswersFragment extends Fragment {

    private AnswerFilter answerFilter = new AnswerFilter();
    private View answerContainer;
    private ProgressBar progressBar;
    private View pagination;
    private TextView pageView;
    private AnswerAdapter answerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_answers, null);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        final ListView listView = view.findViewById(R.id.list_view);
        final Button prevButton = view.findViewById(R.id.prev_button);
        final Button nextButton = view.findViewById(R.id.next_button);

        listView.setAdapter((answerAdapter = new AnswerAdapter(getActivity())));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Answer a = (Answer) answerAdapter.getItem(position);
                final Intent intent = new Intent(getActivity(), EditAnswerActivity.class);
                intent.putExtra("answerId", a.getAnswerId());

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

        answerContainer = view.findViewById(R.id.answer_container);
        progressBar = view.findViewById(R.id.progress_bar);
        pagination = view.findViewById(R.id.pagination);
        pageView = view.findViewById(R.id.page_text_view);

        loadData();
    }

    private void showProgress(final boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            answerContainer.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            answerContainer.setVisibility(View.VISIBLE);
        }
    }

    private void previousPage() {
        // TODO: implement this
    }

    private void nextPage() {
        // TODO: implment this
    }

    private void loadData() {
        new LoadAnswersTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private final class LoadAnswersTask extends AsyncTask<Void, Void, AnswerResults> {
        @Override
        protected void onPreExecute() { showProgress(true); }

        @Override
        protected AnswerResults doInBackground(Void... voids) {
            try {
                final AnswerClient answerClient = new AnswerClient(getActivity());
                return answerClient.getMyAnswers(answerFilter);
            } catch (ClientException e) {
                e.printStackTrace();
                return null;
            }
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        protected void onPostExecute(final AnswerResults result) {
            showProgress(false);

            if (result == null) {
                Toast.makeText(getActivity(), "Error fetching the answers from the server", Toast.LENGTH_LONG).show();
            }

            if (result.getAttributes().getPagination().getTotalPages() == 1) {
                pagination.setVisibility(View.GONE);
            } else {
                pageView.setText(
                        String.format(Locale.getDefault(), "%d / %d",
                                result.getAttributes().getPagination().getCurrentPage(),
                                result.getAttributes().getPagination().getTotalPages()));
            }

            answerAdapter.loadAnswers(result.getData());
        }
    }

    private static final class AnswerAdapter extends BaseAdapter {

        private final List<Answer> answerList = new ArrayList<>();
        private final LayoutInflater inflater;

        AnswerAdapter(final Context context) {
            this.inflater = LayoutInflater.from(context);
        }

        public void loadAnswers(final List<Answer> answers) {
            answerList.clear();
            answerList.addAll(answers);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return answerList.size();
        }

        @Override
        public Object getItem(int position) {
            return answerList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Answer answer = (Answer) getItem(position);
            final ViewHolder holder;

            if(convertView == null)
            {
                convertView = inflater.inflate(R.layout.answer_list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.displayAnswer(answer);

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

        void displayAnswer(final Answer answer) {


            titleView.setText(answer.getQuestion().getTitle());
            userView.setText(answer.getUser().getName());
            dateView.setText(answer.getDatePublished().toString());

        }
    }
}
