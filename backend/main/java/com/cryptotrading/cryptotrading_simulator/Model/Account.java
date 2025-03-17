package com.cryptotrading.cryptotrading_simulator.Model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Account class represents a user's cryptocurrency account in the trading simulator.
 * It contains the balance, holdings, transaction history, and average buy prices for each cryptocurrency.
 * <p>
 * The account allows tracking of the user's available balance, the number of cryptocurrencies held,
 * the details of transactions made, and the average buy prices for each cryptocurrency,
 * which can be used for profit and loss calculations.
 * </p>
 */
public class Account {

    /* Current balance */
    private double balance;

    /* List of the currently held cryptocurrencies */
    private Map<String, Double> holdings;

    /* List of transaction */
    private List<Transaction> transactions;

    /* List of the average buy prices */
    private Map<String, Double> averageBuyPrices;

    /**
     * Constructor to initialize the account with an initial balance.
     *
     * @param initialBalance the initial balance to set in the account
     */
    public Account(double initialBalance) {
        this.balance = initialBalance;
        this.holdings = new HashMap<>();
        this.transactions = new ArrayList<>();
        this.averageBuyPrices = new HashMap<>();
    }

    /* Getters and setters */
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Map<String, Double> getHoldings() {
        return holdings;
    }

    public void setHoldings(Map<String, Double> holdings) {
        this.holdings = holdings;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Map<String, Double> getAverageBuyPrices() {
        return averageBuyPrices;
    }

    public void setAverageBuyPrices(Map<String, Double> averageBuyPrices) {
        this.averageBuyPrices = averageBuyPrices;
    }
}