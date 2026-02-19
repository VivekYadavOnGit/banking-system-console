package services;

import domains.Account;
import domains.Transaction;

import java.util.List;
import java.util.Map;

public interface BankService {
    String openAccount(String name, String email, String accountType);

    List<Account> listAccounts();

    void deposit(String accountNumber, Double amount, String note);

    void withdraw(String accountNumber, Double amount, String withdraw);

    void transfer(String from, String to, Double amount, String note);

    List<Transaction> getStatement(String accountNumber);

    List<Account> searchAccountsByCustomerName(String customerName);
}
