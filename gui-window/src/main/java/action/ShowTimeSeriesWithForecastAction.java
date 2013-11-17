package action;

import forecasting.AbstractForecast;
import forecasting.config.ForecastConfig;
import forecasting.config.GASettings;
import forecasting.config.SelectionMethod;
import forecasting.model.SlidingTimeWindow;
import gui.main;
import mock.MockTimeSeries;

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
    
    public void actionPerformed(ActionEvent e) {
    	
    	//przyk³adowy szereg czasowy, nale¿y wywaliæ
    	TimeSeries timeSeries = new MockTimeSeries().getMySeries();
        
    	//TO DO
    	//jak zostanie ogarniete wpisywanie do pola 'Time window' nale¿y to zmodyfikowaæ
    	SlidingTimeWindow slidingTimeWindow = new SlidingTimeWindow(new int[]{2});

        if(window.getRdBtnStochastic().isSelected())
        	GASettings.getInstance().setSelectionMethod(SelectionMethod.STOCHASTIC_UNIVERSAL_SAMPLING_SELECTION);
        
        //TO DO   if...
        //GASettings.getInstance().setForecastMethod(ForecastMethod.ARMA_FORECAST);

        GASettings.getInstance().setConcurrent(true);

        ApplicationContext context = new AnnotationConfigApplicationContext(ForecastConfig.class);
        AbstractForecast forecast = (AbstractForecast) context.getBean("forecast");

        try {
            forecast.initializeGeneticAlgorithm(
            		(TimeSeries) timeSeries.clone(),				//TO DO
                    (Integer) window.getPopulSizeField().getValue(),
                    slidingTimeWindow,				                //TO DO
                    (Integer) window.getIterNumberField().getValue(),
                    window.getSliderProbOfCross().getValue(),
                    window.getSliderProbOfMutat().getValue(),
                    window.getSliderSelekcji().getValue(),
                    window.getSliderKrzyzowania().getValue(),
                    window.getSliderMutacji().getValue());
            forecast.initializeForecast((Integer) window.getPeriodOfPredField().getValue());
        } catch (CloneNotSupportedException e1) {
            e1.printStackTrace();
        }
        
        forecast.addObserver(new GAChartObserver(window.getFitnessChart(), window.getTimeSeriesChart(), (Integer) window.getPeriodOfPredField().getValue()));
        forecast.execute();
        window.getTabbedPane().setSelectedIndex(3);
    }
}
