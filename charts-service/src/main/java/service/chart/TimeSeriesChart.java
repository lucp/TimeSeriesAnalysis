/**
 * Copyright (c) 2013
 * Tomasz Choma, Olgierd Grodzki, Ĺ�ukasz PotÄ™pa, Monika Rakoczy, PaweĹ‚ Synowiec, Ĺ�ukasz Szarkowicz
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

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
import java.util.ArrayList;
import java.util.List;

/**
 * Panel zawierajacy komponenty biblioteki JFreeChart odpowiedzialne za rysowanie wykresu szeregu czasowego
 */
public class TimeSeriesChart extends JPanel {

    private static final Color centroidColor = Color.blue;
    private int firstColumnID;
    private int lastColumnID;

    private XYLineAndShapeRenderer renderer;

    /**
     * Wlasna klasa renderujaca wykres dziedziczaca po XYLineAndShapeRenderer 
     */
    private class MyRenderer extends XYLineAndShapeRenderer {
    	
        public MyRenderer(boolean lines, boolean shapes) {
            super(lines, shapes);
        }
        
        /**
         * Sprawdz, czy dany element z serii pochodzi z predykcji i ustaw odpowiedni kolor
         * 
         * @param row rzad elementu
         * @param col numer elementu
         */
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
    	createChartPanel(new ArrayList<TimeSeries>(), 0);
    }
    
    public TimeSeriesChart(List<TimeSeries> timeSeriesList){
    	createChartPanel(timeSeriesList, 0);
    }

    public TimeSeriesChart(List<TimeSeries> timeSeriesList, int numOfDataPoints){
    	createChartPanel(timeSeriesList, numOfDataPoints);
    }
    
    /**
     * Utworz nowa przestrzen z wykresem szeregu czasowego (z wyroznionymi punktami dla danych pochodzacych 
     * z predykcji) i dodaj ja do glownego panelu
     * 
     * @param timeSeriesList Lista szeregow czasowych
     * @param numOfDataPoints Liczba punktow czasowych dla ktorych zostala wykonana predykcja
     */
    public void createChartPanel(List<TimeSeries> timeSeriesList, int numOfDataPoints){
        XYDataset dataset = createDataset(timeSeriesList);

        if(numOfDataPoints > 0){
        	firstColumnID = dataset.getItemCount(0) - numOfDataPoints;
        	lastColumnID = dataset.getItemCount(0) - 1;
            renderer = new MyRenderer(true, false); 
        }else{
            renderer = new XYLineAndShapeRenderer(true, false);
        }

        JFreeChart chart = createChart(dataset);

        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);

        this.removeAll();
        this.add(panel);
    }
    
    /**
     * Utworz wykres szeregu czasowego na podstawie zestawu danych
     * 
     * @param dataset Zestaw danych
     * @return Wykres szeregu czasowego
     */
    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Time series", 		 // title
                "Date",             // x-axis label
                "Value",   			// y-axis label
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
            renderer.setBaseShapesVisible(false);
            renderer.setBaseShapesFilled(false);
        }

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));

        return chart;
    }
    
    /**
     * Utworz jeden zestaw danych na podstawie listy szeregow czasowych
     * 
     * @param timeSeriesList Lista szeregow czasowych
     * @return Zestaw danych
     */
    private XYDataset createDataset(List<TimeSeries> timeSeriesList) {

        TimeSeriesCollection dataset = new TimeSeriesCollection();

        for(TimeSeries timeSeries : timeSeriesList){
            dataset.addSeries(timeSeries);
        }

        return dataset;
    }
    
    /**
     * Wyczysc panel
     */
    public void clear(){
    	createChartPanel(new ArrayList<TimeSeries>(), 0);
    }
}
