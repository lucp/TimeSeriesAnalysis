package service.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class TimeSeriesChart extends JPanel {

    private static final Color centroidColor = Color.blue;
    private int firstColumnID;
    private int lastColumnID;

    private XYLineAndShapeRenderer renderer;

    private class MyRenderer extends XYLineAndShapeRenderer {

        public MyRenderer(boolean lines, boolean shapes) {
            super(lines, shapes);
        }

        @Override
        public Paint getItemPaint(int row, int col) {
            if (col >= firstColumnID && col <= lastColumnID) {
                return centroidColor;
            } else {
                return super.getItemPaint(row, col);
            }
        }
    }
    
    public TimeSeriesChart(){
    }
    
    public TimeSeriesChart(List<TimeSeries> timeSeriesList){
    	createChartPanel(timeSeriesList, 0);
    }

    public TimeSeriesChart(List<TimeSeries> timeSeriesList, int numOfDataPoints){
    	createChartPanel(timeSeriesList, numOfDataPoints);
    }
    	
    public void createChartPanel(List<TimeSeries> timeSeriesList, int numOfDataPoints){
        XYDataset dataset = createDataset(timeSeriesList);

        if(numOfDataPoints > 0){
        	firstColumnID = dataset.getItemCount(0) - numOfDataPoints;
        	lastColumnID = dataset.getItemCount(0) - 1;
            System.out.print(firstColumnID + " " + lastColumnID);
            renderer = new MyRenderer(true, true);
        }else{
            renderer = new XYLineAndShapeRenderer(true, true);
        }

        JFreeChart chart = createChart(dataset);

        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);

        this.add(panel);
    }

    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Legal & General Unit Trust Prices",  // title
                "Date",             // x-axis label
                "Price Per Unit",   // y-axis label
                dataset,            // data
                true,               // create legend?
                true,               // generate tooltips?
                false               // generate URLs?
        );

        chart.setBackgroundPaint(Color.white);

        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        plot.setRenderer(renderer);

        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
        }

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));

        return chart;
    }

    private XYDataset createDataset(List<TimeSeries> timeSeriesList) {

        TimeSeriesCollection dataset = new TimeSeriesCollection();

        for(TimeSeries timeSeries : timeSeriesList){
            dataset.addSeries(timeSeries);
        }

        return dataset;
    }
}
