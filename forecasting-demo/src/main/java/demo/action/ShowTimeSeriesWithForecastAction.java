package demo.action;

import demo.chart.FitnessChart;
import forecasting.AbstractForecast;
import forecasting.AppConfig;
import demo.gui.MainWindow;
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

        SlidingTimeWindow slidingTimeWindow = new SlidingTimeWindow(new int[]{1, 2});

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        AbstractForecast forecast = (AbstractForecast) context.getBean("forecast");

        try {
            forecast.initializeForecast((TimeSeries) timeSeriesList.clone(),
                    100,
                    slidingTimeWindow,
                    1000,
                    1.0,
                    1.0,
                    0.4,
                    0.4,
                    0.2);
        } catch (CloneNotSupportedException e1) {
            e1.printStackTrace();
        }

        FitnessChart chart = new FitnessChart("Wykres");
        chart.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);

        forecast.addObserver(new GAChartObserver(chart));

        forecast.execute();


    }
}
