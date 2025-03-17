# Cryptocurrency Trading Simulator

This application simulates a cryptocurrency trading platform that allows users to view real-time prices of the top 20 cryptocurrencies and make virtual trades.

## Features

- Real-time cryptocurrency price updates using Kraken WebSocket API
- Virtual account balance for buying and selling cryptocurrencies
- Transaction history with profit/loss tracking
- Ability to reset account balance

## Technical Stack

- **Frontend**: React
- **Backend**: Java with Spring Boot
- **API Integration**: Kraken WebSocket API

## Project Structure

### Backend

The Spring Boot application provides the following endpoints:

- `GET /api/cryptos`: Retrieve the top 20 cryptocurrencies with current prices
- `POST /api/trade`: Execute a buy or sell order
- `POST /api/reset`: Reset the account to its initial state
- `GET /api/account`: Get account information

The backend uses a WebSocket connection to Kraken to receive real-time price updates, with a fallback to simulated data if the connection fails.

### Frontend

The React application includes:

- A table displaying the top 20 cryptocurrencies with their prices and 24-hour changes
- An account overview showing current balance and holdings
- A trade form to buy and sell cryptocurrencies
- A transaction history table showing all trades with profit/loss information

## Setup Instructions

### Prerequisites

- Java 11 or higher
- Maven
- Node.js and npm

### Running the Backend

1. Navigate to the backend directory
2. Build the application:
   
   mvn clean install
   
3. Run the application:
   
   mvn spring-boot:run
   
   The backend will start on http://localhost:8080

Alternatively, you can use InteliJ
1. Import the project to InteliJ

2.Build the project

3. Run the project

### Running the Frontend

1. Navigate to the frontend directory
2. Install dependencies:
   
   npm install
   
3. Start the development server:

   npm start
   
   The frontend will start on http://localhost:3000

Alternatively, you can use VS Code
1. Import the project

2. Install dependencies:
   
   npm install
   
3. Start the development server:

   npm start

## How It Works

1. The application connects to the Kraken WebSocket API to receive real-time cryptocurrency price updates.
2. If the WebSocket connection fails, the application falls back to simulated price data.
3. Users start with a virtual balance of $10,000.
4. Users can buy cryptocurrencies, which deducts from their cash balance and adds to their holdings.
5. Users can sell cryptocurrencies, which adds to their cash balance and removes from their holdings.
6. The application tracks the average buy price for each cryptocurrency to calculate profit/loss on sales.
7. Users can reset their account to start over with the initial balance.
