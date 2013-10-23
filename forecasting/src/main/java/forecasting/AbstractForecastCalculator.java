package forecasting;

import forecasting.model.Chromosome;
import forecasting.model.SlidingTimeWindow;
import org.jfree.data.time.TimeSeries;

public interface AbstractForecastCalculator {

    public Double calculateForecast(TimeSeries timeSeries,
                                    SlidingTimeWindow window,
                                    Chromosome chromosome,
                                    int currentIndex);
}
