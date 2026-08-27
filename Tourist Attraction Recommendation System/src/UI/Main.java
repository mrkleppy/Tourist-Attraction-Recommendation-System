package UI;

import java.util.List;
import Class.*;
import graph.*;

public class Main {

    public static void main(String[] args) {
        // Load the data files then proceed to the login menu
        List<User> users = File.readCredentialFile();
        List<SearchHistory> searchHistories = File.readSearchHistoryFile();
        Graph graph = new Graph();
        graph.loadGraph();

        AuthenticationUI.loginMenuUI(users, searchHistories, graph);
    }
}
