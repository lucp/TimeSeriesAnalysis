
package action;

import forecasting.AbstractForecast;
import forecasting.config.ForecastConfig;
import forecasting.config.GASettings;
import forecasting.config.SelectionMethod;
import forecasting.model.SlidingTimeWindow;
import gui.main;
import mock.MockTimeSeries;

import org.bouncycastle.crypto.DataLengthException;
import org.jfree.data.time.TimeSeries;
import org.jfree.ui.RefineryUtilities;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import service.action.GAChartObserver;
import service.chart.FitnessChart;
import service.chart.TimeSeriesChart;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class ShowTimeSeriesWithForecastAction implements ActionListener {

    main window;

    public ShowTimeSeriesWithForecastAction(main window){
        this.window = window;
    }
    
    public void actionPerformed(ActionEvent e){
    	
    	if (window.getSliderSelekcji().getValue() + window.getSliderKrzyzowania().getValue() + window.getSliderMutacji().getValue() != 100)
    	{
    			JOptionPane.showMessageDialog(null, "Prosze wprowadzic poprawne dane procentowe dla Selection, Crossing, oraz Mutation");
    			return;
    	}
    	try{
    		
	    	TimeSeries timeSeries = window.getCurrentTimeSeries();
	    	if (timeSeries==null || timeSeries.isEmpty()) throw new DataLengthException();
	        
	    	//TO DO
	    	//jak zostanie ogarniete wpisywanie do pola 'Time window' nale�y to zmodyfikowa�
	    	SlidingTimeWindow slidingTimeWindow = new SlidingTimeWindow(new int[]{2});
	
	        if(window.getRdBtnStochastic().isSelected())
	        	GASettings.getInstance().setSelectionMethod(SelectionMethod.STOCHASTIC_UNIVERSAL_SAMPLING_SELECTION);
	        
	        //TO DO   if...
	        //GASettings.getInstance().setForecastMethod(ForecastMethod.ARMA_FORECAST);
	
	        GASettings.getInstance().setConcurrent(true);
	
	        ApplicationContext context = new AnnotationConfigApplicationContext(ForecastConfig.class);
	        AbstractForecast forecast = (AbstractForecast) context.getBean("forecast");
	
	        forecast.initializeGeneticAlgorithm(
	    		(TimeSeries) timeSeries.clone(),				//TO DO
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
	        forecast.execute();
	        window.getTabbedPane().setSelectedIndex(3);
	        
        } 
    	catch (CloneNotSupportedException e1) {
            e1.printStackTrace();
        }
    	catch (DataLengthException de){
    		JOptionPane.showMessageDialog(window, "Current data is not set or empty", "Error", JOptionPane.ERROR_MESSAGE);
    	}
    	catch(Exception exc){
    		JOptionPane.showMessageDialog(window, "Unknown error", "Error", JOptionPane.ERROR_MESSAGE);
    	}
    }
}
