import java.util.List;

public class CustomAlgorithm {
    private double currentBalance;
    private List<StockData> stockDataList;
    private int sharesOwned;
    private int smaPeriod;
    private double stopLossPercentage;
    private double transactionCostPercentage;

    public CustomAlgorithm(double startingBalance, List<StockData> stockDataList, int smaPeriod, double stopLossPercentage, double transactionCostPercentage) {
        this.currentBalance = startingBalance;
        this.stockDataList = stockDataList;
        this.smaPeriod = smaPeriod;
        this.stopLossPercentage = stopLossPercentage;
        this.transactionCostPercentage = transactionCostPercentage;
    }

    public void runCustomAlgorithm() {
        for (int i = 0; i < stockDataList.size(); i++) {
            if (i < smaPeriod) {
                continue; // Skip until enough data points for SMA calculation
            }

            double[] smaValues = MovingAverageTrendLine.calculateSMA(stockDataList.subList(0, i + 1), smaPeriod);
            double sma = smaValues[smaValues.length - 1]; // Get the latest SMA value

            StockData stockData = stockDataList.get(i);

            if (stockData.getClose() < sma) {
                // Buy logic
                double tradeAmount = currentBalance * 0.1; // Example: Invest 10% of current balance
                int sharesToBuy = (int) (tradeAmount / stockData.getClose());
                double totalCost = sharesToBuy * stockData.getClose() * (1 + transactionCostPercentage); // Include transaction cost
                if (totalCost <= currentBalance) {
                    currentBalance -= totalCost;
                    sharesOwned += sharesToBuy; // Update shares owned
                }
            } else {
                // Sell logic
                if (sharesOwned > 0) {
                    double earnings = sharesOwned * stockData.getClose() * (1 - transactionCostPercentage); // Include transaction cost
                    currentBalance += earnings;
                    sharesOwned = 0; // Reset shares owned
                }
            }

            // Implement stop-loss mechanism
            if (stopLossTriggered(stockData)) {
                System.out.println("Stop-loss triggered at date: " + stockData.getDate());
                break; // Exit trading if stop-loss threshold is reached
            }

            // Implement transaction cost
            currentBalance -= currentBalance * transactionCostPercentage;

            // Print current balance or other relevant information
            System.out.println("Date: " + stockData.getDate() + ", Current Balance: $" + currentBalance);
        }
    }

    private boolean stopLossTriggered(StockData stockData) {
        double netWorth = currentBalance + (sharesOwned * stockData.getClose());
        double stopLossThreshold = currentBalance * stopLossPercentage;
        return netWorth <= stopLossThreshold;
    }

    public static void main(String[] args) {
        double startingBalance = 10000.0;
        int smaPeriod = 30;
        double stopLossPercentage = 0.1; // Example stop-loss percentage
        double transactionCostPercentage = 0.005; // Example transaction cost percentage

        // Assuming you have a method to load weekly stock data
        List<StockData> stockDataList = StockDataLoader.loadStockData("Data/BTC-USD.csv");

        CustomAlgorithm customAlgorithm = new CustomAlgorithm(startingBalance, stockDataList, smaPeriod, stopLossPercentage, transactionCostPercentage);
        customAlgorithm.runCustomAlgorithm();
    }
}
