package action;

import forecasting.GAObserver;

import org.jfree.data.time.TimeSeries;

import statistics.Statistics;

import java.util.ArrayList;
import java.util.List;

public class GAStatisticObserver implements GAObserver {

	private Statistics stat;
	private TimeSeries timeSeries;
	private int numOfDataPoints;
    
    public GAStatisticObserver(Statistics stat, TimeSeries timeSeries, int numOfDataPoints){
    	this.stat = stat;
    	this.timeSeries = timeSeries;
        this.numOfDataPoints = numOfDataPoints;
    }

    @Override
    public void update(double fitness, double[] best, int i) {
    }
    
    @Override
    public void done(TimeSeries timeSeriesWithForecast){
    	int number = timeSeriesWithForecast.getItemCount() - 1;
    	TimeSeries Forecast;
		try {
			Forecast = timeSeriesWithForecast.createCopy(number - numOfDataPoints, number);
			stat.loadTimeSeries(timeSeries, Forecast);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void done(double[] best) {
    }
}
