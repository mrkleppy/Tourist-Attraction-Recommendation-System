package Class;

import java.util.Objects;
import java.util.List;
import java.util.Iterator;

public class Member extends User {    
    public Member() {
        super("", "");
    }

    public Member(String username, String password) {
        super(username, password);
    }
    
    public static void viewRecommendationByState(List<Attraction> attractions) {    
        Iterator<Attraction> it = attractions.iterator();

        while (it.hasNext()) {
            System.out.printf(" - %s\n", it.next().getName());
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