import React, { useState, useEffect } from 'react';
import axios from 'axios';
import CryptoTable from './Components/CryptoTable';
import AccountBalance from './Components/AccountBalance';
import TradeForm from './Components/TradeForm';
import TransactionHistory from './Components/TransactionHistory';
import './App.css';

function App() {
  // State to store the list of cryptocurrencies
  const [cryptos, setCryptos] = useState([]);

  // State to store account information (balance, holdings, transactions)
  const [account, setAccount] = useState({
    balance: 10000, // Starting balance for the account
    holdings: {}, // Holds user's cryptocurrency amounts (e.g., BTC: 0.5, ETH: 1.2)
    transactions: [] // Stores transaction history
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Fetch initial crypto data
    fetchCryptos();

    // Set up polling for price updates
    const interval = setInterval(() => {
      fetchCryptos();
    }, 10000);

    // Clean up the interval
    return () => clearInterval(interval);
  }, []);

  // Fetch cryptocurrency data from the backend
  const fetchCryptos = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/cryptos');
      setCryptos(response.data); // Update the state with the new crypto data
      setLoading(false); // Set loading to false once data is fetched
    } catch (err) {
      setError('Failed to fetch cryptocurrency data'); // Set error if the request fails
      setLoading(false); // Set loading to false even if there is an error
    }
  };

  // Function to handle executing a trade (buy/sell)
  const executeTrade = async (type, symbol, amount) => {
    try {
       // Send trade request to the backend
      const response = await axios.post('http://localhost:8080/api/trade', {
        type, // Type of trade (buy or sell)
        symbol, // Cryptocurrency symbol (e.g., BTC, ETH)
        amount, // Amount of cryptocurrency to buy or sell
        accountBalance: account.balance, // User's current balance
        holdings: account.holdings // User's current holdings
      });

      if (response.data.success) {
        setAccount(response.data.account); // Update account state with the new data
      } else {
        setError(response.data.message); // Show error message if trade fails
      }
    } catch (err) {
      setError('Error executing trade'); // Display error if there is a problem with the request
    }
  };

  // Function to reset the account (called when the user resets their account)
  const resetAccount = async () => {
    try {
      // Send reset request to the backend
      const response = await axios.post('http://localhost:8080/api/reset');
      // Reset account data
      setAccount(response.data);
    } catch (err) {
      setError('Error resetting account'); // Display error if reset fails
    }
  };

  // Display loading message if data is still being fetched
  if (loading) return <div className="loading">Loading cryptocurrency data...</div>;

  // Display error message if there was an issue fetching data
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="app-container">
      <header>
        <h1>Crypto Trading Simulator</h1>
      </header>
      <main>
        <div className="dashboard">
          <AccountBalance 
            balance={account.balance} 
            holdings={account.holdings} 
            cryptos={cryptos} 
            onReset={resetAccount} 
          />
          <TradeForm 
            cryptos={cryptos} 
            onTrade={executeTrade} 
            balance={account.balance}
            holdings={account.holdings}
          />
        </div>
        <div className="data-container">
          <CryptoTable cryptos={cryptos} />
          <TransactionHistory transactions={account.transactions} />
        </div>
      </main>
    </div>
  );
}
export default App;