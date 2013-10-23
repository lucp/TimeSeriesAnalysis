package forecasting;

import org.jfree.data.time.TimeSeries;

public interface GAObserver {

    public void update(Object arg);

    public void done(TimeSeries timeSeries);
}
