import java.util.Scanner;

public class TransferApplication {

    static String sourceFile = "src/main/resources/accounts.csv";
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Bank bank = new Bank();
        var closeApplication = false;
        while (!closeApplication) {
            welcomeUser();
            closeApplication = selectAnOption(bank);
        }
    }

    private static boolean selectAnOption(Bank bank) {
        var menu = input.nextLine();
        var shouldClose = false;
        if (menu.equals("AddAccount")) {
            var account = createAccount();
            bank.accounts.add(account);
            System.out.println("Account created!");
        } else if (menu.matches("ShowAccount\\s\\d+")) {
            var strings = menu.split(" ");
            System.out.println(bank.showAccount(Integer.parseInt(strings[1])));
        } else if (menu.equals("ListAccounts")) {
            System.out.printf(bank.listAccounts());
        } else if (menu.equals("LoadAccounts")) {
            System.out.printf(bank.loadAccounts(sourceFile));
        } else if (menu.matches("TransferMoney\\s\\d+\\s\\d+\\s\\d+.\\d+\\s(USD|PLN|EUR)")) {
            var response = menu.split(" ");
            var sourceAccountNumber = Integer.parseInt(response[1]);
            var targetAccountNumber = Integer.parseInt(response[2]);
            var amount = Double.parseDouble(response[3]);
            var currency = response[4];
            System.out.println(bank.transferMoney(sourceAccountNumber, targetAccountNumber, amount, currency));
        } else if (menu.equals("Exit")) {
            shouldClose = true;
        } else {
            System.out.println("Command unknown");
        }
        return shouldClose;
    }

    private static void welcomeUser() {
        System.out.println("Please type the option you need:\n" +
                "1) AddAccount, " +
                "2) ShowAccount, " +
                "3) ListAccounts, " +
                "4) LoadAccounts, " +
                "5) TransferMoney, " +
                "6) Exit\n" +
                "If you want to make a transfer please provide: number of source account, number of target account, sum, and currency (PLN/USD/EUR)");
    }

    private static Account createAccount(){
        System.out.println("\nEnter your first name.");
        var firstname = input.next();
        System.out.println("\nEnter your last name.");
        var lastname = input.next();
        System.out.println("\nEnter amount to fund your new account.");
        var amountMoney = input.nextDouble();
        System.out.println("\nPlease enter the currency : PLN, EUR, USD");
        var currency = input.next();
        return new Account(firstname, lastname, amountMoney, currency);
    }
}
