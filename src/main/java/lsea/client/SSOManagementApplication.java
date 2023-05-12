package lsea.client;
import java.io.*;
import java.nio.file.*;
import java.util.Scanner;

public class SSOManagementApplication {
    private static final String COOKIE_FILE = "cookie.txt";

    public static void main(String[] args) {
        showLoginScreen();
    }

    private static void showLoginScreen() {
        System.out.println("SSO Management Application");
        System.out.println("--------------------------");
        System.out.println("Please enter your credentials:");

        Scanner scanner = new Scanner(System.in);
        System.out.print("E-mail: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        boolean loginSuccessful = performLogin(email, password);

        if (loginSuccessful) {
            showMainScreen();
        } else {
            System.out.println("Invalid credentials. Please try again.");
            showLoginScreen();
        }
    }

    private static boolean performLogin(String email, String password) {
        String cookieToken = "some_cookie_token";

        try {
            Files.write(Paths.get(COOKIE_FILE), cookieToken.getBytes());
            return true;
        } catch (IOException e) {
            System.out.println("Failed to save the cookie token.");
            return false;
        }
    }

    private static void showMainScreen() {
        System.out.println("SSO Management Application");
        System.out.println("--------------------------");
        System.out.println("Welcome to the application!");

        System.out.println("1. Logout");
        System.out.println("2. Exit the program");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                logout(false);
                break;
            case 2:
                exit();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                showMainScreen();
                break;
        }
    }

    private static void logout(boolean exit) {
        try {
            Files.deleteIfExists(Paths.get(COOKIE_FILE));
            if (!exit) {
                System.out.println("Logged out successfully.");
                showLoginScreen();
            }
        } catch (IOException e) {
            System.out.println("Failed to clear the cookie token.");
        }
    }

    private static void exit() {
        logout(true);
        System.out.println("Exiting the program.");
        System.exit(0);
    }
}
