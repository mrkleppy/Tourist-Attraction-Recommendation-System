package Class;

public class SearchHistory {
    private Member member; 
    private State state;

    public SearchHistory() {
        this.member = null;
        this.state = null;
    }

    public SearchHistory(Member member, State state) {
        this.member = member;
        this.state = state;
    }

    public Member getMember() {
        return member;
    }
 
    public void setMember(Member member) {
        this.member = member;
    }
    
    public State getState() {
        return state;
    }
    
    public void setState(State state) {
        this.state = state;
    }
}

