package client;

import java.util.OptionalInt;

import client.api.UserAPI;

public class SSOManagementApplication {
    private static UserAPI userAPI;

    public static void main(String[] args) {
        userAPI = new UserAPI();
        loginScreen();
    }

    /**
     * Display the login screen with login and password fields
     */
    private static void loginScreen() {
        try {
            System.out.println("Please enter your credentials:");

            System.out.print("E-mail: ");
            String email = System.console().readLine();
            System.out.print("Password: ");
            char[] passwordChars = System.console().readPassword();
            String password = new String(passwordChars);

            boolean loginSuccessful = userAPI.login(email, password);

            if (loginSuccessful) {
                mainScreen();
            } else {
                System.out.println("Invalid credentials. Please try again.");
                loginScreen();
            }
        } catch (Exception e) {
            System.out.println("An error occurred. Please try again.");
            loginScreen();
        }
    }

    /**
     * Display the main screen with the available options
     */
    private static void mainScreen() {
        displayWelcomeMessage();
        displayOptions();

        String choiceStr;
        OptionalInt choiceOptional = OptionalInt.empty();

        while (!choiceOptional.isPresent()) {
            choiceStr = System.console().readLine("Please enter your choice: ")
                    .trim();
            try {
                choiceOptional = OptionalInt.of(Integer.parseInt(choiceStr));
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        int choice = choiceOptional.getAsInt();
        switch (choice) {
            case 1:
                userAPI.logout();
                break;
            case 2:
                exit();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                mainScreen();
                break;
        }
    }

    /**
     * Logout and then exit the program
     */
    private static void exit() {
        userAPI.logout();
        System.out.println("Exiting the program.");
        System.exit(0);
    }

    /**
     * Display all available options
     */
    private static void displayOptions() {
        System.out.println("1. Logout");
        System.out.println("2. Exit the program");
    }

    /**
     * Display the welcome message
     */
    private static void displayWelcomeMessage() {
        System.out.println("SSO Management Application");
        System.out.println("--------------------------");
        System.out.println("Welcome to the application!");
    }
}
