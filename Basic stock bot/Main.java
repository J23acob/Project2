import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Load stock data
        String csvFile = "Data/BTC-USD.csv";
        List<StockData> stockDataList = StockDataLoader.loadStockData(csvFile);

        if (stockDataList.isEmpty()) {
            System.out.println("Failed to load stock data.");
            return;
        }

        // Calculate RSI
        int rsiPeriod = 14;
        double[] rsiValues = RSICalculator.calculateRSI(stockDataList, rsiPeriod);

        // Calculate SMA
        int smaPeriod = 50;
        double[] smaValues = MovingAverageTrendLine.calculateSMA(stockDataList, smaPeriod);

        // Write RSI and SMA values to CSV file
        String outputCsvFile = "Data/RSI_SMA_values.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputCsvFile))) {
            // Write header
            writer.write("Date,RSI,SMA\n");

            // Write RSI and SMA values
            for (int i = rsiPeriod; i < stockDataList.size(); i++) {
                StockData stockData = stockDataList.get(i);
                String date = stockData.getDate();
                double rsi = rsiValues[i - rsiPeriod];
                double sma = smaValues[i - rsiPeriod];
                writer.write(date + "," + rsi + "," + sma + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("RSI and SMA values saved to " + outputCsvFile);
    }
}
