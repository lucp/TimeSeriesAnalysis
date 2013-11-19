/**
 * Copyright (c) 2013
 * Tomasz Choma, Olgierd Grodzki, Łukasz Potępa, Monika Rakoczy, Paweł Synowiec, Łukasz Szarkowicz
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
 * Panel zawierajacy komponenty biblioteki JFreeChart odpowiedzialne za rysowanie wykresu funkcji fitness
 * w czasie rzeczywistym (w trakcie działania algorytmu genetycznego)
 */
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
    
    /**
     * Utworz wykres funkcji fitness dla najlepszego osobnika w danej iteracji algorytmu genetycznego 
     * na podstawie zestawu danych
     * 
     * @param categoryDataset Zestaw danych
     * @return Wykres funkcji fitness
     */
    private static JFreeChart createChart(CategoryDataset categoryDataset) {

        JFreeChart chart = ChartFactory.createLineChart(
            "Best fitness function value",  			// title
            "Iteration",         // x-axis label
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
     * Dodaj nowa wartosc funkcji fitness do zestawu danych
     * 
     * @param val Wartosc funkcji fitness najlepszego osobnika w danej iteracji algorytmu
     * @param series Nazwa serii danych
     * @param ix Numer iteracji
     */
    public void addValue(double val, String series, int ix){
    	dataset.addValue(val, series, Integer.toString(ix));
    }
    
    /**
     * Odśwież i przerysuj panel
     */
    public void ripejnt(){
    	chartPanel.setRefreshBuffer(true);
    	chartPanel.repaint();
    }
    
    /**
     * Wyczysc panel
     */
    public void clear(){
    	dataset.clear();
    	this.ripejnt();
    }
}
