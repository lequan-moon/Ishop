package com.nn.ishop.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.nn.ishop.client.util.Library;

public class ChartUtil {
	public static void main(String[] args) throws Exception{
		ChartUtil c = new ChartUtil();
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(c.drawLineChart(null));
		f.validate();
		f.pack();
		f.setVisible(true);
		
	}
	
	public ChartUtil() {
		super();
		BarRenderer.setDefaultShadowsVisible(false);
		ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
	}

	public ChartPanel drawLineChart(DefaultCategoryDataset dataset){		
		if(dataset == null){
			dataset = new DefaultCategoryDataset();
			dataset.addValue(20, "saleVolume", "N1");
			dataset.addValue(40, "saleVolume", "N2");
			dataset.addValue(30, "saleVolume", "N3");
			dataset.addValue(60, "saleVolume", "N4");
			dataset.addValue(20, "saleVolume", "N5");
			dataset.addValue(120, "saleVolume", "N6");
			
			dataset.addValue(150, "saleVolume", "N7");
			dataset.addValue(110, "saleVolume", "N8");
			dataset.addValue(12, "saleVolume", "N9");                        
			dataset.addValue(120, "saleVolume", "N10");		
		}
        
        /* Step -2:Define the JFreeChart object to create line chart */
        JFreeChart lineChartObject=ChartFactory.createLineChart("Sale Volume","Quarter","Amount($Milion)",
        		dataset,PlotOrientation.VERTICAL,true,true,false);       
        lineChartObject.setBackgroundPaint(new Color(255,255,255));
        CategoryPlot plot = lineChartObject.getCategoryPlot();                
        plot.getRenderer().setSeriesPaint(0, new Color(0x2F,0x7E,0xD8));     
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setDomainGridlinesVisible(true);   
        
        final ChartPanel chartPanel = new ChartPanel(lineChartObject);
        chartPanel.setBackground(new Color(255,255,255));
        return chartPanel;
	}
	public  ChartPanel drawBarChart(DefaultCategoryDataset dataset){
		if(dataset == null){
			dataset = new DefaultCategoryDataset();
			dataset.addValue(15, "interest", "Q1");
			dataset.addValue(30, "interest", "Q2");
			dataset.addValue(60, "interest", "Q3");                        
			dataset.addValue(80, "interest", "Q4");
	        
			dataset.addValue(10, "costing", "Q1");
			dataset.addValue(20, "costing", "Q2");
			dataset.addValue(25, "costing", "Q3");                        
			dataset.addValue(35, "costing", "Q4");
			
			dataset.addValue(10, "salary", "Q1");
			dataset.addValue(20, "salary", "Q2");
			dataset.addValue(25, "salary", "Q3");                        
			dataset.addValue(35, "salary", "Q4");
		}
		
		
        /* Step -2:Define the JFreeChart object to create line chart */		
        JFreeChart lineChartObject=ChartFactory.createBarChart(
        		"Cost vs Interest",
        		"Quarter",
        		"Amount($Milion)",
        		dataset,
        		PlotOrientation.VERTICAL,
        		true,true,false);
        
        lineChartObject.setBackgroundPaint(Color.WHITE);

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = lineChartObject.getCategoryPlot();
        plot.setNoDataMessage("NO DATA!");
        plot.setBackgroundPaint(Color.WHITE);        
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setDomainGridlinesVisible(true);        
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        
        // disable bar outlines...
        final CategoryItemRenderer catRenderer = plot.getRenderer();
        catRenderer.setSeriesItemLabelsVisible(0, Boolean.TRUE);
        
        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        
        final BarRenderer renderer1 = (BarRenderer) plot.getRenderer();
        renderer1.setDrawBarOutline(false);
        renderer1.setItemMargin(0.10);
        
        Color c1 = new Color(0x2F,0x7E,0xD8);
        Color c2 = new Color(0x0D,0x23,0x3A);
        Color c3 = new Color(0x8B,0xBC,0x21);
        
        renderer1.setSeriesPaint(0, c1);
        renderer1.setSeriesPaint(1, c2);
        renderer1.setSeriesPaint(2, c3);
       
        //--change the margin at the top of the range axis...
        final ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setLowerMargin(0.15);
        rangeAxis.setUpperMargin(0.15);
        
        final ChartPanel chartPanel = new ChartPanel(lineChartObject);
        return chartPanel;
	}
	public static ChartPanel drawPieChart(DefaultPieDataset dataset){
		if(dataset == null){
			dataset = new DefaultPieDataset();
	        dataset.setValue("Item1", new Double(43.2));
	        dataset.setValue("Item2", new Double(10.0));
	        dataset.setValue("Item3", new Double(27.5));
	        dataset.setValue("Item4", new Double(17.5));
		}
               
        JFreeChart chart = ChartFactory.createPieChart3D( "Top 10 best seller item",  
                dataset);
        PiePlot plot = (PiePlot) chart.getPlot();
        
        Color[] colors = {
        		Color.DARK_GRAY, 
        		Color.ORANGE, 
        		Color.GREEN,
        		Color.BLUE
        		}; 
        PieRenderer renderer = new PieRenderer(colors); 
        renderer.setColor(plot, dataset);
        
        plot.setLabelFont(new Font("Arial", Font.PLAIN, 11));
        plot.setNoDataMessage("No data available");
        plot.setCircular(true);
        plot.setLabelGap(0.02);
        plot.setLabelLinkStyle(PieLabelLinkStyle.STANDARD);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setBaseSectionOutlinePaint(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
        plot.setOutlinePaint(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
        plot.setLabelBackgroundPaint(Color.WHITE);
        
        final ChartPanel chartPanel = new ChartPanel(chart);
        return chartPanel;
	}
	
	static class PieRenderer 
    { 
        private Color[] color; 

        public PieRenderer(Color[] color) 
        { 
            this.color = color; 
        }        

        @SuppressWarnings("unchecked")
		public void setColor(PiePlot plot, DefaultPieDataset dataset) 
        { 
            List <Comparable<?>> keys = dataset.getKeys(); 
            int aInt; 

            for (int i = 0; i < keys.size(); i++) 
            { 
                aInt = i % this.color.length; 
                plot.setSectionPaint(keys.get(i), this.color[aInt]); 
            } 
        } 
    } 	 
	class Rotator extends Timer implements ActionListener {
		private static final long serialVersionUID = 1L;

		/** The plot. */
	    private PiePlot3D plot;

	    /** The angle. */
	    private int angle = 270;

	    /**
	     * Constructor.
	     *
	     * @param plot  the plot.
	     */
	    Rotator(final PiePlot3D plot) {
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
	        this.plot.setStartAngle(this.angle);
	        this.angle = this.angle + 1;
	        if (this.angle == 360) {
	            this.angle = 0;
	        }
	    }

	}
	class CustomRenderer extends BarRenderer {
		private static final long serialVersionUID = 1L;
		/** The colors. */
        private Paint[] colors;

        /**
         * Creates a new renderer.
         *
         * @param colors  the colors.
         */
        public CustomRenderer(final Paint[] colors) {
            this.colors = colors;
        }

        /**
         * Returns the paint for an item.  Overrides the default behaviour inherited from
         * AbstractSeriesRenderer.
         *
         * @param row  the series.
         * @param column  the category.
         *
         * @return The item color.
         */
        public Paint getItemPaint(final int row, final int column) {
            return this.colors[column % this.colors.length];
        }
    }
}
