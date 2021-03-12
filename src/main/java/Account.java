public class Account {
    private double AccountBalance;
    private String currency;
    private int accountNumber;
    private String customerFirstName;
    private String customerLastName;
    private static int lastAccountNumber = 0;

    public Account(String customerFirstName, String customerLastName, double AccountBalance, String currency) {
        this.AccountBalance = AccountBalance;
        this.customerFirstName =customerFirstName;
        this.customerLastName = customerLastName;
        this.currency = currency;
        this.accountNumber = lastAccountNumber;
        lastAccountNumber++;
    }

    public Account(int accountNumber, String customerFirstName, String customerLastName, double AccountBalance, String currency) {
        this.accountNumber = accountNumber;
        this.customerFirstName =customerFirstName;
        this.customerLastName = customerLastName;
        this.AccountBalance = AccountBalance;
        this.currency = currency;
    }

    public void setAccountBalance(double balance){
        this.AccountBalance = balance;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public double getAccountBalance() {
        return AccountBalance;
    }

    public int getAccountNumber(){
        return accountNumber;
    }
    public String getCurrency(){
        return currency;
    }


}


