package com.cryptotrading.cryptotrading_simulator.Model;

/**
 * The TradeResponse class represents the response returned after executing a trade in the cryptocurrency trading simulator.
 * It contains information about whether the trade was successful, a message describing the result,
 * and the updated account information after the trade.
 * <p>
 * This class is used to encapsulate the result of a trade operation, allowing the application
 * to provide feedback to the user and update their account details accordingly.
 * </p>
 */
public class TradeResponse {

    /* Success indicator */
    private boolean success;

    /* Result */
    private String message;

    /* Updated account */
    private Account account;

    /**
     * Constructor to initialize a TradeResponse with the success status, message,
     * and updated account details.
     *
     * @param success indicates whether the trade was successful (true) or not (false)
     * @param message a message describing the outcome of the trade (e.g., error message or success message)
     * @param account the updated account information after the trade is executed
     */
    public TradeResponse(boolean success, String message, Account account) {
        this.success = success;
        this.message = message;
        this.account = account;
    }

    /* Getters and setters */
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

