import org.apache.commons.math3.util.Precision;

import java.util.*;

public class Bank {

    static Scanner input = new Scanner(System.in);
    public ArrayList<Account> accounts = new ArrayList<>();

    private final String NO_ACCOUNTS_YET = "You have no accounts yet";

    public String listAccounts() {
        if (accounts.size() > 0) {
            StringBuilder allAccounts = new StringBuilder("");
            for (Account account : accounts) {
                var accountData = account.getCustomerFirstName() + " " + account.getCustomerLastName() + " " + account.getAccountNumber() +
                        " " + Precision.round(account.getAccountBalance(),2) + " " + account.getCurrency() + "\n";
                allAccounts.append(accountData);
            }
            return allAccounts.toString();
        }
        return NO_ACCOUNTS_YET;
    }

    public String showAccount(int accountNumber) {
        if (accounts.size() > 0) {
            for (Account account : accounts) {
                if (account.getAccountNumber() == accountNumber) {
                    return account.getCustomerFirstName() + " " + account.getCustomerLastName() + " " + account.getAccountNumber() + " " +
                            Precision.round(account.getAccountBalance(), 2) + " " + account.getCurrency();
                }
            }
        }
        return NO_ACCOUNTS_YET;
    }

    public String loadAccounts(String filePath) {
        CSVReader csvreader = new CSVReader();
        var newAccounts = csvreader.readAccountsFromCSV(filePath);
        accounts.clear();
        accounts.addAll(newAccounts);
        return accounts.size() + " account(s) were successfully added.";
    }

    public String transferMoney(Integer source, Integer target, Double amount, String currency) {
        String resultMessage = "No account with this number in database";
        var sourceAccount = getAccountByNumber(source);
        var targetAccount = getAccountByNumber(target);
        if (sourceAccount != null && targetAccount != null) {
            var sourceBalanceInPLN = calculateRateFromPLN(sourceAccount.getAccountBalance(), sourceAccount.getCurrency());
            var amountInPLN = calculateRateFromPLN(amount, currency);
            if (sourceBalanceInPLN < amountInPLN) {
                resultMessage = "Not enough money";
            } else {
                calculateAccountsBalanceAfterTransfer(sourceAccount, targetAccount, sourceBalanceInPLN, amountInPLN);
                resultMessage = "Your transfer was successful";
            }
        }
        return resultMessage;
    }

    private void calculateAccountsBalanceAfterTransfer(Account sourceAccount, Account targetAccount, double sourceBalanceInPLN, double amountInPLN) {
        var newSourceBalanceInPLN = sourceBalanceInPLN - amountInPLN;
        calculateSourceAccountBalanceAfterTransfer(sourceAccount, newSourceBalanceInPLN);
        var targetBalanceInPLN = calculateRateFromPLN(targetAccount.getAccountBalance(), targetAccount.getCurrency());
        var newTargetBalanceInPLN = targetBalanceInPLN + amountInPLN;
        calculateTargetAccountBalanceAfterTransfer(targetAccount, newTargetBalanceInPLN);
    }

    private void calculateSourceAccountBalanceAfterTransfer(Account sourceAccount, double newSourceBalanceInPLN) {
        if (sourceAccount.getCurrency().equals("PLN")) {
            sourceAccount.setAccountBalance(calculateRateFromPLN(newSourceBalanceInPLN, sourceAccount.getCurrency()));
        } else {
            sourceAccount.setAccountBalance(calculateRateToPLN(newSourceBalanceInPLN, sourceAccount.getCurrency()));
        }
    }

    private void calculateTargetAccountBalanceAfterTransfer(Account targetAccount, double newTargetBalanceInPLN) {
        if (targetAccount.getCurrency().equals("PLN")) {
            targetAccount.setAccountBalance(calculateRateFromPLN(newTargetBalanceInPLN, targetAccount.getCurrency()));
        } else {
            targetAccount.setAccountBalance(calculateRateToPLN(newTargetBalanceInPLN, targetAccount.getCurrency()));
        }
    }

    private Account getAccountByNumber(Integer number) {
        for (Account account : accounts) {
            if (account.getAccountNumber() == number) {
                return account;
            }
        }
        return null;
    }

     private double calculateRateFromPLN(double amount, String currency) {
        if (currency.equals("EUR")) {
            return amount * CurrencyRate.EUR;
        } else if (currency.equals("USD")) {
            return amount * CurrencyRate.USD;
        }
        return amount;
    }

    private double calculateRateToPLN(double amount, String currency) {
        if (currency.equals("EUR")) {
            return amount / CurrencyRate.EUR;
        } else if (currency.equals("USD")) {
            return amount / CurrencyRate.USD;
        }
        return amount;
    }
}
