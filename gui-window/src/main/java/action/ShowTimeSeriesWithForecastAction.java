
package action;

import forecasting.AbstractForecast;
import forecasting.config.ForecastConfig;
import forecasting.config.ForecastMethod;
import forecasting.config.GASettings;
import forecasting.config.SelectionMethod;
import forecasting.model.SlidingTimeWindow;
import gui.main;

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

    main window;
    Statistics stat;

    public ShowTimeSeriesWithForecastAction(main window){
        this.window = window;
        this.stat = window.getStat();
    }
    
    public void actionPerformed(ActionEvent e){
    	
    	try{
    		
    		if (window.getSliderSelekcji().getValue() + window.getSliderKrzyzowania().getValue() + window.getSliderMutacji().getValue() != 100)
    			throw new ParseException("Please insert correct data for Selection, Crossing, oraz Mutation. The sum of the three has to equal 100%", 0);
    		
	    	TimeSeries timeSeries = window.getCurrentTimeSeries();
	    	if (timeSeries==null || timeSeries.isEmpty()) throw new DataLengthException();
	        
	    	TimeSeries[] timeSeriesArray = SwingTableDataAcquisitor.splitTimeSeriesOnHalf(timeSeries);
	    	
	    	//TO DO
	    	//jak zostanie ogarniete wpisywanie do pola 'Time window' nale�y to zmodyfikowa�
	    	SlidingTimeWindow slidingTimeWindow = new SlidingTimeWindow(new int[]{2});
	
	        if(window.getRdBtnStochastic().isSelected())
	        	GASettings.getInstance().setSelectionMethod(SelectionMethod.STOCHASTIC_UNIVERSAL_SAMPLING_SELECTION);
	        
	        if(window.getRdbtnArmaForecast().isSelected())
	        	GASettings.getInstance().setForecastMethod(ForecastMethod.ARMA_FORECAST);
	
	        GASettings.getInstance().setConcurrent(true);
	
	        ApplicationContext context = new AnnotationConfigApplicationContext(ForecastConfig.class);
	        AbstractForecast forecast = (AbstractForecast) context.getBean("forecast");
	
	        forecast.initializeGeneticAlgorithm(
	    		(TimeSeries) timeSeriesArray[0].clone(),				
	            (Integer) window.getPopulSizeField().getValue(),
	            slidingTimeWindow,				                //TO DO
	            (Integer) window.getIterNumberField().getValue(),
	            (double)window.getSliderProbOfCross().getValue()/100,
	            (double)window.getSliderProbOfMutat().getValue()/100,
	            (double)window.getSliderSelekcji().getValue()/100,
	            (double)window.getSliderKrzyzowania().getValue()/100,
	            (double)window.getSliderMutacji().getValue()/100);
	        forecast.initializeForecast((Integer) window.getPeriodOfPredField().getValue());
	                
	        forecast.addObserver(new GAChartObserver(window.getFitnessChart(), window.getTimeSeriesChart(), (Integer) window.getPeriodOfPredField().getValue()));
	        forecast.addObserver(new GAStatisticObserver(stat, timeSeriesArray[1], (Integer) window.getPeriodOfPredField().getValue()));
	        forecast.execute();
	        window.getTabbedPane().setSelectedIndex(2);
	        
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
    	String[] values=str.split(",");
    	int[] parsed=new int[values.length];
    	for (int i=0;i<parsed.length;i++){
    		parsed[i]=Integer.valueOf(values[i]);
    		if (parsed[i]<=0) throw new NumberFormatException();
    	}
    	return parsed;
    }
    
}
