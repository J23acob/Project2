import java.util.List;

public class RSICalculator {

    public static double[] calculateRSI(List<StockData> data, int period) {
        double[] rsiValues = new double[data.size() - period];

        for (int i = period; i < data.size(); i++) {
            double avgUp = 0;
            double avgDown = 0;

            // Calculate average up and average down moves
            for (int j = i - period + 1; j <= i; j++) {
                double priceDiff = data.get(j).getClose() - data.get(j - 1).getClose();
                if (priceDiff > 0) {
                    avgUp += priceDiff;
                } else {
                    avgDown += Math.abs(priceDiff);
                }
            }
            avgUp /= period;
            avgDown /= period;

            // Calculate Relative Strength (RS)
            double rs = (avgDown == 0) ? Double.POSITIVE_INFINITY : avgUp / avgDown;

            // Calculate RSI
            double rsi = 100 - 100 / (1 + rs);
            rsiValues[i - period] = rsi;
        }

        return rsiValues;
    }
}
