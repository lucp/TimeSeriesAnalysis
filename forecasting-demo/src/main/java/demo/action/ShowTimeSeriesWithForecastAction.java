package demo.action;

import demo.chart.FitnessChart;
import forecasting.AbstractForecast;
import forecasting.config.ForecastMethod;
import forecasting.config.GASettings;
import forecasting.config.ForecastConfig;
import demo.gui.MainWindow;
import forecasting.config.SelectionMethod;
import forecasting.model.SlidingTimeWindow;
import org.jfree.data.time.TimeSeries;
import org.jfree.ui.RefineryUtilities;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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

        //GASettings.getInstance().setSelectionMethod(SelectionMethod.STOCHASTIC_UNIVERSAL_SAMPLING_SELECTION);
        GASettings.getInstance().setForecastMethod(ForecastMethod.ARMA_FORECAST);

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
