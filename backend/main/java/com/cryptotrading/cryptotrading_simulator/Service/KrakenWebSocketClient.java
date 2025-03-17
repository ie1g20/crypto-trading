package com.cryptotrading.cryptotrading_simulator.Service;

import com.cryptotrading.cryptotrading_simulator.Model.Cryptocurrency;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A WebSocket client that connects to Kraken's WebSocket API to receive real-time cryptocurrency ticker updates.
 * It subscribes to ticker updates for a predefined set of cryptocurrency pairs and updates the cryptocurrency data
 * in the {@link CryptoService}.
 */
@Service
@ClientEndpoint
public class KrakenWebSocketClient {

    /* Crypto service */
    private final CryptoService cryptoService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Session session;

    /* Map cryptocurrency symbol to kraken symbols */
    private final Map<String, String> symbolMap = new HashMap<>();

    /**
     * Constructs a KrakenWebSocketClient.
     *
     * @param cryptoService the service that handles cryptocurrency data and updates
     */
    public KrakenWebSocketClient(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
        initializeSymbolMap();
    }

    /**
     * Initializes the mapping between internal cryptocurrency symbols and Kraken's WebSocket symbols.
     */
    private void initializeSymbolMap() {
        symbolMap.put("BTC", "XBT/USD");
        symbolMap.put("ETH", "ETH/USD");
        symbolMap.put("BNB", "BNB/USD");
        symbolMap.put("SOL", "SOL/USD");
        symbolMap.put("XRP", "XRP/USD");
        symbolMap.put("ADA", "ADA/USD");
        symbolMap.put("DOGE", "DOGE/USD");
        symbolMap.put("DOT", "DOT/USD");
        symbolMap.put("AVAX", "AVAX/USD");
        symbolMap.put("LINK", "LINK/USD");
        symbolMap.put("LTC", "LTC/USD");
        symbolMap.put("MATIC", "MATIC/USD");
        symbolMap.put("UNI", "UNI/USD");
        symbolMap.put("ATOM", "ATOM/USD");
        symbolMap.put("XLM", "XLM/USD");
        symbolMap.put("XMR", "XMR/USD");
        symbolMap.put("TRX", "TRX/USD");
        symbolMap.put("VET", "VET/USD");
        symbolMap.put("FIL", "FIL/USD");
        symbolMap.put("ALGO", "ALGO/USD");
    }

    /**
     * Establishes a connection to the Kraken WebSocket server. This method is invoked after the bean is constructed
     * and the application context is ready. It attempts to connect to the WebSocket server and log any errors.
     */
    @PostConstruct
    public void connect() {
        try {
            // Create a standard javax WebSocket client
            WebSocketContainer container = javax.websocket.ContainerProvider.getWebSocketContainer();

            // Connect to the WebSocket server
            this.session = container.connectToServer(
                    this,  // this class uses @ClientEndpoint
                    new URI("wss://ws.kraken.com")
            );

            System.out.println("Connected to Kraken WebSocket");

        } catch (Exception e) {
            System.err.println("Error connecting to Kraken WebSocket: " + e.getMessage());
            e.printStackTrace();
            // Fall back to simulated data
        }
    }

    /**
     * Called when the WebSocket connection is successfully established.
     * Subscribes to ticker updates for all supported cryptocurrency pairs.
     *
     * @param session the WebSocket session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        // Subscribe to tickers once connected
        subscribeToTickers();
    }

    /**
     * Subscribes to the ticker updates for all cryptocurrency pairs in the symbol map.
     * It sends a WebSocket subscription message to Kraken's server to receive real-time data.
     */
    private void subscribeToTickers() {
        try {
            // Create subscription request for all pairs
            Map<String, Object> subscription = new HashMap<>();
            subscription.put("name", "ticker");

            Map<String, Object> message = new HashMap<>();
            message.put("method", "subscribe");
            message.put("params", new Object[]{subscription, symbolMap.values().toArray()});

            String subscriptionMessage = objectMapper.writeValueAsString(message);
            this.session.getBasicRemote().sendText(subscriptionMessage);
            System.out.println("Subscribed to tickers");
        } catch (Exception e) {
            System.err.println("Error subscribing to tickers: " + e.getMessage());
        }
    }

    /**
     * Called when a message is received from the Kraken WebSocket server.
     * It processes the message and updates the cryptocurrency data in the {@link CryptoService}.
     *
     * @param message the WebSocket message in JSON format
     */
    @OnMessage
    public void onMessage(String message) {
        try {
            JsonNode root = objectMapper.readTree(message);

            // Check if this is a ticker update (not a subscription confirmation)
            if (root.isArray() && root.size() > 1 && root.get(1).isObject() && root.get(2).isArray()) {
                String pair = root.get(3).asText();
                JsonNode tickerData = root.get(1);

                // Find our symbol from Kraken's pair
                String ourSymbol = null;
                for (Map.Entry<String, String> entry : symbolMap.entrySet()) {
                    if (entry.getValue().equals(pair)) {
                        ourSymbol = entry.getKey();
                        break;
                    }
                }

                if (ourSymbol != null) {
                    // Extract price from ticker data
                    double price = tickerData.get("c").get(0).asDouble();

                    // Calculate 24h change
                    double open24h = tickerData.get("o").get(0).asDouble();
                    double change24h = ((price - open24h) / open24h) * 100;

                    // Update our cryptocurrency data
                    cryptoService.updateCryptoPrice(ourSymbol, price, change24h);
                }
            }
        } catch (IOException e) {
            System.err.println("Error processing WebSocket message: " + e.getMessage());
        }
    }

    /**
     * Called when the WebSocket connection is closed.
     *
     * @param session the WebSocket session
     * @param reason  the reason for closing the connection
     */
    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("Disconnected from Kraken WebSocket: " + reason);
    }

    /**
     * Called when an error occurs during WebSocket communication.
     *
     * @param session the WebSocket session
     * @param error   the error that occurred
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.err.println("WebSocket error: " + error.getMessage());
    }

    /**
     * Closes the WebSocket connection when the application is shutting down.
     * This method is invoked before the bean is destroyed.
     */
    @PreDestroy
    public void disconnect() {
        if (this.session != null && this.session.isOpen()) {
            try {
                this.session.close();
            } catch (IOException e) {
                System.err.println("Error closing WebSocket connection: " + e.getMessage());
            }
        }
    }
}