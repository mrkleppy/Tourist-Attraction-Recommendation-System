package UI;

import java.util.List;
import Class.*;

public class Main {

    public static void main(String[] args) {
        // Load the data files then proceed to the login menu
        List<User> users = File.readCredentialFile();
        List<SearchHistory> searchHistories = File.readSearchHistoryFile();

        AuthenticationUI.loginMenuUI(users, searchHistories);
    }
}
