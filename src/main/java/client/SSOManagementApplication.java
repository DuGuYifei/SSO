package client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import client.api.UserAPI;
import lsea.entity.Log;

/**
 * Application main class for SSO Management Application. (Client)
 */
public class SSOManagementApplication extends JFrame implements ActionListener {
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
     * The table to display the logs
     */
    private JTable table;

    /**
     * The table model for the table
     */
    private DefaultTableModel tableModel;

    /**
     * Default values for offset in order not to get overflown by the logs
     */
    private int offset = 0;

    /**
     * Default values for limit in order not to get overflown by the logs
     */
    private int limit = 10;

    /**
     * Thread for live capture logs
     */
    private Thread liveCaptureThread;

    /**
     * Flag to control live capture process
     */
    private boolean isLiveCapture = false;

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
        if (UserAPI.connect()) {
            System.out.println("Connected to the server.");
        }

        Container pane = resetScreen();

        displayWelcomeMessage();

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoutPanel.add(logoutButton);

        JButton retrieveLogs = new JButton("Retrieve 10 logs");
        retrieveLogs.addActionListener(this);
        JPanel retrieveLogsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        retrieveLogsPanel.add(retrieveLogs);

        JButton liveCaptureLogs = new JButton("Live capture logs");
        liveCaptureLogs.addActionListener(this);
        JPanel liveCaptureLogsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        liveCaptureLogsPanel.add(liveCaptureLogs);

        JButton stopLogs = new JButton("Stop");
        stopLogs.addActionListener(this);
        JPanel stopLogsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        stopLogsPanel.add(stopLogs);

        JButton exitButton = new JButton("Exit the program");
        exitButton.addActionListener(this);
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        exitPanel.add(exitButton);

        pane.setLayout(new BorderLayout());

        // Add the buttons to a separate panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(logoutPanel);
        buttonPanel.add(retrieveLogsPanel);
        buttonPanel.add(liveCaptureLogsPanel);
        buttonPanel.add(stopLogsPanel);
        buttonPanel.add(exitPanel);

        pane.add(buttonPanel, BorderLayout.WEST);

        // Create the table model with column names
        String[] columnNames = { "ID", "Data", "Log Type", "User ID", "Created At", "User Current State" };
        tableModel = new DefaultTableModel(columnNames, 0);
        // Create the JTable with the table model
        table = new JTable(tableModel);
        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 200)); // Adjust the dimensions as needed
        // Add the scroll pane to the panel
        pane.add(scrollPane, BorderLayout.CENTER);

        // Create the next and previous buttons
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        JButton previousButton = new JButton("Previous");
        previousButton.addActionListener(this);
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        navigationPanel.add(previousButton);
        navigationPanel.add(nextButton);

        // Add the navigation panel to the south region
        pane.add(navigationPanel, BorderLayout.SOUTH);

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
     * Handle the action events
     * 
     * @param logs - the logs to add
     */
    public void setLogs(List<Log> logs) {
        // Clear the table
        tableModel.setRowCount(0);

        // Add each log to the table
        for (Log log : logs) {
            Object[] rowData = {
                    log.getId(),
                    log.getData(),
                    log.getLogType(),
                    log.getUserId(),
                    log.getCreatedAt(),
                    log.getUserCurrentState()
            };
            tableModel.addRow(rowData);
        }
    }

    /**
     * Add logs to the table
     * 
     * @param logs - the logs to add
     */
    public void addLogs(List<Log> logs) {
        // Add each log to the table
        for (Log log : logs) {
            Object[] rowData = {
                    log.getId(),
                    log.getData(),
                    log.getLogType(),
                    log.getUserId(),
                    log.getCreatedAt(),
                    log.getUserCurrentState()
            };
            tableModel.insertRow(0, rowData);
        }
    }

    /**
     * Handle the action performed
     * 
     * @param e - the action event
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
        } else if (e.getActionCommand().equals("Live capture logs")) {
            startLiveCapture();
        } else if (e.getActionCommand().equals("Stop")) {
            stopLiveCapture();
            try {
                userAPI.serverStopLiveCapture();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getActionCommand().equals("Retrieve 10 logs")) {
            List<Log> logs;
            try {
                logs = userAPI.retrieveLogs(offset, limit);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            setLogs(logs);
        } else if (e.getActionCommand().equals("Next")) {
            offset += 10;
            List<Log> logs;
            try {
                logs = userAPI.retrieveLogs(offset, limit);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            setLogs(logs);
        } else if (e.getActionCommand().equals("Previous")) {
            if (offset >= 10) {
                offset -= 10;
            }
            List<Log> logs;
            try {
                logs = userAPI.retrieveLogs(offset, limit);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            setLogs(logs);
        }
    }

    /**
     * Start the live capture thread
     */
    private void startLiveCapture() {
        isLiveCapture = true;
        tableModel.setRowCount(0);
        Runnable liveCaptureTask = () -> {
            while (isLiveCapture) {
                List<Log> logs;
                try {
                    logs = userAPI.liveCaptureLogs();
                    addLogs(logs);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        liveCaptureThread = new Thread(liveCaptureTask);
        liveCaptureThread.start();
    }

    /**
     * Stop the live capture thread
     */
    private void stopLiveCapture() {
        isLiveCapture = false;
        try {
            liveCaptureThread.join(); // Wait for the live capture thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
