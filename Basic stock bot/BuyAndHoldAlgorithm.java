import java.util.List;

public class BuyAndHoldAlgorithm {
    private double currentBalance;
    private final List<StockData> stockDataList;
    private int sharesOwned;
    private double initialPrice; // Track initial purchase price for buy and hold strategy

    public BuyAndHoldAlgorithm(double startingBalance, List<StockData> stockDataList) {
        this.currentBalance = startingBalance;
        this.stockDataList = stockDataList;
        this.sharesOwned = 0;
        this.initialPrice = 0; // Initialize initial price to zero
    }

    public void runTradingStrategy() {
        // Buy and hold strategy: Buy at the beginning and hold for the entire duration
        StockData firstStockData = stockDataList.get(0);
        initialPrice = firstStockData.getClose();
        sharesOwned = (int) (currentBalance / initialPrice);
        currentBalance -= sharesOwned * initialPrice;

        // Calculate net worth at the end of the trading period
        StockData lastStockData = stockDataList.get(stockDataList.size() - 1);
        double finalNetWorth = currentBalance + (sharesOwned * lastStockData.getClose());

        System.out.println("Initial Investment: $" + (initialPrice * sharesOwned));
        System.out.println("Final Net Worth: $" + finalNetWorth);
    }

    public static void main(String[] args) {
        double startingBalance = 10000.0;

        List<StockData> stockDataList = StockDataLoader.loadStockData("Data/BTC-USD.csv");

        BuyAndHoldAlgorithm bot = new BuyAndHoldAlgorithm(startingBalance, stockDataList);
        bot.runTradingStrategy();
    }
}
