package Class;

import java.util.List;

public class Authentication { 

    // Attribute to store the current logged-in user
    private static String currentUser;

    // Accessors and Mutator methods
    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String currentUser) {
        Authentication.currentUser = currentUser;
    }
    
    // Validate login credentials for a given role (admin or member)
    public static boolean validateLogin(List<User> users, String inputUsername, String inputPassword, String role) {

        // Iterate through the list of users to find a match for the provided username, password, and role
        for (User user : users) {
            if (user.getUsername().equals(inputUsername) &&
                user.getPassword().equals(inputPassword) &&
                user.getRole().equalsIgnoreCase(role)) {
                
                // If a match is found, set the current user and return true
                currentUser = inputUsername;
                return true;
            }
        }
        return false;
    }

    // Register a new member with the provided username and password
    public static boolean registerMember(List<User> users, List<SearchHistory> searchHistories, String inputUsername, String inputPassword) {
        if (usernameExists(users, inputUsername)) {
            return false; // Username already exists (no duplicates, case sensitive)
        }

        // Builds a new member object and appends it to the users list and credential file
        Member newMember = new Member(inputUsername, inputPassword);
        users.add(newMember);
        File.appendCredentialFile(newMember); // Append to the credential file
        
        // Create a new search history for the newly registered member and append it to the search history file
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setMember(newMember);
        searchHistory.setStates(SearchHistory.defaultStates());
        searchHistories.add(searchHistory);
        File.appendSearchHistoryFile(searchHistory);
        
        return true;
    }

    // Check if a username already exists in the list of users
    public static boolean usernameExists(List<User> users, String inputUsername) {

        // Iterate through the list of users to check for an existing username (case insensitive)
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(inputUsername)) {
                return true;
            }
        }

        return false;
    }
}
