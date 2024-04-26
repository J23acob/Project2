import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StockDataLoader {

    public static List<StockData> loadStockData(String csvFile) {
        List<StockData> stockDataList = new ArrayList<>();
        String line = "";
        String csvDelimiter = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Read CSV header line
            br.readLine();

            // Read CSV data line by line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvDelimiter);
                // Extract data fields
                String date = data[0];
                double open = Double.parseDouble(data[1]);
                double high = Double.parseDouble(data[2]);
                double low = Double.parseDouble(data[3]);
                double close = Double.parseDouble(data[4]);
                double adjClose = Double.parseDouble(data[5]);
                long volume = Long.parseLong(data[6]);

                // Create StockData object and add to list
                StockData stockData = new StockData(date, open, high, low, close, adjClose, volume);
                stockDataList.add(stockData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stockDataList;
    }
}
//org