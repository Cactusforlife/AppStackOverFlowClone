package pt.uac.qa.client;

import android.net.Uri;
import android.text.TextUtils;

import pt.uac.qa.model.Answer;

public class AnswerFilter {

    private String pattern;
    private int page = 1;               // default to first page
    private int rowsPerPage = 12;       // default to 12 rows per page


    public String getPattern() {
        return pattern;
    }

    public AnswerFilter setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public Integer getPage() {
        return page;
    }

    public AnswerFilter setPage(Integer page) {
        this.page = page;
        return this;
    }

    public Integer getRowsPerPage() {
        return rowsPerPage;
    }

    public AnswerFilter setRowsPerPage(Integer rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
        return this;
    }

    String toQueryString() {
        final Uri.Builder builder = new Uri.Builder();


        if (!TextUtils.isEmpty(pattern))
            builder.appendQueryParameter("pattern", pattern);

        builder.appendQueryParameter("page", Integer.toString(page));
        builder.appendQueryParameter("rows", Integer.toString(rowsPerPage));

        return builder.build().getQuery();
    }
}
