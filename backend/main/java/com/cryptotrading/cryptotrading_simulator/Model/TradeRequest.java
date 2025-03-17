package com.cryptotrading.cryptotrading_simulator.Model;

import java.util.Map;

/**
 * The TradeRequest class represents a request to execute a trade in the cryptocurrency trading simulator.
 * It contains information about the trade type, the cryptocurrency being traded,
 * the amount of cryptocurrency to trade, the user's account balance, and their current holdings.
 * <p>
 * This class is used to transfer trade data between different layers of the application and
 * to specify the necessary details for performing a trade action.
 * </p>
 */
public class TradeRequest {

    /* Trade type: BUY/SELL */
    private String type;

    /* Cryptocurrency symbol */
    private String symbol;

    /* Traded amount */
    private double amount;

    /* Current account balance */
    private double accountBalance;

    /* Current holdings */
    private Map<String, Double> holdings;

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

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Map<String, Double> getHoldings() {
        return holdings;
    }

    public void setHoldings(Map<String, Double> holdings) {
        this.holdings = holdings;
    }
}
