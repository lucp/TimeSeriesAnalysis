package service.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;

import javax.swing.*;
import java.awt.*;

/**
 * Fitness chart class
 * 
 * @author Tomek
 *
 */
public class FitnessChart extends JFrame {

    private static final long serialVersionUID = 1L;

    {
        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow", true));
    }
    
    private DefaultCategoryDataset dataset;
    private MyChartPanel chartPanel;
    
    /**
     * @param title  Chart window title
     */
    public FitnessChart(String title) {
        super(title);
        dataset = new DefaultCategoryDataset ();
        chartPanel = (MyChartPanel) createPanel();
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        add(chartPanel);
    }

    /**
     * Chart creation
     *
     * @param categoryDataset  a dataset.
     *
     * @return A chart
     */
    private static JFreeChart createChart(CategoryDataset categoryDataset) {

        JFreeChart chart = ChartFactory.createLineChart(
            "Wykres",  			// title
            "Iteracja",         // x-axis label
            "Fitness",   		// y-axis label
            categoryDataset,    // data
            PlotOrientation.VERTICAL,
            true,               // create legend?
            true,               // generate tooltips?
            false               // generate URLs?
        );

        chart.setBackgroundPaint(Color.white);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        CategoryItemRenderer r = plot.getRenderer();
        if (r instanceof LineAndShapeRenderer) {
            LineAndShapeRenderer renderer = (LineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(false);
            renderer.setBaseShapesFilled(true);
        }

        return chart;
    }

    /**
     * Dataset creation
     *
     * @param val Value to be inserted into dataset
     * @param series  Series label
     * @param ix  X axis label, in this case iteration number
     */
    public void addValue(double val, String series, int ix){
    	dataset.addValue(val, series, Integer.toString(ix));
    }
    
    /**
     * Panel creation
     *
     * @return A panel.
     */
    public JPanel createPanel() {
        JFreeChart chart = createChart(dataset);
        MyChartPanel panel = new MyChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
        return panel;
    }
    
    public void ripejnt(){
    	chartPanel.setRefreshBuffer(true);
    	chartPanel.repaint();
    }
}
