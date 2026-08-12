package UI;

import java.util.List;
import Class.*;

public class Main {

    public static void main(String[] args) {
        List<User> users = File.readCredentialFile();
        List<City> cities = File.readCityFile();
        // List<Attraction> attractions = File.readAttractionFile(cities); // temporary (waiting tenglok to fix the attraction file)

        AuthenticationUI.loginMenuUI(users, cities);
    }
}
