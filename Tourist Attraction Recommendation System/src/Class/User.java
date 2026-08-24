package Class;

public abstract class User {
    
    // Attributes
    private String username;
    private String password;
    private String role;

    // Constructors
    public User() {
        this.username = "";
        this.password = "";
        this.role = "";
    }

    // Parameterised constructor
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Accessor and Mutator methods
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return getUsername() + "," + getPassword() + "," + getRole();
    }
}