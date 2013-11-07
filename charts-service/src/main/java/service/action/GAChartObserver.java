package service.action;

import forecasting.GAObserver;

import org.jfree.data.time.TimeSeries;
import org.jfree.ui.RefineryUtilities;

import service.chart.FitnessChart;
import service.chart.TimeSeriesChart;
import service.chart.TimeSeriesChartFrame;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GAChartObserver implements GAObserver {

    private FitnessChart chart;

    public GAChartObserver(FitnessChart chart){
        this.chart = chart;
    }

    @Override
    public void update(double fitness, double[] best, int i) {

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

    public void done(TimeSeries timeSeriesWithForecast){

        List<TimeSeries> timeSeriesWithForecastList = new ArrayList<>();
        timeSeriesWithForecastList.add(timeSeriesWithForecast);

        /*JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setVisible(true);*/
        
        JFrame frame = new TimeSeriesChartFrame(timeSeriesWithForecastList, true);

    }

    @Override
    public void done(double[] best) {
    }
}
