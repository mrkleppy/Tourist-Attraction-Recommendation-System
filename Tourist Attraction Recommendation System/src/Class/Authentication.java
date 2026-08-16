package Class;

import java.util.List;

public class Authentication { 
    private static String currentUser;

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String currentUser) {
        Authentication.currentUser = currentUser;
    }
    
    public static boolean validateLogin(List<User> users, String inputUsername, String inputPassword, String role) {
        for (User user : users) {
            if (user.getUsername().equals(inputUsername) &&
                user.getPassword().equals(inputPassword) &&
                user.getRole().equalsIgnoreCase(role)) {
                
                currentUser = inputUsername;
                return true;
            }
        }
        return false;
    }

    public static boolean registerMember(List<User> users, List<SearchHistory> searchHistories, String inputUsername, String inputPassword) {
        if (usernameExists(users, inputUsername)) {
            return false; // Username already exists
        }

        Member newMember = new Member(inputUsername, inputPassword);
        users.add(newMember);
        File.appendCredentialFile(newMember); // Append to the credential file
        
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setMember(newMember);
        searchHistory.setStates(SearchHistory.defaultStates());
        searchHistories.add(searchHistory);
        File.appendSearchHistoryFile(searchHistory);
        
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
