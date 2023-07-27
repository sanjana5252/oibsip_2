import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

      
        BankAccount account = new BankAccount("Sanjana Singh", "1234", 1000.0);

        System.out.println("Welcome to the ATM!");

        while (true) {
            String user_name;
            System.out.println("Enter Your User Name: ");
            user_name =scanner.nextLine();
            
            System.out.print("Enter your PIN: ");
            
            String enteredPIN = scanner.nextLine();
            if (login(account, enteredPIN)) {
                ATM atm = new ATM(account);
                atm.displayMenu();
                break;
            } else {
                System.out.println("Invalid PIN. Please try again.");
            }
        }
    }

    public static boolean login(BankAccount account, String enteredPIN) {
        return account.checkPIN(enteredPIN);
    }
}

class ATM {
    private BankAccount account;
    private TransactionHistory transactionHistory;

    public ATM(BankAccount account) {
        this.account = account;
        this.transactionHistory = new TransactionHistory();
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n----- Menu -----");
            System.out.println("1. View Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transaction History");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.println("Current Balance: Rs. " + account.getBalance());
                    break;
                case 2:
                    System.out.print("Enter the deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine(); 
                    account.deposit(depositAmount);
                    transactionHistory.addTransaction(new DepositTransaction(depositAmount));
                    System.out.println("Deposit successful. Current Balance: Rs. " + account.getBalance());
                    break;
                case 3:
                    System.out.print("Enter the withdrawal amount: ");
                    double withdrawalAmount = scanner.nextDouble();
                    scanner.nextLine(); 
                    if (account.withdraw(withdrawalAmount)) {
                        transactionHistory.addTransaction(new WithdrawTransaction(withdrawalAmount));
                        System.out.println("Withdrawal successful. Current Balance: Rs. " + account.getBalance());
                    } else {
                        System.out.println("Insufficient balance.");
                    }
                    break;
                case 4:
                    transactionHistory.displayTransactionHistory();
                    break;
                case 5:
                    System.out.println("Thank you for using ");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

class BankAccount {
    private String accountHolderName;
    private String pin;
    private double balance;

    public BankAccount(String accountHolderName, String pin, double balance) {
        this.accountHolderName = accountHolderName;
        this.pin = pin;
        this.balance = balance;
    }

    public boolean checkPIN(String enteredPIN) {
        return pin.equals(enteredPIN);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

class TransactionHistory {
    private StringBuilder history;

    public TransactionHistory() {
        this.history = new StringBuilder();
    }

    public void addTransaction(Transaction transaction) {
        history.append(transaction.getTransactionDetails()).append("\n");
    }

    public void displayTransactionHistory() {
        System.out.println("\nTransaction History:");
        System.out.println(history);
    }
}

interface Transaction {
    String getTransactionDetails();
}

class DepositTransaction implements Transaction {
    private double amount;

    public DepositTransaction(double amount) {
        this.amount = amount;
    }

    public String getTransactionDetails() {
        return "Deposit: + " + amount;
    }
}

class WithdrawTransaction implements Transaction {
    private double amount;

    public WithdrawTransaction(double amount) {
        this.amount = amount;
    }


    public String getTransactionDetails() {
        return "Withdrawal: - " + amount;
    }
}
