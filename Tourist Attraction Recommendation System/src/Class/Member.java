public class Member extends User {
    private SearchHistory searchHistory;

    public Member() {
        super("", "");
        this.searchHistory = null;
    }

    public Member(String username, String password, SearchHistory searchHistory) {
        super(username, password);
        this.searchHistory = searchHistory;
    }

    public SearchHistory getSearchHistory() {
        return searchHistory;
    }

    public void setSearchHistory(SearchHistory searchHistory) {
        this.searchHistory = searchHistory;
    }
}