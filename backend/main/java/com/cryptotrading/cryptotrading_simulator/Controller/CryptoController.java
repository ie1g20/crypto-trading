package com.cryptotrading.cryptotrading_simulator.Controller;

import com.cryptotrading.cryptotrading_simulator.Model.*;
import com.cryptotrading.cryptotrading_simulator.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The CryptoController class provides the RESTful API endpoints for managing cryptocurrency trades
 * and interacting with the user's account in the crypto trading simulator.
 * <p>
 * It exposes endpoints to get the list of cryptocurrencies, execute trades, reset accounts, and
 * retrieve account information.
 * </p>
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CryptoController {
    private final CryptoService cryptoService;

    /**
     * Constructor to initialize the CryptoController with the CryptoService.
     *
     * @param cryptoService the service used to interact with cryptocurrency data and perform trades
     */
    @Autowired
    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    /**
     * Endpoint to retrieve a list of all cryptocurrencies.
     *
     * @return a list of Cryptocurrency objects representing all available cryptocurrencies
     */
    @GetMapping("/cryptos")
    public List<Cryptocurrency> getAllCryptos() {
        return cryptoService.getAllCryptos();
    }

    /**
     * Endpoint to execute a trade based on the provided trade request.
     *
     * @param request the trade request containing the details of the trade to be executed
     * @return a TradeResponse object containing the details of the executed trade
     */
    @PostMapping("/trade")
    public TradeResponse executeTrade(@RequestBody TradeRequest request) {
        return cryptoService.executeTrade(request);
    }

    /**
     * Endpoint to reset the user's account and return the updated account information.
     *
     * @return the updated Account object after the reset
     */
    @PostMapping("/reset")
    public Account resetAccount() {
        return cryptoService.resetAccount();
    }

    /**
     * Endpoint to retrieve the current account information of the user.
     *
     * @return the Account object containing the user's account details
     */
    @GetMapping("/account")
    public Account getAccount() {
        return cryptoService.getAccount();
    }
}
