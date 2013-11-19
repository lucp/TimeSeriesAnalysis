package action;

import forecasting.GAObserver;

import org.jfree.data.time.TimeSeries;

import statistics.Statistics;

import java.util.ArrayList;
import java.util.List;

public class GAStatisticObserver implements GAObserver {

	private TimeSeries forecast;
	private int numOfDataPoints;
    
    public GAStatisticObserver(TimeSeries forecast, int numOfDataPoints){
    	this.forecast = forecast;
        this.numOfDataPoints = numOfDataPoints;
    }

    @Override
    public void update(double fitness, double[] best, int i) {
    }
    
    @Override
    public void done(TimeSeries timeSeriesWithForecast){
    	int number = timeSeriesWithForecast.getItemCount() - 1;
		try {
			forecast = timeSeriesWithForecast.createCopy(number - numOfDataPoints, number);
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void done(double[] best) {
    }
}
