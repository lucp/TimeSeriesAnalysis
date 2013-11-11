package service.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;

import javax.swing.*;

import java.awt.*;

public class FitnessChart extends JPanel {
  
    private DefaultCategoryDataset dataset;
    private ChartPanel chartPanel;
    
    public FitnessChart() {
        
        dataset = new DefaultCategoryDataset ();
        
        JFreeChart chart = createChart(dataset);
        
        chartPanel = new ChartPanel(chart);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setMouseWheelEnabled(true);
        
        this.add(chartPanel);
    }

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

    public void addValue(double val, String series, int ix){
    	dataset.addValue(val, series, Integer.toString(ix));
    }
    
    public void ripejnt(){
    	chartPanel.setRefreshBuffer(true);
    	chartPanel.repaint();
    }
}
