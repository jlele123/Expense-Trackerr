package github;

class Expense {
    private String description;
    private String amountWithSymbol;
    private String date;
    private double amount;

    public Expense(String description, String amountWithSymbol, String date, double amount) {
        this.description = description;
        this.amountWithSymbol = amountWithSymbol;
        this.date = date;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public String getAmountWithSymbol() {
        return amountWithSymbol;
    }

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

  
    public String toString() {
        return String.format("| %-20s | %-10s | %-16s |", description, amountWithSymbol, date);
    }
}