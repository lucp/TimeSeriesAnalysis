package demo;

import forecasting.AbstractForecast;
import forecasting.config.ForecastConfig;
import forecasting.model.SlidingTimeWindow;

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

public class ShowTimeSeriesWithForecastAction implements ActionListener {

    MainWindow window;

    public ShowTimeSeriesWithForecastAction(MainWindow window){
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        TimeSeries timeSeriesList = window.getTimeSeries();

        SlidingTimeWindow slidingTimeWindow = new SlidingTimeWindow(new int[]{2});

        // zmienilem zdanie :) zrobilem Singletona z ustawieniami,
        // jak potrzeba innej metody selekcji/innej metody obliczania forecastu trzeba ustawic ustawienie,
        // config zostaje jeden, z ifami
        // czyli na przyklad, chcemy metode selekcji stochastic universal sampling, robimy:

        // GASettings.getInstance().setSelectionMethod(SelectionMethod.STOCHASTIC_UNIVERSAL_SAMPLING_SELECTION);

        // i smiga

        ApplicationContext context = new AnnotationConfigApplicationContext(ForecastConfig.class);
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
            forecast.initializeForecast(5);
        } catch (CloneNotSupportedException e1) {
            e1.printStackTrace();
        }
        
        TimeSeriesChart tmChart = new TimeSeriesChart();
        
        JFrame frame1 = new JFrame("TimeSeriesChart2");
        frame1.setPreferredSize(new java.awt.Dimension(700,500));
        frame1.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame1.add(tmChart);
        frame1.pack();
        RefineryUtilities.centerFrameOnScreen(frame1);
        frame1.setVisible(true);
        
        
        FitnessChart fitChart = new FitnessChart();
               
        JFrame frame2 = new JFrame("Fitness");
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame2.add(fitChart);
        frame2.pack();
        RefineryUtilities.centerFrameOnScreen(frame2);
        frame2.setVisible(true);

        //ChartObserver obserwuje algo i uaktualnia wykres
        forecast.addObserver(new GAChartObserver(fitChart, tmChart, 5));

        //proponuje dodac drugiego observera ktory zapisze jakie geny wyszly na koncu

        //w ten sposob sie odpala algorytm genetyczny, czyli wyszukiwanie nowego rozwiazania
        forecast.execute();

        //jesli chcemy zrobic predykcje na podstawie starego rozwiazania, to metoda doForecast, wtedy nie musimy
        //robic zadnego initialize, i nie odpala sie przez execute i nie uzywa sie observerow, zwykle wywolanie

    }
}
