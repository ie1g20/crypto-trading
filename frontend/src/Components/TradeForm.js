import React, { useState } from 'react';

/**
 * TradeForm component that allows users to trade cryptocurrencies.
 * It supports both buying and selling of cryptocurrencies. 
 * The form validates the input, checks if the user has sufficient funds or holdings, and processes the trade.
 *
 * @param {Object} props - Component props
 * @param {Array} props.cryptos - An array of cryptocurrency objects with the following properties:
 *                                - `name`: The name of the cryptocurrency (string)
 *                                - `symbol`: The symbol of the cryptocurrency (string, e.g. "BTC")
 *                                - `price`: The current price of the cryptocurrency in USD (number)
 *
 * @param {Function} props.onTrade - A callback function that handles the trading action.
 *                                    It takes the trade type ('buy' or 'sell'), the cryptocurrency symbol, and the amount.
 *
 * @param {number} props.balance - The current cash balance available for trading.
 * @param {Object} props.holdings - An object containing the user's current holdings of each cryptocurrency, 
 *                                  where keys are cryptocurrency symbols (e.g., "BTC") and values are the amounts owned.
 *
 * @returns {JSX.Element} A form for executing cryptocurrency trades (buy or sell).
 */
function TradeForm({ cryptos, onTrade, balance, holdings }) {

  // State variables to manage the form inputs and error messages
  const [tradeType, setTradeType] = useState('buy');
  const [symbol, setSymbol] = useState(cryptos[0]?.symbol || '');
  const [amount, setAmount] = useState('');
  const [error, setError] = useState('');
  
  /**
   * Handles form submission for the trade action.
   * Validates the input, checks for sufficient funds or holdings, and triggers the trade if valid.
   * 
   * @param {Object} e - The form submission event object
   */
  const handleSubmit = (e) => {
    e.preventDefault();
    
  // Validate the amount entered
    if (!amount || isNaN(amount) || parseFloat(amount) <= 0) {
      setError('Please enter a valid positive amount');
      return;
    }
    
  // For buy trades, check if the user has sufficient balance
    const amountValue = parseFloat(amount);
    const selectedCrypto = cryptos.find(c => c.symbol === symbol);
    
    if (tradeType === 'buy') {
      const cost = selectedCrypto.price * amountValue;
      if (cost > balance) {
        setError(`Insufficient funds. Cost: $${cost.toFixed(2)}, Balance: $${balance.toFixed(2)}`);
        return;
      }
    } else if (tradeType === 'sell') {
      const currentHolding = holdings[symbol] || 0;
      if (amountValue > currentHolding) {
        setError(`Insufficient holdings. You have ${currentHolding} ${symbol}`);
        return;
      }
    }
    
    setError('');
    onTrade(tradeType, symbol, amountValue);
    setAmount('');
  };
  
  return (
    <div className="trade-form">
      <h2>Trade Cryptocurrency</h2>
      {error && <div className="error-box">{error}</div>}
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Trade Type:</label>
          <select value={tradeType} onChange={(e) => setTradeType(e.target.value)}>
            <option value="buy">Buy</option>
            <option value="sell">Sell</option>
          </select>
        </div>
        <div className="form-group">
          <label>Cryptocurrency:</label>
          <select value={symbol} onChange={(e) => setSymbol(e.target.value)}>
            {cryptos.map(crypto => (
              <option key={crypto.symbol} value={crypto.symbol}>
                {crypto.name} ({crypto.symbol})
              </option>
            ))}
          </select>
        </div>
        <div className="form-group">
          <label>Amount:</label>
          <input
            type="number"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
            placeholder="Enter amount to trade"
            step="0.000001"
            min="0"
          />
        </div>
        <div style={{ display: 'flex', justifyContent: 'center', marginTop: '20px' }}>
          <button type="submit" className="trade-button">
            {tradeType === 'buy' ? 'Buy' : 'Sell'}
          </button>
        </div>
      </form>
    </div>
  );
}

export default TradeForm;