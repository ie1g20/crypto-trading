package com.cryptotrading.cryptotrading_simulator.Model;

/**
 * The Cryptocurrency class represents a cryptocurrency and its associated details,
 * such as its name, symbol, price, and the change in price over the last 24 hours.
 * <p>
 * This class is used to store information about individual cryptocurrencies,
 * providing properties to track their current price and the price fluctuation.
 * </p>
 */
public class Cryptocurrency {
    /* Name of the cryptocurrency */
    private String name;

    /* Symbol of the cryptocurrency */
    private String symbol;

    /* Price of the cryptocurrency */
    private double price;

    /* Change in the price of the cryptocurrency in the last 24h */
    private double change24h;

    /**
     * Constructor to initialize a cryptocurrency with its name, symbol, price, and
     * change in price over the last 24 hours.
     *
     * @param name the name of the cryptocurrency (e.g., Bitcoin)
     * @param symbol the symbol of the cryptocurrency (e.g., BTC)
     * @param price the current price of the cryptocurrency
     * @param change24h the percentage change in the price of the cryptocurrency over the last 24 hours
     */
    public Cryptocurrency(String name, String symbol, double price, double change24h) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.change24h = change24h;
    }

    /* Getters and setters */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getChange24h() {
        return change24h;
    }

    public void setChange24h(double change24h) {
        this.change24h = change24h;
    }
}
