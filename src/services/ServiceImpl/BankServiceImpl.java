package services.ServiceImpl;

import domains.Account;
import domains.Customer;
import domains.Transaction;
import domains.Type;
import exceptions.AccountNotFoundException;
import exceptions.InsufficientBalance;
import exceptions.ValidationException;
import repository.AccountRepository;
import repository.CustomerRepository;
import repository.TransactionRepository;
import services.BankService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BankServiceImpl implements BankService {

    private final AccountRepository accountRepository = new AccountRepository();
    private final TransactionRepository transactionRepository = new TransactionRepository();
    private final CustomerRepository customerRepository = new CustomerRepository();

    @Override
    public String openAccount(String name, String email, String accountType) {
        String customerId = UUID.randomUUID().toString();
        String accountNumber = getAccountNumber();
        Customer customer = new Customer(customerId, name, email);
        customerRepository.save(customer);
        Account account = new Account(accountNumber, (double) 0, customerId, accountType);
        accountRepository.save(account);
        return accountNumber;
    }

    @Override
    public List<Account> listAccounts() {
        return accountRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Account::getAccountNumber))
                .collect(Collectors.toList());
    }

    @Override
    public void deposit(String accountNumber, Double amount, String note) {
        Account account = accountRepository.findByNumber(accountNumber).orElseThrow(() -> new AccountNotFoundException("Account not found: "+ accountNumber));
        account.setBalance(account.getBalance() + amount);
        Transaction transaction = new Transaction(account.getAccountNumber(),
                UUID.randomUUID().toString(), amount, LocalDateTime.now(), Type.DEPOSIT, note);
        transactionRepository.add(transaction);
    }

    @Override
    public void withdraw(String accountNumber, Double amount, String note) {
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: "+ accountNumber));
        if(account.getBalance() < amount || account.getBalance() < 0)
            throw new InsufficientBalance("Insufficient Balance...");
        account.setBalance(account.getBalance() - amount);
        Transaction transaction = new Transaction(account.getAccountNumber(),
                UUID.randomUUID().toString(), amount, LocalDateTime.now(), Type.WITHDRAW, note);
        transactionRepository.add(transaction);
    }

    @Override
    public void transfer(String from, String to, Double amount, String note) {
        if(from.equals(to))
            throw new ValidationException("Cannot transfer to own Account!!!!!");

        Account fromAccount = accountRepository.findByNumber(from)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: "+ from));
        Account toAccount = accountRepository.findByNumber(to)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: "+ to));

        if(fromAccount.getBalance() < amount || toAccount.getBalance() < 0)
            throw new InsufficientBalance("Insufficient Balance...");

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        Transaction fromTransaction = new Transaction(fromAccount.getAccountNumber(),
                UUID.randomUUID().toString(), amount, LocalDateTime.now(), Type.TRANSFER_OUT, note);
        transactionRepository.add(fromTransaction);
        Transaction toTransaction = new Transaction(toAccount.getAccountNumber(),
                UUID.randomUUID().toString(), amount, LocalDateTime.now(), Type.TRANSFER_IN, note);
        transactionRepository.add(toTransaction);
    }

    @Override
    public List<Transaction> getStatement(String accountNumber) {
        // Validate that account exists
//        accountRepository.findByNumber(accountNumber)
//                .orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));
        return transactionRepository.findByAccountNo(accountNumber)
                .stream()
                .sorted(Comparator.comparing(Transaction::getTimeStamp))
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> searchAccountsByCustomerName(String q) {
        String query = (q == null) ? "" : q.toLowerCase();
//        List<Account> result = new ArrayList<>();
//        for (Customer c : customerRepository.findAll()){
//            if (c.getName().toLowerCase().contains(query))
//                result.addAll(accountRepository.findByCustomerId(c.getId()));
//        }
//        result.sort(Comparator.comparing(Account::getAccountNumber));

        return customerRepository.findAll().stream()
                .filter(c -> c.getName().toLowerCase().contains(query))
                .flatMap(c -> accountRepository.findByCustomerId(c.getId()).stream())
                .sorted(Comparator.comparing(Account::getAccountNumber))
                .collect(Collectors.toList());

//        return result;
    }
    private String getAccountNumber() {
        int size = accountRepository.findAll().size()+1;
        return String.format("AC%06d", size);
    }
}
