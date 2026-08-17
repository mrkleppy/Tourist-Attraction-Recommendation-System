package Class;

import java.util.Objects;

public class Admin extends User {
    public Admin() {
        super("", "", "admin");
    }

    public Admin(String username, String password) {
        super(username, password, "admin");
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
        
        Admin admin = (Admin)obj;
        return this.getUsername().equals(admin.getUsername());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.getUsername());
    }
}