package UI;

import Class.*;
import java.util.List;

public class AuthenticationUI extends UI{
    public static void loginMenuUI(List<User> users, List<SearchHistory> searchHistories) {
        do {
            System.out.println("\t1. Login as Member\n\t2. Login as Admin\n\t3. Register as Member\n\t0. Exit");
            System.out.print("\nSelection: ");
            String choice = sc.nextLine();
            
            clearScreen();
            switch (choice) {
                case "1":
                    System.out.println("Enter 'q' to go back to the previous menu.");
                    memberLoginUI(users, searchHistories);
                    break;
                case "2":
                    System.out.println("Enter 'q' to go back to the previous menu.");
                    adminLoginUI(users);
                    break;
                case "3":
                    System.out.println("Enter 'q' to go back to the previous menu.");
                    registerMemberUI(users, searchHistories);
                    break;
                case "0":
                    System.out.println("Bye, Have a nice trip!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Enter 1, 2, 3, or 0 Only!");
            }
        } while(true);
    }

    public static void memberLoginUI(List<User> users, List<SearchHistory> searchHistories) {
        do {
            System.out.println("Member Login");
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            
            if (username.equalsIgnoreCase("q")) {
                return;
            }
            
            System.out.print("Enter password: ");
            String password = sc.nextLine();

            clearScreen();
            if (Authentication.validateLogin(users, username, password, "member")) {
                System.out.println("Login successful!");
                MemberModuleUI.memberMenuUI(searchHistories);
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        } while(true);
    }

    public static void adminLoginUI(List<User> users) {
        do {
            System.out.println("Admin Login");
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            
            if (username.equalsIgnoreCase("q")) {
                return;
            }
            
            System.out.print("Enter password: ");
            String password = sc.nextLine();

            clearScreen();
            if (Authentication.validateLogin(users, username, password, "admin")) {
                System.out.println("Login successful!");
                AdminModuleUI.adminMenuUI(users);
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        } while(true);
    }

    public static void registerMemberUI(List<User> users, List<SearchHistory> searchHistories) {
        do {
            System.out.println("Register as Member");
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            
            if (username.equalsIgnoreCase("q")) {
                clearScreen();
                return;
            }
            
            if (username.isEmpty()) {
                clearScreen();
                System.out.println("Please enter a username!");
                continue;
            } else if (username.length() < 3) {
                clearScreen();
                System.out.println("Please enter a username longer than 3 characters!");
                continue;
            }
            
            System.out.print("Enter password: ");
            String password = sc.nextLine();

            if (password.isEmpty()) {
                clearScreen();
                System.out.println("Password cannot be empty!");
                continue;
            } else if (password.length() < 6) {
                clearScreen();
                System.out.println("Password cannot be shorter than 6 characters!");
                continue;
            }
            
            System.out.print("Enter password again: ");
            String confirmPassword = sc.nextLine();

            clearScreen();
            if (!password.equals(confirmPassword)) {
                System.out.println("Passwords do not match. Please try again.");
                continue;
            }

            if (Authentication.registerMember(users, searchHistories, username, password)) {
                System.out.println("Registration successful! You can now log in.");
                break;
            } else {
                System.out.println("Username already exists. Please try again.");
            }

            System.out.println("Registration successful! You can now log in.");
            break;
        } while(true);
    }
}
