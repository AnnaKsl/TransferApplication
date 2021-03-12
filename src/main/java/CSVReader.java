import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class CSVReader {

    static List<Account> readAccountsFromCSV(String fileName) {
        List<Account> accounts = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {
            String line = br.readLine();

            while (line != null) {

                String[] attributes = line.split(",");
                Account account = addAccount(attributes);
                accounts.add(account);
                line = br.readLine();
            }

        } catch (IOException ioe) {
        }

        return accounts;
    }

    private static Account addAccount(String[] metadata) {
        int accountNumber = Integer.parseInt(metadata[0]);
        String customerFirstName = metadata[1];
        String customerLastName = metadata[2];
        double balance = Double.parseDouble(metadata[3]);
        String currency = metadata[4];
        // create and return account of this metadata
        return new Account(accountNumber, customerFirstName, customerLastName, balance, currency);
    }
}


