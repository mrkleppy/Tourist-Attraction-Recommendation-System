package Class;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchHistory {
    private Member member; 
    private ArrayList<State> states;

    public SearchHistory() {
        this.member = null;
        this.states = null;
    }

    public SearchHistory(Member member, ArrayList<State> states) {
        this.member = member;
        this.states = states;
    }

    public Member getMember() {
        return member;
    }
 
    public void setMember(Member member) {
        this.member = member;
    }
    
    public ArrayList<State> getStates() {
        return states;
    }
    
    public void setStates(ArrayList<State> states) {
        this.states = states;
    }
    
    public static ArrayList<State> defaultStates() {
        ArrayList<State> defaultStates = new ArrayList<>();
        
        defaultStates.addAll(Arrays.asList(State.values()));
        
        return defaultStates;
    }
}

