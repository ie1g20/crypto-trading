import React from 'react';

/**
 * CryptoTable component that displays a table of top 20 cryptocurrencies.
 * The table includes the cryptocurrency's name, symbol, price in USD, and 24-hour price change.
 * It also highlights the price change (positive or negative) in different styles.
 *
 * @param {Object} props - Component props
 * @param {Array} props.cryptos - An array of cryptocurrency objects with the following properties:
 *                                - `name`: The name of the cryptocurrency (string)
 *                                - `symbol`: The symbol of the cryptocurrency (string, e.g. "BTC")
 *                                - `price`: The current price of the cryptocurrency in USD (number)
 *                                - `change24h`: The percentage change in price over the last 24 hours (number)
 *
 * @returns {JSX.Element} A table displaying the top cryptocurrencies with their relevant information
 */
function CryptoTable({ cryptos }) {
  return (
    <div className="crypto-table">
      <h2>Top 20 Cryptocurrencies</h2>
      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Symbol</th>
            <th>Price (USD)</th>
            <th>24h Change</th>
          </tr>
        </thead>
        <tbody>
          {cryptos.map(crypto => (
            <tr key={crypto.symbol}>
              <td>{crypto.name}</td>
              <td>{crypto.symbol}</td>
              <td>${crypto.price.toFixed(2)}</td>
              <td className={crypto.change24h >= 0 ? 'positive' : 'negative'}>
                {crypto.change24h > 0 ? '+' : ''}{crypto.change24h.toFixed(2)}%
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default CryptoTable;