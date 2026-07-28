public class SearchHistory {
    private Member member; 

    public SearchHistory() {
        this.member = null;
    }

    public SearchHistory(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
 
    public void setMember(Member member) {
        this.member = member;
    }
}

