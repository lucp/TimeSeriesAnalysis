package demo.action;

import demo.chart.FitnessChart;
import demo.chart.TimeSeriesChart;
import forecasting.GAObserver;
import forecasting.model.Chromosome;
import org.jfree.data.time.TimeSeries;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;

public class GAChartObserver implements GAObserver {

    private FitnessChart chart;

    public GAChartObserver(FitnessChart chart){
        this.chart = chart;
    }

    @Override
    public void update(Object arg) {
        if(arg instanceof List){

            List<Double> list = (List<Double>) arg;

            double fitness = list.get(0);
            double forecast = list.get(1);
            int i = list.get(2).intValue();

            chart.setVisible(true);

            try{
                chart.addValue(fitness,"Max", i);
            }catch(IllegalArgumentException ex){
                System.err.println("Some minor chart problem: "+ex.getMessage());
            }

            try{
                chart.ripejnt();
                chart.pack();
                chart.validate();

            }catch(IllegalArgumentException ex){
                System.err.println("Some minor chart problem: "+ex.getMessage());
            }

        }
    }

    public void done(TimeSeries timeSeriesWithForecast){

        List<TimeSeries> timeSeriesWithForecastList = new ArrayList<>();
        timeSeriesWithForecastList.add(timeSeriesWithForecast);

        JPanel panel = new TimeSeriesChart(timeSeriesWithForecastList, true);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setVisible(true);

    }
}
