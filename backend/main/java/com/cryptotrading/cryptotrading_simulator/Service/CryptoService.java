package com.cryptotrading.cryptotrading_simulator.Service;

import com.cryptotrading.cryptotrading_simulator.Model.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The CryptoService class is responsible for handling all the business logic related to cryptocurrency trading.
 * It manages the list of cryptocurrencies, allows for executing trades (buying and selling), simulates price updates,
 * and manages the user's account and transaction history.
 * <p>
 * This service uses simulated data for cryptocurrency prices and provides functionality for performing trades,
 * updating prices, and resetting or retrieving account information.
 * </p>
 */
@Service
public class CryptoService {

    /* List of the top 20 cryptocurrencies */
    private final List<Cryptocurrency> cryptocurrencies = new ArrayList<>();

    /* Map of the cryptocurrency symbol to the cryptocurrency */
    private final Map<String, Cryptocurrency> cryptoMap = new ConcurrentHashMap<>();

    /* Account */
    private Account account = new Account(10000.0);

    /* Random ID */
    private final Random random = new Random();

    /* Indicator whether to use simulated data */
    private boolean useSimulatedData = true;

    /**
     * Initializes the CryptoService with sample cryptocurrency data.
     * Adds top 20 cryptocurrencies with predefined values for price and 24h change.
     */
    public CryptoService() {
        initializeCryptos();
    }

    /**
     * Initializes a list of cryptocurrencies with sample data for testing purposes.
     * Adds a set of top 20 cryptocurrencies with name, symbol, price, and 24h change.
     */
    private void initializeCryptos() {
        // Add top 20 cryptocurrencies with sample data
        addCrypto("Bitcoin", "BTC", 43256.78, 1.23);
        addCrypto("Ethereum", "ETH", 2324.65, -0.45);
        addCrypto("Binance Coin", "BNB", 289.45, 0.76);
        addCrypto("Solana", "SOL", 98.34, 2.54);
        addCrypto("Ripple", "XRP", 0.52, -1.21);
        addCrypto("Cardano", "ADA", 0.41, 0.32);
        addCrypto("Dogecoin", "DOGE", 0.08, 1.11);
        addCrypto("Polkadot", "DOT", 6.78, -0.89);
        addCrypto("Avalanche", "AVAX", 34.56, 3.21);
        addCrypto("Chainlink", "LINK", 14.23, 0.56);
        addCrypto("Litecoin", "LTC", 70.98, -0.32);
        addCrypto("Polygon", "MATIC", 0.76, 1.45);
        addCrypto("Uniswap", "UNI", 6.89, -0.78);
        addCrypto("Cosmos", "ATOM", 9.45, 2.34);
        addCrypto("Stellar", "XLM", 0.12, 0.23);
        addCrypto("Monero", "XMR", 168.45, -1.34);
        addCrypto("Tron", "TRX", 0.11, 0.45);
        addCrypto("VeChain", "VET", 0.023, 1.56);
        addCrypto("Filecoin", "FIL", 4.32, -0.67);
        addCrypto("Algorand", "ALGO", 0.18, 0.87);
    }

    /**
     * Adds a new cryptocurrency to the list and map.
     *
     * @param name the name of the cryptocurrency
     * @param symbol the symbol of the cryptocurrency (e.g., "BTC")
     * @param price the current price of the cryptocurrency
     * @param change24h the percentage change in the price over the last 24 hours
     */
    private void addCrypto(String name, String symbol, double price, double change24h) {
        Cryptocurrency crypto = new Cryptocurrency(name, symbol, price, change24h);
        cryptocurrencies.add(crypto);
        cryptoMap.put(symbol, crypto);
    }

    /**
     * Updates the price and 24-hour price change of a specific cryptocurrency.
     *
     * @param symbol the symbol of the cryptocurrency to update
     * @param price the new price of the cryptocurrency
     * @param change24h the new 24-hour price change percentage
     */
    public void updateCryptoPrice(String symbol, double price, double change24h) {
        Cryptocurrency crypto = cryptoMap.get(symbol);
        if (crypto != null) {
            crypto.setPrice(price);
            crypto.setChange24h(change24h);
            useSimulatedData = false;
        }
    }

    /**
     * Simulates price updates for all cryptocurrencies in the system every 10 seconds.
     * This method is scheduled to run periodically and simulates price changes between -2% and +2%.
     */
    @Scheduled(fixedRate = 10000) // Every 10 seconds
    public void updatePrices() {
        if (useSimulatedData) {
            for (Cryptocurrency crypto : cryptocurrencies) {
                // Simulate price changes between -2% and +2%
                double changePercent = (random.nextDouble() * 4) - 2;
                double newPrice = crypto.getPrice() * (1 + (changePercent / 100));
                crypto.setPrice(newPrice);

                // Update 24h change (would be more complex in real app)
                double new24hChange = crypto.getChange24h() + ((random.nextDouble() * 1) - 0.5);
                // Keep 24h change within reasonable bounds
                if (new24hChange > 10) new24hChange = 10;
                if (new24hChange < -10) new24hChange = -10;
                crypto.setChange24h(new24hChange);
            }
        }
    }

