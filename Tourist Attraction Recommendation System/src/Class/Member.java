package Class;

public class Member extends User {    
    public Member() {
        super("", "");
    }

    public Member(String username, String password) {
        super(username, password);
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
}