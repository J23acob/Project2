import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class RSIGrapher {
    public static void exportRSIToCSV(List<StockData> data, int period, String outputCSV) {
        double[] rsiValues = RSICalculator.calculateRSI(data, period);
        try (FileWriter writer = new FileWriter(outputCSV)) {
            writer.write("Date,RSI\n");
            for (int i = period * 2; i < data.size(); i++) { // Skip first 28 days
                writer.write(data.get(i).getDate() + "," + rsiValues[i - period * 2] + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
