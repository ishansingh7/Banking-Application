package bank;

public class Transaction {
    private int senderAccountNumber;
    private int receiverAccountNumber;
    private int transactionId;
    private int transactionAmount;
    private String date;
    private String transactionType;

    // Constructor
    public Transaction(int senderAccountNumber, int receiverAccountNumber, int transactionId, int transactionAmount, String date, String transactionType) {
        this.senderAccountNumber = senderAccountNumber;
        this.receiverAccountNumber = receiverAccountNumber;
        this.transactionId = transactionId;
        this.transactionAmount = transactionAmount;
        this.date = date;
        this.transactionType = transactionType;
    }

    // Getters and Setters
    public int getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public void setSenderAccountNumber(int senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    public int getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(int receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(int transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    // Override toString method
    @Override
    public String toString() {
        return "Transaction{" +
                "senderAccountNumber=" + senderAccountNumber +
                ", receiverAccountNumber=" + receiverAccountNumber +
                ", transactionId=" + transactionId +
                ", transactionAmount=" + transactionAmount +
                ", date='" + date + '\'' +
                ", transactionType='" + transactionType + '\'' +
                '}';
    }
}
