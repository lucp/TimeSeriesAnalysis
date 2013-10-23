package forecasting;

import forecasting.model.SlidingTimeWindow;
import org.jfree.data.time.TimeSeries;

import javax.swing.*;

public abstract class AbstractForecast extends SwingWorker<TimeSeries, Double[]> {

    public abstract void initializeForecast(TimeSeries timeSeries,
                                            int populationSize,
                                            SlidingTimeWindow slidingTimeWindow,
                                            int numOfIterations,
                                            double crossoverProbability,
                                            double mutationProbability,
                                            double percentOfKeptFromSelection ,
                                            double percentOfKeptFromCrossover,
                                            double percentOfKeptFromMutation);

    public abstract void addObserver(GAObserver o);
}
