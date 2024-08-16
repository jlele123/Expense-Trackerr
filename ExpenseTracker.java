package github;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ExpenseTracker {
    private List<Expense> expenses;

    public ExpenseTracker() {
        this.expenses = new ArrayList<>();
    }

    public void addExpense(String description, String amountWithSymbol, String date) {
        double amount = parseAmount(amountWithSymbol);
        Expense expense = new Expense(description, amountWithSymbol, formatDate(date), amount);
        expenses.add(expense);
    }

    public void viewExpenses(Scanner scanner) {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            while (true) {
                System.out.println("+----+----------------------+------------+------------------+");
                System.out.println("| #  | Description          | Amount     | Date             |");
                System.out.println("+----+----------------------+------------+------------------+");
                int counter = 1;
                for (Expense expense : expenses) {
                    System.out.printf("|%-1d | %s%n", counter++, expense);
                }
                System.out.println("+----+----------------------+------------+------------------+");

                System.out.println("\nOptions:");
                System.out.println("1. Delete an Expense");
                System.out.println("2. Return to Main Menu");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (choice == 1) {
                    System.out.print("Enter the number of the expense to delete: ");
                    int expenseNumber = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    if (expenseNumber > 0 && expenseNumber <= expenses.size()) {
                        expenses.remove(expenseNumber - 1);
                        System.out.println("Expense deleted successfully!");
                    } else {
                        System.out.println("Invalid expense number.");
                    }
                } else if (choice == 2) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
                System.out.println(); // Add a space between outputs
            }
        }
    }

    private double parseAmount(String input) {
        return Double.parseDouble(input.replaceAll("[^\\d.]", ""));
    }

    private String getValidDate(Scanner scanner) {
        String date;
        while (true) {
            System.out.print("Enter date (yyyy-mm-dd): ");
            date = scanner.nextLine();
            if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                break;
            } else {
                System.out.println("Invalid date format. Please enter the date in yyyy-mm-dd format.");
            }
        }
        return date;
    }

    private String formatDate(String dateInput) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        try {
            Date date = inputFormat.parse(dateInput);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateInput; // return original if parsing fails
        }
    }

    private void clearConsole() {
        try {
            if (System.getProperty("os.name").startsWith("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Could not clear the console.");
        }
    }

    public static void main(String[] args) {
        ExpenseTracker tracker = new ExpenseTracker();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nExpense Tracker Menu:");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            System.out.println("---------------------------------------");
            switch (choice) {
                case 1:
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    String amountWithSymbol = scanner.nextLine();
                    String date = tracker.getValidDate(scanner);
                    tracker.addExpense(description, amountWithSymbol, date);
                    System.out.println("Expense added successfully!");
                    System.out.println(); // Add a space between outputs
                    break;
                case 2:
                    System.out.println("Viewing all expenses:");
                    tracker.viewExpenses(scanner);
                    System.out.println(); // Add a space between outputs
                    break;
                case 3:
                    tracker.clearConsole();
                    System.out.println("Exited Application");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println(); // Add a space between outputs
            }
            System.out.println("---------------------------------------");
        }
    }
}