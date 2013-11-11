package service.action;

import forecasting.GAObserver;

import org.jfree.data.time.TimeSeries;
import service.chart.FitnessChart;
import service.chart.TimeSeriesChart;
import java.util.ArrayList;
import java.util.List;

public class GAChartObserver implements GAObserver {

    private FitnessChart fitChart;
    private TimeSeriesChart tmChart;
    private int numOfDataPoints;
    
    public GAChartObserver(FitnessChart fitChart, TimeSeriesChart tmChart, int numOfDataPoints){
        this.fitChart = fitChart;
        this.tmChart = tmChart;
        this.numOfDataPoints = numOfDataPoints;
    }

    @Override
    public void update(double fitness, double[] best, int i) {

    	fitChart.setVisible(true);

        try{
        	fitChart.addValue(fitness,"Max", i);
        }catch(IllegalArgumentException ex){
            System.err.println("Some minor chart problem: "+ex.getMessage());
        }

        try{
        	fitChart.ripejnt();
        	fitChart.validate();

        }catch(IllegalArgumentException ex){
            System.err.println("Some minor chart problem: "+ex.getMessage());
        }
    }
    
    @Override
    public void done(TimeSeries timeSeriesWithForecast){

        List<TimeSeries> timeSeriesWithForecastList = new ArrayList<>();
        timeSeriesWithForecastList.add(timeSeriesWithForecast);
        
        tmChart.createChartPanel(timeSeriesWithForecastList, this.numOfDataPoints);
        tmChart.validate();
    }

    @Override
    public void done(double[] best) {
    }
}
