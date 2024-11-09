package bank;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static Connection connection;
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Account> accounts = new ArrayList<>();
    private static ArrayList<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        connectToDatabase();

        while (true) {
            System.out.println("\n--- Banking System ---");
            System.out.println("1. Create New Account");
            System.out.println("2. User Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer after integer input

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    login();  // User Login
                    break;
                case 3:
                    adminLogin();  // Admin Login
                    break;
                case 4:
                    System.out.println("Exiting the system.");
                    closeConnection();
                    scanner.close(); // Close scanner resource
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void connectToDatabase() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bankdb", "root", "ishan");
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }

    private static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to close the database connection.");
            e.printStackTrace();
        }
    }

    private static void createAccount() {
        System.out.print("Enter your name: ");
        String username = scanner.nextLine();

        System.out.print("Enter a unique account number: ");
        String accNo = scanner.nextLine();

        System.out.print("Enter account type (e.g., Savings, Checking): ");
        String accType = scanner.nextLine();

        System.out.print("Enter IFSC code: ");
        String ifscCode = scanner.nextLine();

        System.out.print("Enter initial balance: ");
        double balance = scanner.nextDouble();

        System.out.print("Create a password: ");
        scanner.nextLine(); // Clear the newline
        String password = scanner.nextLine();

        System.out.print("Enter your phone number: ");
        String phone = scanner.nextLine();

        System.out.print("Enter your address: ");
        String address = scanner.nextLine();

        System.out.print("Enter branch name: ");
        String branch = scanner.nextLine();

        String query = "INSERT INTO accounts (username, accNo, accType, ifscCode, balance, password, phone, address, branch) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, accNo);
            preparedStatement.setString(3, accType);
            preparedStatement.setString(4, ifscCode);
            preparedStatement.setDouble(5, balance);
            preparedStatement.setString(6, password);
            preparedStatement.setString(7, phone);
            preparedStatement.setString(8, address);
            preparedStatement.setString(9, branch);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Account created successfully!");
            } else {
                System.out.println("Failed to create account.");
            }
        } catch (SQLException e) {
            System.out.println("Error creating account.");
            e.printStackTrace();
        }
    }

    private static void login() {
        System.out.print("Enter account number: ");
        String accNo = scanner.next();

        System.out.print("Enter password: ");
        String password = scanner.next();

        Account account = authenticateUser(accNo, password);
        if (account != null) {
            System.out.println("Login successful!");
            userMenu(account);
        } else {
            System.out.println("Invalid credentials. Try again.");
        }
    }

    private static Account authenticateUser(String accNo, String password) {
        String query = "SELECT * FROM accounts WHERE accNo = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, accNo);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Account(
                        resultSet.getString("username"),
                        resultSet.getString("accNo"),
                        resultSet.getString("accType"),
                        resultSet.getString("ifscCode"),
                        resultSet.getDouble("balance"),
                        resultSet.getString("password"),
                        resultSet.getString("phone"),
                        resultSet.getString("address"),
                        resultSet.getString("branch")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error authenticating user.");
            e.printStackTrace();
        }
        return null;
    }

    private static void userMenu(Account account) {
        while (true) {
            System.out.println("\n--- Welcome to Laxmi Cheat Funds Bank --- " + account.getUsername());
            System.out.println("\n--- User Menu ---");
            System.out.println("1. Update Account");
            System.out.println("2. Check Balance");
            System.out.println("3. Set Password");
            System.out.println("4. Make a Transaction");
            System.out.println("5. Transfer Money");
            System.out.println("6. View Transaction History");
            System.out.println("7. View Account Details");
            System.out.println("8. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    updateAccount(account);
                    break;
                case 2:
                    checkBalance(account);
                    break;
                case 3:
                    setPassword(account);
                    break;
                case 4:
                    makeTransaction(account);
                    break;
                case 5:
                    transferMoney(account);
                    break;
                case 6:
                    viewTransactionHistory(account);
                    break;
                case 7:
                    viewAccountDetails(account);
                    break;
                case 8:
                    System.out.println("Logging out.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void updateAccount(Account account) {
        System.out.println("\n--- Update Account ---");
        System.out.println("1. Update Phone Number");
        System.out.println("2. Update Address");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Clear the newline

        try {
            if (choice == 1) {
                System.out.print("Enter new phone number: ");
                String newPhone = scanner.nextLine();
                String updateQuery = "UPDATE accounts SET phone = ? WHERE accNo = ?";
                try (PreparedStatement ps = connection.prepareStatement(updateQuery)) {
                    ps.setString(1, newPhone);
                    ps.setString(2, account.getAccNo());
                    ps.executeUpdate();
                    System.out.println("Phone number updated.");
                }
            } else if (choice == 2) {
                System.out.print("Enter new address: ");
                String newAddress = scanner.nextLine();
                String updateQuery = "UPDATE accounts SET address = ? WHERE accNo = ?";
                try (PreparedStatement ps = connection.prepareStatement(updateQuery)) {
                    ps.setString(1, newAddress);
                    ps.setString(2, account.getAccNo());
                    ps.executeUpdate();
                    System.out.println("Address updated.");
                }
            } else {
                System.out.println("Invalid option.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating account.");
            e.printStackTrace();
        }
    }

    private static void checkBalance(Account account) {
        System.out.println("Current balance: " + account.getBalance());
    }

    private static void setPassword(Account account) {
        System.out.print("Enter new password: ");
        scanner.nextLine();
        String newPassword = scanner.nextLine();
        String updateQuery = "UPDATE accounts SET password = ? WHERE accNo = ?";
        try (PreparedStatement ps = connection.prepareStatement(updateQuery)) {
            ps.setString(1, newPassword);
            ps.setString(2, account.getAccNo());
            ps.executeUpdate();
            System.out.println("Password updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error setting new password.");
            e.printStackTrace();
        }
    }

    private static void makeTransaction(Account account) {
        System.out.println("\n--- Make a Transaction ---");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
    
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
    
        String transactionType = (choice == 1) ? "Deposit" : "Withdraw";
        String query = "UPDATE accounts SET balance = balance + ? WHERE accNo = ?";
    
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            if (choice == 2) {
                amount = -amount;
            }
    
            ps.setDouble(1, amount);
            ps.setString(2, account.getAccNo());
            ps.executeUpdate();
    
            System.out.println(transactionType + " successful!");
            saveTransaction(account.getAccNo(), transactionType, amount);
    
            // Ask the user if they want to print the bill
            System.out.print("Do you want to print the transaction bill? (yes/no): ");
            String printBill = scanner.next();
    
            if ("yes".equalsIgnoreCase(printBill)) {
                printTransactionBill(account, transactionType, amount);
            }
    
        } catch (SQLException e) {
            System.out.println("Error performing transaction.");
            e.printStackTrace();
        }
    }
    
    private static void printTransactionBill(Account account, String transactionType, double amount) {
        System.out.println("\n--- Transaction Bill ---");
        System.out.println("Account Number: " + account.getAccNo());
        System.out.println("Account Holder: " + account.getUsername());
        System.out.println("Transaction Type: " + transactionType);
        System.out.println("Amount: " + amount);
        System.out.println("Updated Balance: " + account.getBalance());
        System.out.println("-------------------------");
    }
    
    
    private static void printTransactionBill(Account account, String transactionType, double amount) {
        System.out.println("\n--- Transaction Bill ---");
        System.out.println("Account Number: " + account.getAccNo());
        System.out.println("Account Holder: " + account.getUsername());
        System.out.println("Transaction Type: " + transactionType);
        System.out.println("Amount: " + amount);
        System.out.println("Updated Balance: " + account.getBalance());
        System.out.println("-------------------------");
    }
    

    private static void transferMoney(Account account) {
        System.out.print("Enter recipient's account number: ");
        scanner.nextLine();
        String recipientAccNo = scanner.nextLine();
    
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
    
        try {
            connection.setAutoCommit(false); // Start transaction
    
            String deductQuery = "UPDATE accounts SET balance = balance - ? WHERE accNo = ?";
            try (PreparedStatement ps = connection.prepareStatement(deductQuery)) {
                ps.setDouble(1, amount);
                ps.setString(2, account.getAccNo());
                ps.executeUpdate();
            }
    
            String addQuery = "UPDATE accounts SET balance = balance + ? WHERE accNo = ?";
            try (PreparedStatement ps = connection.prepareStatement(addQuery)) {
                ps.setDouble(1, amount);
                ps.setString(2, recipientAccNo);
                ps.executeUpdate();
            }
    
            saveTransaction(account.getAccNo(), "Transfer", -amount, recipientAccNo);
            saveTransaction(recipientAccNo, "Transfer", amount, account.getAccNo());
    
            connection.commit(); // Commit transaction
            System.out.println("Transfer successful.");
    
            // Ask the user if they want to print the transfer bill
            System.out.print("Do you want to print the transfer bill? (yes/no): ");
            String printBill = scanner.next();
    
            if ("yes".equalsIgnoreCase(printBill)) {
                printTransferBill(account, recipientAccNo, amount);
            }
    
        } catch (SQLException e) {
            System.out.println("Transfer failed.");
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static void printTransferBill(Account account, String recipientAccNo, double amount) {
        System.out.println("\n--- Transfer Bill ---");
        System.out.println("Sender Account Number: " + account.getAccNo());
        System.out.println("Sender Account Holder: " + account.getUsername());
        System.out.println("Recipient Account Number: " + recipientAccNo);
        System.out.println("Amount Transferred: " + amount);
        System.out.println("Sender Updated Balance: " + account.getBalance());
        System.out.println("-------------------------");
    }
    

    private static void saveTransaction(String accNo, String type, double amount, String recipientAccNo) {
        String query = "INSERT INTO transactions (accNo, type, amount, recipientAccNo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, accNo);
            ps.setString(2, type);
            ps.setDouble(3, amount);
            ps.setString(4, recipientAccNo);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error saving transaction.");
            e.printStackTrace();
        }
    }

    private static void saveTransaction(String accNo, String type, double amount) {
        saveTransaction(accNo, type, amount, null);
    }

    private static void viewTransactionHistory(Account account) {
        String query = "SELECT * FROM transactions WHERE accNo = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, account.getAccNo());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("Transaction ID: " + rs.getInt("id"));
                System.out.println("Type: " + rs.getString("type"));
                System.out.println("Amount: " + rs.getDouble("amount"));
                System.out.println("Recipient Account: " + rs.getString("recipientAccNo"));
                System.out.println("Date: " + rs.getTimestamp("date"));
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching transaction history.");
            e.printStackTrace();
        }
    }

    private static void viewAccountDetails(Account account) {
        System.out.println("\n--- Account Details ---");
        System.out.println("Account Number: " + account.getAccNo());
        System.out.println("Username: " + account.getUsername());
        System.out.println("Account Type: " + account.getAccType());
        System.out.println("IFSC Code: " + account.getIfscCode());
        System.out.println("Balance: " + account.getBalance());
        System.out.println("Phone: " + account.getPhone());
        System.out.println("Address: " + account.getAddress());
        System.out.println("Branch: " + account.getBranch());
    }

    private static void adminLogin() {
        System.out.print("Enter admin username: ");
        String adminUsername = scanner.next();

        System.out.print("Enter admin password: ");
        String adminPassword = scanner.next();

        if ("admin".equals(adminUsername) && "admin123".equals(adminPassword)) {
            System.out.println("Admin login successful.");
            adminMenu();
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }

    // In the Admin Menu method
private static void adminMenu() {
    while (true) {
        System.out.println("\n--- Welcome to Laxmi Cheat Funds Bank ---");
        System.out.println("\n--- Admin Menu ---");
        System.out.println("1. View All Accounts");
        System.out.println("2. View All Transactions");
        System.out.println("3. Delete Account");
        System.out.println("4. Logout");
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
                deleteAccount();
                break;
            case 4:
                System.out.println("Logging out.");
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}

// New method to delete account
private static void deleteAccount() {
    System.out.print("Enter the account number to delete: ");
    String accNo = scanner.next();

    // Check if the account exists before trying to delete
    String checkQuery = "SELECT * FROM accounts WHERE accNo = ?";
    try (PreparedStatement ps = connection.prepareStatement(checkQuery)) {
        ps.setString(1, accNo);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            // Confirm deletion
            System.out.print("Are you sure you want to delete the account (y/n)? ");
            String confirm = scanner.next();

            if ("y".equalsIgnoreCase(confirm)) {
                // Delete the account
                String deleteAccountQuery = "DELETE FROM accounts WHERE accNo = ?";
                try (PreparedStatement deletePs = connection.prepareStatement(deleteAccountQuery)) {
                    deletePs.setString(1, accNo);
                    deletePs.executeUpdate();
                    System.out.println("Account deleted successfully.");

                    // Optionally, delete the related transactions (if needed)
                    String deleteTransactionsQuery = "DELETE FROM transactions WHERE accNo = ?";
                    try (PreparedStatement deleteTransPs = connection.prepareStatement(deleteTransactionsQuery)) {
                        deleteTransPs.setString(1, accNo);
                        deleteTransPs.executeUpdate();
                        System.out.println("Related transactions deleted.");
                    }
                } catch (SQLException e) {
                    System.out.println("Error deleting account.");
                    e.printStackTrace();
                }
            } else {
                System.out.println("Account deletion cancelled.");
            }
        } else {
            System.out.println("Account not found.");
        }
    } catch (SQLException e) {
        System.out.println("Error checking account.");
        e.printStackTrace();
    }
}

    private static void viewAllAccounts() {
        String query = "SELECT * FROM accounts";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("Account Number: " + rs.getString("accNo"));
                System.out.println("Username: " + rs.getString("username"));
                System.out.println("Account Type: " + rs.getString("accType"));
                System.out.println("Balance: " + rs.getDouble("balance"));
                System.out.println("Phone: " + rs.getString("phone"));
                System.out.println("Address: " + rs.getString("address"));
                System.out.println("Branch: " + rs.getString("branch"));
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching accounts.");
            e.printStackTrace();
        }
    }

    private static void viewAllTransactions() {
        String query = "SELECT * FROM transactions";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("Transaction ID: " + rs.getInt("id"));
                System.out.println("Account Number: " + rs.getString("accNo"));
                System.out.println("Transaction Type: " + rs.getString("type"));
                System.out.println("Amount: " + rs.getDouble("amount"));
                System.out.println("Recipient Account: " + rs.getString("recipientAccNo"));
                System.out.println("Date: " + rs.getTimestamp("date"));
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching transactions.");
            e.printStackTrace();
        }
    }
}
