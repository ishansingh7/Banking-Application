package bank;

import java.sql.*;
import java.util.Scanner;

public class Admin {

    private static Scanner scanner = new Scanner(System.in);

    // Admin login validation (example username and password)
    public boolean login(String username, String password) {
        // For simplicity, using hardcoded credentials. You can replace this with database check.
        return username.equals("admin") && password.equals("admin123");
    }

    // Admin menu for managing accounts and transactions
    public void showAdminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View All Accounts");
            System.out.println("2. View All Transactions");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewAllAccounts();
                    break;
                case 2:
                    viewAllTransactions();
                    break;
                case 3:
                    System.out.println("Logging out.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to view all accounts in the system (Admin-only access)
    private void viewAllAccounts() {
        System.out.println("\n--- All Accounts ---");

        // SQL query to fetch all accounts from the database
        String query = "SELECT * FROM accounts";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdb", "root", "password");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                System.out.println("Username: " + resultSet.getString("username"));
                System.out.println("Account Number: " + resultSet.getString("accNo"));
                System.out.println("Account Type: " + resultSet.getString("accType"));
                System.out.println("Balance: " + resultSet.getDouble("balance"));
                System.out.println("Phone: " + resultSet.getString("phone"));
                System.out.println("Address: " + resultSet.getString("address"));
                System.out.println("Branch: " + resultSet.getString("branch"));
                System.out.println("-----------------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to view all transactions in the system (Admin-only access)
    private void viewAllTransactions() {
        System.out.println("\n--- All Transactions ---");

        // SQL query to fetch all transactions from the database
        String query = "SELECT * FROM transactions";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdb", "root", "password");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                System.out.println("Account Number: " + resultSet.getString("accNo"));
                System.out.println("Transaction Type: " + resultSet.getString("type"));
                System.out.println("Amount: " + resultSet.getDouble("amount"));
                System.out.println("Date: " + resultSet.getTimestamp("date"));
                System.out.println("-----------------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
