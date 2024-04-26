import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class MovingAverageTrendLine {

    // Method to calculate Simple Moving Average (SMA)
    public static double[] calculateSMA(List<StockData> stockDataList, int period) {
        int dataSize = stockDataList.size();
        double[] smaValues = new double[dataSize];
    
        // Calculate SMA for each data point
        for (int i = 0; i < dataSize; i++) {
            // Calculate sum of closing prices for the period
            double sum = 0;
            for (int j = Math.max(0, i - period + 1); j <= i; j++) {
                sum += stockDataList.get(j).getClose();
            }
    
            // Calculate SMA and store in array
            smaValues[i] = sum / Math.min(period, i + 1);
        }
    
        return smaValues;
    }
    
    // Method to overlay MA line with loaded Stock Data
    public static void overlayMATrendLine(List<StockData> stockDataList, double[] movingAverage, String outputFile) {
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            // Write header
            writer.write("Date,Close,SMA\n");

            // Write stock data and SMA values
            for (int i = 0; i < stockDataList.size(); i++) {
                StockData stockData = stockDataList.get(i);
                if (i >= movingAverage.length) {
                    writer.write(stockData.getDate() + "," + stockData.getClose() + ",\n");
                } else {
                    writer.write(stockData.getDate() + "," + stockData.getClose() + "," + movingAverage[i] + "\n");
                }
            }

            System.out.println("Data with SMA values saved to " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
//ORG