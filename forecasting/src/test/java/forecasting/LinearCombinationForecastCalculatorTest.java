package forecasting;

import forecasting.config.ForecastConfig;
import mock.MockTimeSeries;
import forecasting.model.Chromosome;
import forecasting.model.SlidingTimeWindow;
import org.jfree.data.time.TimeSeries;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.*;

public class LinearCombinationForecastCalculatorTest {
    @Test
    public void testCalculateForecast() throws Exception {

        TimeSeries timeSeries = new MockTimeSeries().getMySeries();

        SlidingTimeWindow slidingTimeWindow = new SlidingTimeWindow(new int[]{1, 2, 3});

        ApplicationContext context = new AnnotationConfigApplicationContext(ForecastConfig.class);
        AbstractForecastCalculator calculator =
                (AbstractForecastCalculator) context.getBean("forecastCalculator");

        Chromosome chromosome = new Chromosome(new double[]{-0.5, 1, 0.5, 0.2});

        assertEquals(53.5, calculator.calculateForecast(timeSeries,
                slidingTimeWindow,
                chromosome,
                3),
                0.00001);

        assertEquals(42.5, calculator.calculateForecast(timeSeries,
                slidingTimeWindow,
                chromosome,
                4),
                0.00001);

        assertNull(calculator.calculateForecast(timeSeries,
                slidingTimeWindow,
                chromosome,
                2));
    }
}
