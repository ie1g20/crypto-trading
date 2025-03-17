package com.cryptotrading.cryptotrading_simulator.Model;

import java.time.LocalDateTime;

/**
 * The Transaction class represents a cryptocurrency transaction, such as a buy or sell order.
 * It contains details about the type of transaction, the cryptocurrency involved, the amount,
 * price, total cost, profit/loss (for sell transactions), and the timestamp of the transaction.
 * <p>
 * This class is used to record and track the transactions made in the cryptocurrency trading simulator.
 * </p>
 */
public class Transaction {

    /* Trade type: BUY/SEL */
    private String type;

    /* Cryptocurrency symbol */
    private String symbol;

    /* Traded amount */
    private double amount;

    /* Price of cryptocurrency */
    private double price;

    /* Total cost of the transaction */
    private double total;

    /* Profit indicator */
    private Double profitLoss; // null for buys, calculated for sells

    /* Timestamp */
    private LocalDateTime timestamp;

    /**
     * Constructor to initialize a transaction with the specified details.
     * This constructor calculates the total value of the transaction (amount * price)
     * and sets the timestamp to the current time.
     *
     * @param type the type of the transaction ("buy" or "sell")
     * @param symbol the symbol of the cryptocurrency involved in the transaction (e.g., "BTC")
     * @param amount the amount of cryptocurrency involved in the transaction
     * @param price the price at which the cryptocurrency is bought or sold
     * @param profitLoss the profit or loss from the transaction (null for buy, calculated for sell)
     */
    public Transaction(String type, String symbol, double amount, double price, Double profitLoss) {
        this.type = type;
        this.symbol = symbol;
        this.amount = amount;
        this.price = price;
        this.total = amount * price;
        this.profitLoss = profitLoss;
        this.timestamp = LocalDateTime.now();
    }

    /* Getters and setters */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Double getProfitLoss() {
        return profitLoss;
    }

    public void setProfitLoss(Double profitLoss) {
        this.profitLoss = profitLoss;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}