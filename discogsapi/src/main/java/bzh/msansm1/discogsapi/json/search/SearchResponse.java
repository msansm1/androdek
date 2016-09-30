package bzh.msansm1.discogsapi.json.search;

import java.util.List;

/**
 * Created by ronan on 30/09/2016.
 */

public class SearchResponse {
    private Pagination pagination;
    private List<SearchResult> results;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<SearchResult> getResults() {
        return results;
    }

    public void setResults(List<SearchResult> results) {
        this.results = results;
    }
}
