package UI;

public class AuthenticationUI extends UI{
    public static void loginMenuUI() {
        clearScreen();
        do {
            System.out.println("Welcome to Malaysia Tourist Attraction Recommendations!");
            System.out.println("\t1. Login as Member\n\t2. Login as Admin\n\t3. Register as Member\n\t0. Exit");
            System.out.print("Selection: ");
            String choice = sc.nextLine();
            
            switch (choice) {
                case "1":
                    memberLoginUI();
                    break;
                case "2":
                    adminLoginUI();
                    break;
                case "3":
                    registerMemberUI();
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

    public static void memberLoginUI() {
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

            // TODO : Link to file to check if the username and password are correct and validations
            if (username.equals("member") && password.equals("password")) {
                System.out.println("Login successful!");
                MemberModuleUI.memberMenuUI();
            } else {
                clearScreen();
                System.out.println("Invalid credentials. Please try again.");
            }
            break;
        } while(true);
    }

    public static void adminLoginUI() {
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

            if (username.equals("admin") && password.equals("admin")) {
                System.out.println("Login successful!");
                AdminModuleUI.adminMenuUI();
            } else {
                clearScreen();
                System.out.println("Invalid credentials. Please try again.");
            }
            break;
        } while(true);
    }

    public static void registerMemberUI() {
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

            // TODO : Link to file to save the new member's credentials and validations
            System.out.println("Registration successful! You can now log in.");
            break;
        } while(true);
    }
}
