package pt.uac.qa.client;

import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by Patrício Cordeiro <patricio.cordeiro@gmail.com> on 12-12-2018.
 */
public final class QuestionFilter {
    private String tag;
    private String pattern;
    private int page = 1;               // default to first page
    private int rowsPerPage = 12;       // default to 12 rows per page

    public String getTag() {
        return tag;
    }

    public QuestionFilter setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getPattern() {
        return pattern;
    }

    public QuestionFilter setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public Integer getPage() {
        return page;
    }

    public QuestionFilter setPage(Integer page) {
        this.page = page;
        return this;
    }

    public Integer getRowsPerPage() {
        return rowsPerPage;
    }

    public QuestionFilter setRowsPerPage(Integer rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
        return this;
    }

    String toQueryString() {
        final Uri.Builder builder = new Uri.Builder();

        if (!TextUtils.isEmpty(tag))
            builder.appendQueryParameter("tag", tag);

        if (!TextUtils.isEmpty(pattern))
            builder.appendQueryParameter("pattern", pattern);

        builder.appendQueryParameter("page", Integer.toString(page));
        builder.appendQueryParameter("rows", Integer.toString(rowsPerPage));

        return builder.build().getQuery();
    }
}
