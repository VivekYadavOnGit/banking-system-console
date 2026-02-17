package app;

import services.BankService;
import services.ServiceImpl.BankServiceImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankService bankService = new BankServiceImpl();
        System.out.println("Welcome to Console Bank...");
        boolean running = true;
        while(running){
        System.out.println("""
               1) Open Account
               2) Deposit
               3) Withdraw
               4) Transfer
               5) Account Statement
               6) List Accounts
               7) Search Account by Name
               0) Exit
               """);
            System.out.print("CHOOSE: ");
            String choice = scanner.nextLine().trim();
            System.out.println("Choice: " + choice);
            switch (choice){
                case "1" -> openAccount(scanner, bankService);
                case "2" -> deposit(scanner, bankService);
                case "3" -> withdraw(scanner, bankService);
                case "4" -> transfer(scanner, bankService);
                case "5" -> statement(scanner, bankService);
                case "6" -> listAccounts(scanner, bankService);
                case "7" -> searchAccounts(scanner, bankService);
                case "0" -> running = false;
            }
        }
    }

    private static void openAccount(Scanner scanner, BankService bankService) {
        System.out.print("Customer Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Customer E-mail: ");
        String email = scanner.nextLine().trim();
        System.out.print("Account Type (SAVINGS/CURRENT): ");
        String type = scanner.nextLine().trim();
        System.out.print("Initial Deposit(optional, blank for 0): ");
        String amountStr = scanner.nextLine().trim();
        Double initialDeposit = Double.valueOf(amountStr);
        String accountNumber = bankService.openAccount(name, email, type);
        if(initialDeposit > 0)
            bankService.deposit(accountNumber, initialDeposit, "Initial Deposit");
        System.out.println(">>>>>>> ACCOUNT CREATED SUCCESSFULLY <<<<<<<<");
    }

    private static void deposit(Scanner scanner, BankService bankService) {
        System.out.print("Bank Account Number: ");
        String accountNumber = scanner.nextLine().trim();
        System.out.print("Amount: ");
        Double amount = Double.valueOf(scanner.nextLine().trim());
        bankService.deposit(accountNumber,amount, "Deposit");
        System.out.println("Deposit Successfully!!!!");
    }

    private static void withdraw(Scanner scanner, BankService bankService) {
        System.out.print("Bank Account Number: ");
        String accountNumber = scanner.nextLine().trim();
        System.out.print("Amount: ");
        Double amount = Double.valueOf(scanner.nextLine().trim());
        bankService.withdraw(accountNumber,amount, "Withdraw");
        System.out.println("Withdraw Successfully!!!!");
    }

    private static void transfer(Scanner scanner, BankService bankService) {
        System.out.print("From Account Number: ");
        String from = scanner.nextLine().trim();
        System.out.print("To Account Number: ");
        String to = scanner.nextLine().trim();
        System.out.print("Amount: ");
        Double amount = Double.valueOf(scanner.nextLine().trim());
        bankService.transfer(from, to, amount, "Transfer");
        System.out.println("Money Transferred Successfully!!!!");
    }
// File: `src/app/Main.java`
private static void statement(Scanner scanner, BankService bankService) {
    System.out.print("Account number: ");
    String account = scanner.nextLine().trim();
    if (account.isEmpty()) {
        System.out.println("Account number cannot be empty.");
        return;
    }
    var transactions = bankService.getStatement(account);
    if (transactions == null || transactions.isEmpty()) {
        System.out.println("No transactions found for account: " + account);
        return;
    }
    transactions.forEach(t -> {
        System.out.println(t.getTimeStamp() + " | " + t.getType() + " | " + t.getAmount() + " | " + t.getNote());
    });
}
//    private static void statement(Scanner scanner, BankService bankService) {
//        System.out.println("Account number: ");
//        String account = scanner.nextLine().trim();
//        bankService.getStatement(account).forEach(t -> {
//            System.out.println(t.getTimeStamp() + " | " + t.getType() + " | " + t.getAmount() + " | " + t.getNote());
//        });
//    }

    private static void listAccounts(Scanner scanner, BankService bankService) {
        bankService.listAccounts().forEach(a -> {
            System.out.println(a.getAccountNumber() + " | " + a.getAccountType() + " | " + a.getBalance());
        });
    }

    private static void searchAccounts(Scanner scanner, BankService bankService) {
        System.out.print("Customer name contains: ");
        String q = scanner.nextLine().trim();
        var accounts = bankService.searchAccountsByCustomerName(q);
        if (accounts.isEmpty()) {
            System.out.println("No accounts found for customer name containing: " + q);
        } else {
            accounts.forEach(account -> {
                System.out.println(account.getAccountNumber() + " | " + account.getAccountType() + " | " + account.getBalance());
            });
        }
    }
}
