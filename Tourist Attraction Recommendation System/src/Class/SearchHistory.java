package Class;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchHistory {

    // Attributes
    private Member member; 
    private ArrayList<State> states;

    // Default constructor
    public SearchHistory() {
        this.member = null;
        this.states = null;
    }

    // Parameterised constructor
    public SearchHistory(Member member, ArrayList<State> states) {
        this.member = member;
        this.states = states;
    }

    // Accessor and Mutator methods
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
    
    // Generate the default list of states for a new member's search history
    public static ArrayList<State> defaultStates() {
        ArrayList<State> defaultStates = new ArrayList<>();
        
        defaultStates.addAll(Arrays.asList(State.values()));
        
        return defaultStates;
    }
}

