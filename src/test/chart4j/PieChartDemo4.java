package test.chart4j;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.Log;
import org.jfree.util.PrintStreamLogTarget;
public class PieChartDemo4 extends ApplicationFrame {

    /**
     * Default constructor.
     *
     * @param title  the frame title.
     */
    public PieChartDemo4(final String title) {

        super(title);
        final PieDataset dataset = createDataset(14);

        // create the chart...
        final JFreeChart chart = ChartFactory.createPieChart(
            "Pie Chart Demo 4",  // chart title
            dataset,             // dataset
            false,               // include legend
            true,
            false
        );

        // set the background color for the chart...
        chart.setBackgroundPaint(new Color(222, 222, 255));
        final PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setCircular(true);
        plot.setNoDataMessage("No data available");

        // add the chart to a panel...
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
        
        final Rotator rotator = new Rotator(plot);
        rotator.start();

    }

    /**
     * Creates a sample dataset.
     * 
     * @param sections  the number of sections.
     * 
     * @return A sample dataset.
     */
    private PieDataset createDataset(final int sections) {
        final DefaultPieDataset result = new DefaultPieDataset();
        for (int i = 0; i < sections; i++) {
            final double value = 100.0 * Math.random();
            result.setValue("Section " + i, value);
        }
        return result;
    }
    
    // ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE                                               *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
    // * to purchase from Object Refinery Limited:                                *
    // *                                                                          *
    // * http://www.object-refinery.com/jfreechart/guide.html                     *
    // *                                                                          *
    // * Sales are used to provide funding for the JFreeChart project - please    * 
    // * support us so that we can continue developing free software.             *
    // ****************************************************************************
    
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(final String[] args) {

        Log.getInstance().addTarget(new PrintStreamLogTarget());
        final PieChartDemo4 demo = new PieChartDemo4("Pie Chart Demo 4");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

    /**
     * The rotator.
     *
     */
    static class Rotator extends Timer implements ActionListener {

        /** The plot. */
        private PiePlot plot;

        /** The angle. */
        private int angle = 270;

        /**
         * Constructor.
         *
         * @param plot  the plot.
         */
        Rotator(final PiePlot plot) {
            super(100, null);
            this.plot = plot;
            addActionListener(this);
        }

        /**
         * Modifies the starting angle.
         *
         * @param event  the action event.
         */
        public void actionPerformed(final ActionEvent event) {
            this.plot.setStartAngle(angle);
            this.angle = this.angle + 1;
            if (this.angle == 360) {
                this.angle = 0;
            }
        }

    }

}

