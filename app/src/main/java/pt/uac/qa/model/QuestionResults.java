package pt.uac.qa.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patrício Cordeiro <patricio.cordeiro@gmail.com> on 11-12-2018.
 */
public class QuestionResults {
    private int count;
    private boolean empty;
    private Attributes attributes;
    private List<Question> data;

    public static QuestionResults fromJson(final JSONObject jsonObject) throws JSONException {
        final QuestionResults result = new QuestionResults();
        final JSONArray jsonArray = jsonObject.getJSONArray("data");
        final List<Question> questions = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); ++i) {
            questions.add(Question.fromJson(jsonArray.getJSONObject(i)));
        }

        result.setData(questions);
        result.setCount(jsonObject.getInt("count"));
        result.setEmpty(jsonObject.getBoolean("isEmpty"));
        result.setAttributes(Attributes.fromJson(jsonObject.getJSONObject("attributes")));

        return result;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public List<Question> getData() {
        return data;
    }

    public void setData(List<Question> data) {
        this.data = data;
    }

    public static final class Attributes {
        private String tag;
        private String pattern;
        private Pagination pagination;
        private String page;
        private String rows;

        static Attributes fromJson(final JSONObject jsonObject) throws JSONException {
            final Attributes attributes = new Attributes();

            if (!jsonObject.isNull("tag"))
                attributes.setTag(jsonObject.getString("tag"));

            if (!jsonObject.isNull("pattern"))
                attributes.setPattern(jsonObject.getString("pattern"));

            if (!jsonObject.isNull("page"))
                attributes.setPage(jsonObject.getString("page"));

            if (!jsonObject.isNull("rows"))
                attributes.setRows(jsonObject.getString("rows"));

            attributes.setPagination(Pagination.fromJson(jsonObject.getJSONObject("pagination")));

            return attributes;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getRows() {
            return rows;
        }

        public void setRows(String rows) {
            this.rows = rows;
        }

        public Pagination getPagination() {
            return pagination;
        }

        public void setPagination(Pagination pagination) {
            this.pagination = pagination;
        }
    }

    public static final class Pagination {
        private int maxResults;
        private int firstResult;
        private int rows;
        private int rowsPerPage;
        private int currentPage;
        private int totalPages;

        static Pagination fromJson(final JSONObject jsonObject) throws JSONException {
            final Pagination pagination = new Pagination();

            pagination.setMaxResults(jsonObject.getInt("maxResults"));
            pagination.setFirstResult(jsonObject.getInt("firstResult"));
            pagination.setRows(jsonObject.getInt("rows"));
            pagination.setRowsPerPage(jsonObject.getInt("rowsPerPage"));
            pagination.setCurrentPage(jsonObject.getInt("currentPage"));
            pagination.setTotalPages(jsonObject.getInt("totalPages"));

            return pagination;
        }

        public int getMaxResults() {
            return maxResults;
        }

        public void setMaxResults(int maxResults) {
            this.maxResults = maxResults;
        }

        public int getFirstResult() {
            return firstResult;
        }

        public void setFirstResult(int firstResult) {
            this.firstResult = firstResult;
        }

        public int getRows() {
            return rows;
        }

        public void setRows(int rows) {
            this.rows = rows;
        }

        public int getRowsPerPage() {
            return rowsPerPage;
        }

        public void setRowsPerPage(int rowsPerPage) {
            this.rowsPerPage = rowsPerPage;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }
    }
}
