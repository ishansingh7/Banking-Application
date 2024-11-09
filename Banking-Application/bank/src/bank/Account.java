package bank;
public class Account {  
    private String username;  
    private String accNo;        // Account Number  
    private String accType;      // Account Type (e.g., Savings, Checking)  
    private String ifscCode;     // IFSC Code  
    private double balance;       // Account Balance  
    private String password;      // Password for account access  
    private String phone;         // Phone number  
    private String address;       // Address of the account holder  
    private String branch;        // Branch name  

    
    public Account(String username, String accNo, String accType,  
                   String ifscCode, double balance, String password,  
                   String phone, String address, String branch) {  
        this.username = username;  
        this.accNo = accNo;  
        this.accType = accType;  
        this.ifscCode = ifscCode;  
        this.balance = balance;  
        this.password = password;  
        this.phone = phone;  
        this.address = address;  
        this.branch = branch;  
    }  

   
    public String getUsername() {  
        return username;  
    }  

    public String getAccNo() {  
        return accNo;  
    }  

    public String getAccType() {  
        return accType;  
    }  

    public String getIfscCode() {  
        return ifscCode;  
    }  

    public double getBalance() {  
        return balance;  
    }  

    public String getPassword() {  
        return password;  
    }  

    public String getPhone() {  
        return phone;  
    }  

    public String getAddress() {  
        return address;  
    }  

    public String getBranch() {  
        return branch;  
    }  

      
    public void setUsername(String username) {  
        this.username = username;  
    }  

    public void setAccNo(String accNo) {  
        this.accNo = accNo;  
    }  

    public void setAccType(String accType) {  
        this.accType = accType;  
    }  

    public void setIfscCode(String ifscCode) {  
        this.ifscCode = ifscCode;  
    }  

    public void setBalance(double balance) {  
        this.balance = balance;  
    }  

    public void setPassword(String password) {  
        this.password = password;  
    }  

    public void setPhone(String phone) {  
        this.phone = phone;  
    }  

    public void setAddress(String address) {  
        this.address = address;  
    }  

    public void setBranch(String branch) {  
        this.branch = branch;  
    }  

     
    public void checkBalance() {  
        System.out.println("Current balance for account " + accNo + ": " + balance);  
    }  

    @Override 
    public String toString() {  
        return "Account{" +  
                "username='" + username + '\'' +  
                ", accNo='" + accNo + '\'' +  
                ", accType='" + accType + '\'' +  
                ", ifscCode='" + ifscCode + '\'' +  
                ", balance=" + balance +  
                ", phone='" + phone + '\'' +  
                ", address='" + address + '\'' +  
                ", branch='" + branch + '\'' +  
                '}';  
    }  
}