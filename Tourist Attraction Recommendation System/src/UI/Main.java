package UI;

import java.util.List;
import Class.*;

public class Main {

    public static void main(String[] args) {
        List<User> users = File.readCredentialFile();
        List<SearchHistory> searchHistories = File.readSearchHistoryFile();

        AuthenticationUI.loginMenuUI(users, searchHistories);
    }
}
