package UI;

import Class.*;
import java.util.List;

public class AuthenticationUI extends UI{
    public static void loginMenuUI(List<User> users, List<City> cities) {
        clearScreen();
        do {
            System.out.println("Welcome to Malaysia Tourist Attraction Recommendations!");
            System.out.println("\t1. Login as Member\n\t2. Login as Admin\n\t3. Register as Member\n\t0. Exit");
            System.out.print("Selection: ");
            String choice = sc.nextLine();
            
            switch (choice) {
                case "1":
                    memberLoginUI(users, cities);
                    break;
                case "2":
                    adminLoginUI(users, cities);
                    break;
                case "3":
                    registerMemberUI(users);
                    break;
                case "0":
                    clearScreen();
                    System.out.println("Bye, Have a nice trip!");
                    System.exit(0);
                    break;
                default:
                    clearScreen();
                    System.out.println("Enter 1, 2, 3, or 0 Only!");
            }
        } while(true);
    }

    public static void memberLoginUI(List<User> users, List<City> cities) {
        clearScreen();
        do {
            System.out.println("Member Login");
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            
            if (username.equalsIgnoreCase("q")) {
                return;
            }
            
            System.out.print("Enter password: ");
            String password = sc.nextLine();

            if (Authentication.validateLogin(users, username, password, "member")) {
                System.out.println("Login successful!");
                MemberModuleUI.memberMenuUI();
            } else {
                clearScreen();
                System.out.println("Invalid credentials. Please try again.");
            }
        } while(true);
    }

    public static void adminLoginUI(List<User> users, List<City> cities) {
        clearScreen();
        do {
            System.out.println("Admin Login");
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            
            if (username.equalsIgnoreCase("q")) {
                return;
            }
            
            System.out.print("Enter password: ");
            String password = sc.nextLine();

            if (Authentication.validateLogin(users, username, password, "admin")) {
                System.out.println("Login successful!");
                AdminModuleUI.adminMenuUI(users, cities);
            } else {
                clearScreen();
                System.out.println("Invalid credentials. Please try again.");
            }
        } while(true);
    }

    public static void registerMemberUI(List<User> users) {
        clearScreen();
        do {
            System.out.println("Register as Member");
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            
            if (username.equalsIgnoreCase("q")) {
                return;
            }
            
            System.out.print("Enter password: ");
            String password = sc.nextLine();

            if (Authentication.registerMember(users, username, password)) {
                clearScreen();
                System.out.println("Registration successful! You can now log in.");
                break;
            } else {
                clearScreen();
                System.out.println("Username already exists. Please try again.");
            }

            System.out.println("Registration successful! You can now log in.");
            break;
        } while(true);
    }
}