    /**
     * Gets a list of all cryptocurrencies currently available in the system.
     *
     * @return a list of all cryptocurrencies
     */
    public List<Cryptocurrency> getAllCryptos() {
        return cryptocurrencies;
    }

    /**
     * Executes a trade (buy or sell) based on the provided trade request.
     * Validates inputs, checks for sufficient funds or holdings, and updates the account balance and holdings accordingly.
     *
     * @param request the trade request containing trade details (type, symbol, amount, etc.)
     * @return a response indicating the success or failure of the trade along with the updated account
     */
    public TradeResponse executeTrade(TradeRequest request) {
        String type = request.getType();
        String symbol = request.getSymbol();
        double amount = request.getAmount();

        // Validate inputs
        if (amount <= 0) {
            return new TradeResponse(false, "Amount must be positive", account);
        }

        Cryptocurrency crypto = cryptoMap.get(symbol);
        if (crypto == null) {
            return new TradeResponse(false, "Cryptocurrency not found", account);
        }

        double price = crypto.getPrice();
        double total = price * amount;

        if ("buy".equalsIgnoreCase(type)) {
            return executeBuy(symbol, amount, price, total);
        } else if ("sell".equalsIgnoreCase(type)) {
            return executeSell(symbol, amount, price, total);
        } else {
            return new TradeResponse(false, "Invalid trade type", account);
        }
    }

    /**
     * Executes a buy transaction, checking for sufficient funds and updating account balances and holdings.
     *
     * @param symbol the symbol of the cryptocurrency to buy
     * @param amount the amount of cryptocurrency to buy
     * @param price the price at which to buy the cryptocurrency
     * @param total the total cost of the purchase (price * amount)
     * @return a response indicating whether the buy was successful
     */
    private TradeResponse executeBuy(String symbol, double amount, double price, double total) {
        // Check if user has enough balance
        if (total > account.getBalance()) {
            return new TradeResponse(false, "Insufficient funds", account);
        }

        // Update account balance
        account.setBalance(account.getBalance() - total);

        // Update holdings
        Map<String, Double> holdings = account.getHoldings();
        double currentHolding = holdings.getOrDefault(symbol, 0.0);
        holdings.put(symbol, currentHolding + amount);

        // Update average buy price for P/L calculation
        Map<String, Double> avgPrices = account.getAverageBuyPrices();
        double currentTotal = currentHolding * (avgPrices.getOrDefault(symbol, 0.0));
        double newTotal = currentTotal + total;
        double newAmount = currentHolding + amount;
        avgPrices.put(symbol, newTotal / newAmount);

        // Add transaction record
        account.getTransactions().add(new Transaction("buy", symbol, amount, price, null));

        return new TradeResponse(true, "Purchase successful", account);
    }

    /**
     * Executes a sell transaction, checking for sufficient holdings and updating account balances and holdings.
     *
     * @param symbol the symbol of the cryptocurrency to sell
     * @param amount the amount of cryptocurrency to sell
     * @param price the price at which to sell the cryptocurrency
     * @param total the total revenue from the sale (price * amount)
     * @return a response indicating whether the sell was successful
     */
    private TradeResponse executeSell(String symbol, double amount, double price, double total) {
        // Check if user has enough of the cryptocurrency
        Map<String, Double> holdings = account.getHoldings();
        double currentHolding = holdings.getOrDefault(symbol, 0.0);

        if (amount > currentHolding) {
            return new TradeResponse(false, "Insufficient holdings", account);
        }

        // Calculate profit/loss
        Map<String, Double> avgPrices = account.getAverageBuyPrices();
        double avgBuyPrice = avgPrices.getOrDefault(symbol, price);
        double profitLoss = (price - avgBuyPrice) * amount;

        // Update account balance
        account.setBalance(account.getBalance() + total);

        // Update holdings
        holdings.put(symbol, currentHolding - amount);
        if (holdings.get(symbol) <= 0) {
            holdings.remove(symbol);
            avgPrices.remove(symbol);
        }

        // Add transaction record
        account.getTransactions().add(new Transaction("sell", symbol, amount, price, profitLoss));

        return new TradeResponse(true, "Sale successful", account);
    }

    /** Reset account to initial state */
    public Account resetAccount() {
        account = new Account(10000.0);
        return account;
    }

    /** Get account information */
    public Account getAccount() {
        return account;
    }
}
