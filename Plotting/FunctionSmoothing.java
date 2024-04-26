package plotting;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ui.ApplicationFrame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Random;

import javax.swing.JFrame;

public class FunctionSmoothing extends ApplicationFrame {

    public FunctionSmoothing(String title) {
        super(title);
        final XYSeries series = new XYSeries("y = sin(x) Smoothed after Salting");
        final double[] yValues = new double[200]; // Array to hold Y-values
        int index = 0;
        for (double x = 0; x < 20; x += 0.1) {
            yValues[index++] = Math.sin(x); // Store sine values
        }
        
        // Apply the salt function to the sine values
        double[] saltedYValues = SaltFunction.saltFunction(yValues, 0.5);
        
        // Apply the smooth function to the salted values
        double[] smoothedYValues = SmoothFunction.smoothFunction(saltedYValues, 5);
        
        // Add the smoothed values to the series
        index = 0;
        for (double x = 0; x < 20; x += 0.1) {
            series.add(x, smoothedYValues[index++]);
        }
        
        final XYSeriesCollection data = new XYSeriesCollection(series);
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Function Plot Smoothed after Salting",
            "X",
            "Y",
            data
        );
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 370));
        setContentPane(chartPanel);
    }

    public static void centerFrameOnScreen(final JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (screenSize.width - frame.getWidth()) / 2;
        int centerY = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(centerX, centerY);
    }

    public static void main(String[] args) {
        final FunctionSmoothing plot = new FunctionSmoothing("Function Plot Example");
        plot.pack();
        centerFrameOnScreen(plot);
        plot.setVisible(true);
    }

    public static class SaltFunction {
        public static double[] saltFunction(double[] y, double intensity) {
            Random rand = new Random();
            for (int i = 0; i < y.length; i++) {
                y[i] += intensity * (rand.nextDouble() - 0.5);
            }
            return y;
        }
    }

    public static class SmoothFunction {
        public static double[] smoothFunction(double[] y, int windowSize) {
            double[] smoothed = new double[y.length];
            for (int i = 0; i < y.length; i++) {
                double sum = 0;
                int count = 0;
                for (int j = Math.max(0, i - windowSize); j <= Math.min(y.length - 1, i + windowSize); j++) {
                    sum += y[j];
                    count++;
                }
                smoothed[i] = sum / count;
            }
            return smoothed;
        }
    }
}
