package Class;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Member extends User {    
    public Member() {
        super("", "", "member");
    }

    public Member(String username, String password) {
        super(username, password, "member");
    }

    public static void viewRecommendation(List<Attraction> attractions) {    
        Iterator<Attraction> it = attractions.iterator();

        while (it.hasNext()) {
            System.out.printf(" - %s\n", it.next().getName());
        }     
    }
    
    public static void updateHistory(List<SearchHistory> searchHistories, int indexFound, State searchState) {
        List<State> states = searchHistories.get(indexFound).getStates();

        // Find the index of the existing state with the same name
        int foundIndex = -1;
        for (int i = 0; i < states.size(); i++) {
            if (states.get(i).name().equals(searchState.name())) {
                foundIndex = i;
                break;
            }
        }

        // If not found or already at the front, do nothing
        if (foundIndex == -1 || foundIndex == 0) {
            return;
        }

        // Remove from current position and add to front
        State state = states.remove(foundIndex);
        states.add(0, state);
        searchHistories.get(indexFound).setStates((ArrayList<State>) states);

        // Only write file if we actually modified the list
        File.overwriteSearchHistoryFile(searchHistories);
    }
    
    public static void viewRoute(List<String> route) {
        Iterator<String> it = route.iterator();
        
        while (it.hasNext()) {
            System.out.print(it.next());
            if (it.hasNext()) {
                System.out.print(" -> ");
            }
        }
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        
        Member member = (Member)obj;
        return this.getUsername().equals(member.getUsername());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.getUsername());
    }
}