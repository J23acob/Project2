public class StockData {
    private String date;
    private double open;
    private double high;
    private double low;
    private double close;
    private double adjClose;
    private long volume;

    // Constructor
    public StockData(String date, double open, double high, double low, double close, double adjClose, long volume) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.adjClose = adjClose;
        this.volume = volume;
    }

    // Getters
    public String getDate() {
        return date;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public double getAdjClose() {
        return adjClose;
    }

    public long getVolume() {
        return volume;
    }

    
}

