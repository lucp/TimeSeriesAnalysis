package mock;

import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;

import java.util.ArrayList;
import java.util.List;

public class MockTimeSeries {

    private TimeSeries s1;
    private TimeSeries s2;

    private TimeSeries mySeries;

    public MockTimeSeries(){

        createFirstMockTimeSeries();
        createSecondMockTimeSeries();
        createMyMockTimeSeries();
    }

    private void createFirstMockTimeSeries(){

        s1 = new TimeSeries("L&G European Index Trust");
        s1.add(new Month(2, 2001), 181.8);
        s1.add(new Month(3, 2001), 167.3);
        s1.add(new Month(4, 2001), 153.8);
        s1.add(new Month(5, 2001), 167.6);
        s1.add(new Month(6, 2001), 158.8);
        s1.add(new Month(7, 2001), 148.3);
        s1.add(new Month(8, 2001), 153.9);
        s1.add(new Month(9, 2001), 142.7);
        s1.add(new Month(10, 2001), 123.2);
        s1.add(new Month(11, 2001), 131.8);
        s1.add(new Month(12, 2001), 139.6);
        s1.add(new Month(1, 2002), 142.9);
        s1.add(new Month(2, 2002), 138.7);
        s1.add(new Month(3, 2002), 137.3);
        s1.add(new Month(4, 2002), 143.9);
        s1.add(new Month(5, 2002), 139.8);
        s1.add(new Month(6, 2002), 137.0);
        s1.add(new Month(7, 2002), 132.8);
    }

    private void createSecondMockTimeSeries(){

        s2 = new TimeSeries("L&G UK Index Trust");
        s2.add(new Month(2, 2001), 129.6);
        s2.add(new Month(3, 2001), 123.2);
        s2.add(new Month(4, 2001), 117.2);
        s2.add(new Month(5, 2001), 124.1);
        s2.add(new Month(6, 2001), 122.6);
        s2.add(new Month(7, 2001), 119.2);
        s2.add(new Month(8, 2001), 116.5);
        s2.add(new Month(9, 2001), 112.7);
        s2.add(new Month(10, 2001), 101.5);
        s2.add(new Month(11, 2001), 106.1);
        s2.add(new Month(12, 2001), 110.3);
        s2.add(new Month(1, 2002), 111.7);
        s2.add(new Month(2, 2002), 111.0);
        s2.add(new Month(3, 2002), 109.6);
        s2.add(new Month(4, 2002), 113.2);
        s2.add(new Month(5, 2002), 111.6);
        s2.add(new Month(6, 2002), 108.8);
        s2.add(new Month(7, 2002), 101.6);
    }

    private void createMyMockTimeSeries(){

        mySeries = new TimeSeries("My Series");
        mySeries.add(new Day(1, 1, 2013), 20);
        mySeries.add(new Day(2, 1, 2013), 40);
        mySeries.add(new Day(3, 1, 2013), 30);
        mySeries.add(new Day(4, 1, 2013), 20);
        mySeries.add(new Day(5, 1, 2013), 30);

    }

    public TimeSeries getMySeries() {
        return mySeries;
    }

    private List<TimeSeries> createMockTimeSeriesList(){

        List<TimeSeries> timeSeries = new ArrayList<>();
        timeSeries.add(s1);
        timeSeries.add(s2);
        return timeSeries;
    }

    public List<TimeSeries> getMockTimeSeriesList(){
        return createMockTimeSeriesList();
    }
}
