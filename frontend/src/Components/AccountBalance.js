import React, { useState } from 'react';

/**
 * AccountBalance component that displays a user's account overview including:
 * - Cash balance
 * - Holdings value (calculated from cryptocurrency holdings)
 * - Total account value (cash balance + holdings value)
 * - Username field that can be edited
 * - Option to reset the account (clear username and reset state)
 *
 * @param {Object} props - Component props
 * @param {number} props.balance - The cash balance in the user's account
 * @param {Object} props.holdings - An object containing user's cryptocurrency holdings, 
 *                                    where keys are cryptocurrency symbols (e.g. "BTC") and values are the amounts owned
 * @param {Array} props.cryptos - An array of cryptocurrency objects with 'symbol' and 'price' properties
 * @param {Function} props.onReset - A callback function to handle resetting the account
 */
function AccountBalance({ balance, holdings, cryptos, onReset }) {
  // State for the user field
  const [username, setUsername] = useState('');
  const [isEditing, setIsEditing] = useState(true);
  
/**
   * Calculates the total value of the user's holdings.
   * Iterates through the holdings and multiplies the amount by the corresponding cryptocurrency's price.
   * 
   * @returns {number} The total value of the user's holdings
   */
  const calculateHoldingsValue = () => {
    let total = 0;
    Object.entries(holdings).forEach(([symbol, amount]) => {
      const crypto = cryptos.find(c => c.symbol === symbol);
      if (crypto) {
        total += crypto.price * amount;
      }
    });
    return total;
  };
  
  // Calculate the total holdings value
  const holdingsValue = calculateHoldingsValue();

  // Calculate the total value (cash balance + holdings value)
  const totalValue = balance + holdingsValue;
  
  /**
   * Handles the change in the username input field.
   * Updates the `username` state with the new input value.
   * 
   * @param {Object} e - Event object from the input change
   */
  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };
  
 /**
   * Toggles between the editing mode and display mode for the username field.
   * If the username field is valid (non-empty), it switches to display mode; otherwise, it stays in editing mode.
   */
  const toggleEditMode = () => {
    if (isEditing && username.trim()) {
      setIsEditing(false);
    } else {
      setIsEditing(true);
    }
  };
  
 /**
   * Resets the username and toggles edit mode back to true.
   * Calls the onReset function to handle any additional reset actions passed from the parent.
   */
  const handleReset = () => {
    setUsername('');
    setIsEditing(true);
    onReset();
  };
  
  return (
    <div className="account-balance">
      <h2>Account Overview</h2>
      {/* Username Field that toggles between input and display */}
      <div className="username-field">

        {isEditing ? (
          <div>
            <input
              type="text"
              id="username"
              value={username}
              onChange={handleUsernameChange}
              placeholder="Enter your username"
            />
            {username.trim() && (
              <button onClick={toggleEditMode}>Save</button>
            )}
          </div>
        ) : (
          <span 
            style={{ fontWeight: 'bold', cursor: 'pointer' }} 
            onClick={toggleEditMode}
          >
            {username}
          </span>
        )}
      </div>
      <div className="balance-info">
        <div className="balance-item">
          <span>Cash Balance:</span>
          <span>${balance.toFixed(2)}</span>
        </div>
        <div className="balance-item">
          <span>Holdings Value:</span>
          <span>${holdingsValue.toFixed(2)}</span>
        </div>
        <div className="balance-total">
          <span>Total Value:</span>
          <span>${totalValue.toFixed(2)}</span>
        </div>
      </div>
      <div className="holdings">
        <h3>Your Holdings</h3>
        {Object.keys(holdings).length > 0 ? (
          <ul>
            {Object.entries(holdings).map(([symbol, amount]) => {
              const crypto = cryptos.find(c => c.symbol === symbol);
              const value = crypto ? crypto.price * amount : 0;
              return (
                <li key={symbol}>
                  {symbol}: {amount.toFixed(6)} (${value.toFixed(2)})
                </li>
              );
            })}
          </ul>
        ) : (
          <p>No holdings yet</p>
        )}
      </div>
      <button className="reset-button" onClick={handleReset}>Reset Account</button>
    </div>
  );
}

export default AccountBalance;