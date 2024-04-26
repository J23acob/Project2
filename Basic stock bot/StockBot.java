import java.util.List;

public class StockBot {
    private double currentBalance;
    private List<StockData> stockDataList;
    private int sharesOwned;
    private int rsiPeriod;
    private int smaPeriod;

    public StockBot(double startingBalance, List<StockData> stockDataList, int rsiPeriod, int smaPeriod) {
        this.currentBalance = startingBalance;
        this.stockDataList = stockDataList;
        this.sharesOwned = 0;
        this.rsiPeriod = rsiPeriod;
        this.smaPeriod = smaPeriod;
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
                int cost = tradeAmount * (int) stockData.getClose();
                if (cost <= currentBalance) {
                    currentBalance -= cost;
                    sharesOwned += tradeAmount;
                }
            } else if (tradeAmount < 0) {
                int sharesToSell = -tradeAmount;
                if (sharesOwned >= sharesToSell) {
                    int earnings = sharesToSell * (int) stockData.getClose();
                    currentBalance += earnings;
                    sharesOwned -= sharesToSell;
                }
            }

            double netWorth = currentBalance + (sharesOwned * stockData.getClose());

            System.out.println("Date: " + stockData.getDate() + ", Net Worth: $" + netWorth);
        }
    }

    public static void main(String[] args) {
        double startingBalance = 10000.0;
        int rsiPeriod = 14;
        int smaPeriod = 50;

        List<StockData> stockDataList = StockDataLoader.loadStockData("Data/BTC-USD.csv");

        StockBot bot = new StockBot(startingBalance, stockDataList, rsiPeriod, smaPeriod);
        bot.runTradingStrategy();
    }
}
