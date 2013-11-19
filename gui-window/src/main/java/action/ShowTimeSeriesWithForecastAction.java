
package action;

import forecasting.AbstractForecast;
import forecasting.config.ForecastConfig;
import forecasting.config.ForecastMethod;
import forecasting.config.GASettings;
import forecasting.config.SelectionMethod;
import forecasting.model.SlidingTimeWindow;
import gui.TSAFrame;

import org.bouncycastle.crypto.DataLengthException;
import org.jfree.data.io.CSV;
import org.jfree.data.time.TimeSeries;
import org.jfree.ui.RefineryUtilities;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import data.SwingTableDataAcquisitor;
import service.action.GAChartObserver;
import service.chart.FitnessChart;
import service.chart.TimeSeriesChart;
import statistics.Statistics;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Arrays;

public class ShowTimeSeriesWithForecastAction implements ActionListener {

    TSAFrame window;

    public ShowTimeSeriesWithForecastAction(TSAFrame window){
        this.window = window;
    }
    
    public void actionPerformed(ActionEvent e){
    	
    	try{
    		
    		if (window.getSliderSelekcji().getValue() + window.getSliderKrzyzowania().getValue() + window.getSliderMutacji().getValue() != 100)
    			throw new ParseException("Please insert correct data for Selection, Crossing, oraz Mutation. The sum of the three has to equal 100%", 0);
    		
	    	TimeSeries timeSeries = window.getCurrentTimeSeries();
	    	if (timeSeries==null || timeSeries.isEmpty()) throw new DataLengthException();
	        
	    	SlidingTimeWindow slidingTimeWindow = new SlidingTimeWindow(this.parseToWindowForm(window.getTimeWindowField().getText()));
	
	        if(window.getRdBtnStochastic().isSelected())
	        	GASettings.getInstance().setSelectionMethod(SelectionMethod.STOCHASTIC_UNIVERSAL_SAMPLING_SELECTION);
	        
	        if(window.getRdbtnArmaForecast().isSelected())
	        	GASettings.getInstance().setForecastMethod(ForecastMethod.ARMA_FORECAST);
	
	        GASettings.getInstance().setConcurrent(true);
	
	        ApplicationContext context = new AnnotationConfigApplicationContext(ForecastConfig.class);
	        AbstractForecast forecast = (AbstractForecast) context.getBean("forecast");
	
	        forecast.initializeGeneticAlgorithm(
	    		(TimeSeries) timeSeries.clone(),				
	            (Integer) window.getPopulSizeField().getValue(),
	            slidingTimeWindow,				               
	            (Integer) window.getIterNumberField().getValue(),
	            (double)window.getSliderProbOfCross().getValue()/100,
	            (double)window.getSliderProbOfMutat().getValue()/100,
	            (double)window.getSliderSelekcji().getValue()/100,
	            (double)window.getSliderKrzyzowania().getValue()/100,
	            (double)window.getSliderMutacji().getValue()/100);
	        forecast.initializeForecast((Integer) window.getPeriodOfPredField().getValue());
	                
	        forecast.addObserver(new GAChartObserver(window.getFitnessChart(), window.getTimeSeriesChartWithForecast(), (Integer) window.getPeriodOfPredField().getValue()));
	        forecast.addObserver(new GAStatisticObserver(window.getForecast(), (Integer) window.getPeriodOfPredField().getValue()));
	        forecast.execute();
	        window.getTabbedPane().setSelectedIndex(3);
	        
        } 
    	catch (CloneNotSupportedException e1) {
            e1.printStackTrace();
        }
    	catch (DataLengthException de){
    		JOptionPane.showMessageDialog(window, "Current data is not set or empty", "Error", JOptionPane.ERROR_MESSAGE);
    	}
    	catch (ParseException pe){
    		JOptionPane.showMessageDialog(window, pe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	}
    	catch(Exception exc){
    		JOptionPane.showMessageDialog(window, "Unknown error", "Error", JOptionPane.ERROR_MESSAGE);
    	}
    }
    
    public int[] parseToWindowForm(String str) throws NumberFormatException{
    	str=str.replaceAll(" ", "");
    	str=str.replaceAll("\t", "");
    	str=str.replaceAll("\n", "");
    	String[] values=str.split(",");
    	int[] parsed=new int[values.length];
    	for (int i=0;i<parsed.length;i++){
    		parsed[i]=Integer.valueOf(values[i]);
    		if (parsed[i]<=0) throw new NumberFormatException();
    	}
    	return parsed;
    }
    
}
