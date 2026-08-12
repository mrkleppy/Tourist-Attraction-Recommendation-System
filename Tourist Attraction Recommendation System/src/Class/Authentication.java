package Class;

import java.util.List;

public class Authentication {
    public static boolean validateLogin(List<User> users, String inputUsername, String inputPassword, String role) {
        for (User user : users) {
            if (user.getUsername().equals(inputUsername) &&
                user.getPassword().equals(inputPassword) &&
                user.getRole().equalsIgnoreCase(role)) {
                return true;
            }
        }
        return false;
    }

    public static boolean registerMember(List<User> users, String inputUsername, String inputPassword) {
        if (usernameExists(users, inputUsername)) {
            return false; // Username already exists
        }

        Member newMember = new Member(inputUsername, inputPassword);
        users.add(newMember);
        File.AppendCredentialFile(newMember); // Append to the credential file
        return true;
    }

    public static boolean usernameExists(List<User> users, String inputUsername) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(inputUsername)) {
                return true;
            }
        }

        return false;
    }
}
