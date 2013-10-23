package forecasting;

import forecasting.model.Chromosome;
import forecasting.model.SlidingTimeWindow;
import org.jfree.data.time.TimeSeries;

public interface AbstractFitnessCalculator {

    public double calculateFitness(TimeSeries timeSeries,
                                   SlidingTimeWindow window,
                                   Chromosome chromosome);
}
