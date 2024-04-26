import java.util.List;

public class RSIandMovingAverageAlgorithm {
    private double currentBalance;
    private List<StockData> stockDataList;
    private int sharesOwned;
    private int rsiPeriod;
    private int smaPeriod;
    private double stopLossPercentage; // Adding stop-loss percentage
    private double transactionCostPercentage; // Adding transaction cost percentage

    public RSIandMovingAverageAlgorithm(double startingBalance, List<StockData> stockDataList, int rsiPeriod, int smaPeriod, double stopLossPercentage, double transactionCostPercentage) {
        this.currentBalance = startingBalance;
        this.stockDataList = stockDataList;
        this.sharesOwned = 0;
        this.rsiPeriod = rsiPeriod;
        this.smaPeriod = smaPeriod;
        this.stopLossPercentage = stopLossPercentage;
        this.transactionCostPercentage = transactionCostPercentage;
    }

    public int tradeEvaluator(StockData stockData, double rsi, double sma) {
        double closingPrice = stockData.getClose();

        if (rsi < 30 && closingPrice > sma) {
            double tradeAmount = currentBalance * 0.1;
            int sharesToBuy = (int) (tradeAmount / closingPrice);
            return sharesToBuy;
        } else if (rsi > 70 || closingPrice < sma) {
            return -sharesOwned; 
        } else {
            return 0; // Do nothing if conditions are not met
        }
    }

    public void runTradingStrategy() {
        for (int i = Math.max(rsiPeriod, smaPeriod); i < stockDataList.size(); i++) {
            double[] rsiValues = RSICalculator.calculateRSI(stockDataList.subList(0, i + 1), rsiPeriod);
            double[] smaValues = MovingAverageTrendLine.calculateSMA(stockDataList.subList(0, i + 1), smaPeriod);

            double rsi = rsiValues[i - rsiPeriod];
            double sma = smaValues[i - smaPeriod];

            StockData stockData = stockDataList.get(i);

            int tradeAmount = tradeEvaluator(stockData, rsi, sma);

            if (tradeAmount > 0) {
                int cost = (int) (tradeAmount * stockData.getClose() * (1 + transactionCostPercentage)); // Include transaction cost
                if (cost <= currentBalance) {
                    currentBalance -= cost;
                    sharesOwned += tradeAmount;
                }
            } else if (tradeAmount < 0) {
                int sharesToSell = -tradeAmount;
                if (sharesOwned >= sharesToSell) {
                    int earnings = (int) (sharesToSell * stockData.getClose() * (1 - transactionCostPercentage)); // Include transaction cost
                    currentBalance += earnings;
                    sharesOwned -= sharesToSell;
                }
            }

            double netWorth = currentBalance + (sharesOwned * stockData.getClose());

            // Implement stop-loss mechanism
            double stopLossThreshold = currentBalance * stopLossPercentage;
            if (netWorth <= stopLossThreshold) {
                System.out.println("Stop-loss triggered at date: " + stockData.getDate());
                break; // Exit trading if stop-loss threshold is reached
            }

            System.out.println("Date: " + stockData.getDate() + ", Net Worth: $" + netWorth);
        }
    }

    public static void main(String[] args) {
        double startingBalance = 10000.0;
        int rsiPeriod = 14;
        int smaPeriod = 50;
        double stopLossPercentage = 0.1; // Example stop-loss percentage
        double transactionCostPercentage = 0.005; // Example transaction cost percentage

        List<StockData> stockDataList = StockDataLoader.loadStockData("Data/BTC-USD.csv");

        RSIandMovingAverageAlgorithm bot = new RSIandMovingAverageAlgorithm(startingBalance, stockDataList, rsiPeriod, smaPeriod, stopLossPercentage, transactionCostPercentage);
        bot.runTradingStrategy();
    }
}
