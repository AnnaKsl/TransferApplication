import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {

    private final String NO_ACCOUNT_MESSAGE = "You have no accounts yet";

    private final Bank bank = new Bank();

    @BeforeEach
    public void setup(){
        Account account = new Account(1, "Jay", "Pritchett", 5000.00, "EUR");
        bank.accounts.add(account);
    }

    @Test
    public void showAccountWhenThereAreNoAccountsTest() {
        var emptyBank = new Bank();
        int accountNumber = 1;
        Assertions.assertEquals(NO_ACCOUNT_MESSAGE, emptyBank.showAccount(accountNumber));
    }

    @Test
    public void showAccountWhenThereIsOneAccountTest() {
        int accountNumber = 1;
        var expectedResponse = "Jay Pritchett 1 5000.0 EUR";
        Assertions.assertEquals(expectedResponse, bank.showAccount(accountNumber));
    }

    @Test
    public void listAccountTestIfThereAreNoAccountsTest() {
        var emptyBank = new Bank();
        Assertions.assertEquals(NO_ACCOUNT_MESSAGE, emptyBank.listAccounts());
    }

    @Test
    public void listAccountIfThereIsOneAccountTest() {
        var expectedMessage = "Jay Pritchett 1 5000.0 EUR\n";
        Assertions.assertEquals(expectedMessage, bank.listAccounts());
    }

    @Test
    void loadAccountsTest() {
        var expectedMessage = "2 account(s) were successfully added.";
        String TEST_PATH = "src/test/resources/testAccounts.csv";
        Assertions.assertEquals(expectedMessage, bank.loadAccounts(TEST_PATH));
    }

    @Test
    void loadAccountsNoSourceFileTest() {
        var expectedMessage = "0 account(s) were successfully added.";
        String WRONG_TEST_PATH = "wrong/test/path/testAccounts.csv";
        Assertions.assertEquals(expectedMessage, bank.loadAccounts(WRONG_TEST_PATH));
    }

    @Test
    void loadAccountsEmptyFileTest() {
        var expectedMessage = "0 account(s) were successfully added.";
        String EMPTY_FILE_TEST_PATH = "src/test/resources/emptyAccounts.csv";
        Assertions.assertEquals(expectedMessage, bank.loadAccounts(EMPTY_FILE_TEST_PATH));
    }

    @Test
    void transferMoneyTest() {
        var expectedMessage = "Your transfer was successful";
        var secondAccount = new Account(2, "Gloria", "Pritchett", 5000.00, "PLN");
        bank.accounts.add(secondAccount);
        var bankResponse = bank.transferMoney(1, 2, 2000.00, "PLN");
        Assertions.assertEquals(expectedMessage, bankResponse);
    }

    @Test
    void transferMoneyNoEnoughMoneyTest() {
        var expectedMessage = "Not enough money";
        var secondAccount = new Account(2, "Gloria", "Pritchett", 5000.00, "PLN");
        bank.accounts.add(secondAccount);
        var bankResponse = bank.transferMoney(1, 2, 20000.00, "EUR");
        Assertions.assertEquals(expectedMessage, bankResponse);
    }

    @Test
    void transferMoneyNoSecondAccountTest() {
        var expectedMessage = "No account with this number in database";
        var bankResponse = bank.transferMoney(1, 2, 2000.00, "PLN");
        Assertions.assertEquals(expectedMessage, bankResponse);
    }

    @Test
    void transferMoneyNoAccountsTest() {
        var emptyBank = new Bank();
        var expectedMessage = "No account with this number in database";
        var bankResponse = emptyBank.transferMoney(1, 2, 2000.00, "PLN");
        Assertions.assertEquals(expectedMessage, bankResponse);
    }
}
