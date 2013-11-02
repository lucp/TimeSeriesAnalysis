package forecasting;

import forecasting.config.RouletteWheelConfig;
import mock.MockTimeSeries;
import forecasting.model.Chromosome;
import forecasting.model.SlidingTimeWindow;
import org.jfree.data.time.TimeSeries;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertEquals;

public class RMSEFitnessCalculatorTest {
    @Test
    public void testCalculateFitness() throws Exception {

        TimeSeries timeSeries = new MockTimeSeries().getMySeries();

        SlidingTimeWindow slidingTimeWindow = new SlidingTimeWindow(new int[]{1, 2, 3});

        ApplicationContext context = new AnnotationConfigApplicationContext(RouletteWheelConfig.class);
        AbstractFitnessCalculator calculator =
                (AbstractFitnessCalculator) context.getBean("fitnessCalculator");

        Chromosome chromosome = new Chromosome(new double[]{-0.5, 1, 0.5, 0.2});

        assertEquals(25.283, calculator.calculateFitness(timeSeries,
                slidingTimeWindow,
                chromosome),
                0.001);
    }
}
