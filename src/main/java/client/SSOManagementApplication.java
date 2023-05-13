package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.api.UserAPI;

/**
 * Application main class for SSO Management Application. (Client)
 */
public class SSOManagementApplication extends JFrame implements ActionListener {
    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    /**
     * The UserAPI instance
     */
    private static UserAPI userAPI;
    /**
     * The email field
     */
    private JTextField emailField;
    /**
     * The password field
     */
    private JPasswordField passwordField;

    /**
     * Constructor for the SSO Management Application
     */
    public SSOManagementApplication() {
        userAPI = new UserAPI();

        setTitle("SSO Management Application");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createLoginForm();
    }

    /**
     * Main method
     * 
     * @param args - command line arguments
     */
    public static void main(String[] args) {
        new SSOManagementApplication();
    }

    /**
     * Create the login screen
     */
    private void createLoginForm() {
        Container pane = resetScreen();

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);

        pane.add(emailPanel);
        pane.add(passwordPanel);
        pane.add(buttonPanel);

        doneScreen();
    }

    /**
     * Create the main screen
     */
    private void createMainScreen() {
        Container pane = resetScreen();

        displayWelcomeMessage();

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoutPanel.add(logoutButton);

        JButton exitButton = new JButton("Exit the program");
        exitButton.addActionListener(this);
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        exitPanel.add(exitButton);

        pane.add(logoutPanel);
        pane.add(exitPanel);

        doneScreen();
    }

    /**
     * Reset the screen
     * 
     * @return the pane
     */
    private Container resetScreen() {
        setVisible(false);
        Container pane = getContentPane();
        pane.removeAll();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        return pane;
    }

    /**
     * Called when the screen is done being created
     */
    private void doneScreen() {
        setVisible(true);
        validate();
        repaint();
    }

    /**
     * Display the welcome message
     */
    private void displayWelcomeMessage() {
        JLabel welcomeLabel = new JLabel("Welcome to the application!");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 20));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel appLabel = new JLabel("SSO Management Application");
        appLabel.setFont(new Font("Serif", Font.BOLD, 24));
        appLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.add(appLabel);
        labelPanel.add(welcomeLabel);

        getContentPane().add(labelPanel);
    }

    /**
     * Handle the action performed
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Login")) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            boolean loginSuccessful = userAPI.login(email, password);

            if (loginSuccessful) {
                createMainScreen();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.");
            }
        } else if (e.getActionCommand().equals("Logout")) {
            userAPI.logout();
            createLoginForm();
        } else if (e.getActionCommand().equals("Exit the program")) {
            userAPI.logout();
            System.out.println("Exiting the program.");
            System.exit(0);
        }
    }
}
