import React from 'react';

/**
 * TransactionHistory component that displays a user's cryptocurrency transaction history.
 * The transactions are sorted by timestamp, with the most recent ones appearing at the top.
 * It displays details such as the transaction date, type (buy/sell), symbol, amount, price, total value, and profit/loss.
 *
 * @param {Object} props - Component props
 * @param {Array} props.transactions - An array of transaction objects with the following properties:
 *                                     - `timestamp`: The timestamp of the transaction (string or number, e.g., ISO 8601 date)
 *                                     - `type`: The type of the transaction, either 'buy' or 'sell' (string)
 *                                     - `symbol`: The symbol of the cryptocurrency involved (e.g., 'BTC') (string)
 *                                     - `amount`: The amount of cryptocurrency traded (number)
 *                                     - `price`: The price of the cryptocurrency at the time of the transaction (number)
 *                                     - `total`: The total value of the transaction (number)
 *                                     - `profitLoss`: The profit or loss from the transaction (number), or `null` if not applicable
 *
 * @returns {JSX.Element} A table displaying the transaction history with relevant details or a message indicating no transactions.
 */
function TransactionHistory({ transactions }) {
  // Sort the transactions array by timestamp in descending order
  const sortedTransactions = [...transactions].sort((a, b) => 
    new Date(b.timestamp) - new Date(a.timestamp)
  );

  return (
    <div className="transaction-history">
      <h2>Transaction History</h2>
      {transactions.length > 0 ? (
        <table>
          <thead>
            <tr>
              <th>Date</th>
              <th>Type</th>
              <th>Symbol</th>
              <th>Amount</th>
              <th>Price</th>
              <th>Total</th>
              <th>P/L</th>
            </tr>
          </thead>
          <tbody>
            {sortedTransactions.map((transaction, index) => (
              <tr key={index}>
                <td>{new Date(transaction.timestamp).toLocaleString()}</td>
                <td className={transaction.type === 'buy' ? 'buy' : 'sell'}>
                  {transaction.type.toUpperCase()}
                </td>
                <td>{transaction.symbol}</td>
                <td>{transaction.amount.toFixed(6)}</td>
                <td>${transaction.price.toFixed(2)}</td>
                <td>${transaction.total.toFixed(2)}</td>
                <td className={transaction.profitLoss > 0 ? 'positive' : transaction.profitLoss < 0 ? 'negative' : ''}>
                  {transaction.profitLoss ? `$${transaction.profitLoss.toFixed(2)}` : '-'}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>No transactions yet</p>
      )}
    </div>
  );
}

export default TransactionHistory;