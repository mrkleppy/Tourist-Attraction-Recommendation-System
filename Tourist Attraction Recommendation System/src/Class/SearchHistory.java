package Class;

public class SearchHistory {
    private Member member; 
    private State[] states;

    public SearchHistory() {
        this.member = null;
        this.states = null;
    }

    public SearchHistory(Member member, State[] states) {
        this.member = member;
        this.states = states;
    }

    public Member getMember() {
        return member;
    }
 
    public void setMember(Member member) {
        this.member = member;
    }
    
    public State[] getStates() {
        return states;
    }
    
    public void setStates(State[] states) {
        this.states = states;
    }
}

