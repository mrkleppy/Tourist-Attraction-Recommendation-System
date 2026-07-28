package Class;

public class Member extends User {    
    public Member() {
        super("", "");
    }

    public Member(String username, String password, SearchHistory searchHistory) {
        super(username, password);
    }
}