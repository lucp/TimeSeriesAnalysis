package demo;

import forecasting.AbstractForecast;
import forecasting.config.RouletteWheelConfig;
import forecasting.model.SlidingTimeWindow;

import org.jfree.data.time.TimeSeries;
import org.jfree.ui.RefineryUtilities;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import service.action.GAChartObserver;
import service.chart.FitnessChart;
import service.chart.TimeSeriesChart;
import service.chart.TimeSeriesChartFrame;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class ShowTimeSeriesWithForecastAction implements ActionListener {

    MainWindow window;

    public ShowTimeSeriesWithForecastAction(MainWindow window){
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        TimeSeries timeSeriesList = window.getTimeSeries();

        SlidingTimeWindow slidingTimeWindow = new SlidingTimeWindow(new int[]{2});

        //Jesli potrzeba innej selekcji bierzemy inny config, z pakietu forecasting.config
        ApplicationContext context = new AnnotationConfigApplicationContext(RouletteWheelConfig.class);
        AbstractForecast forecast = (AbstractForecast) context.getBean("forecast");

        try {
            forecast.initializeGeneticAlgorithm((TimeSeries) timeSeriesList.clone(),
                    100,
                    slidingTimeWindow,
                    1000,
                    1.0,
                    1.0,
                    0.4,
                    0.4,
                    0.2);
            forecast.initializeForecast(3);
        } catch (CloneNotSupportedException e1) {
            e1.printStackTrace();
        }

        FitnessChart chart = new FitnessChart("Wykres");
        
        chart.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);

        //ChartObserver obserwuje algo i uaktualnia wykres
        forecast.addObserver(new GAChartObserver(chart));

        //proponuje dodac drugiego observera ktory zapisze jakie geny wyszly na koncu

        //w ten sposob sie odpala algorytm genetyczny, czyli wyszukiwanie nowego rozwiazania
        forecast.execute();

        //jesli chcemy zrobic predykcje na podstawie starego rozwiazania, to metoda doForecast, wtedy nie musimy
        //robic zadnego initialize, i nie odpala sie przez execute i nie uzywa sie observerow, zwykle wywolanie

    }
}
