package plotting;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ui.ApplicationFrame;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;

public class FunctionPlotting extends ApplicationFrame {

    public FunctionPlotting(String title) {
        super(title);
        final XYSeries series = new XYSeries("y = sin(x)");
        for (double x = 0; x < 20; x += 0.1) {
            series.add(x, Math.sin(x));
        }
        final XYSeriesCollection data = new XYSeriesCollection(series);
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Function Plot",
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
        FunctionPlotting plot = new FunctionPlotting("Function Plot Example");
        plot.pack();
        centerFrameOnScreen(plot);
        plot.setVisible(true);
    }

}
