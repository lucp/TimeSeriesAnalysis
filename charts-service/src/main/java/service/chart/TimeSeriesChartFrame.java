package service.chart;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.jfree.data.time.TimeSeries;
import org.jfree.ui.RefineryUtilities;

public class TimeSeriesChartFrame extends JFrame {
	private TimeSeriesChart tmchart;
	
	public TimeSeriesChartFrame(List<TimeSeries> timeSeriesList){
        this(timeSeriesList, false);
    }

    public TimeSeriesChartFrame(List<TimeSeries> timeSeriesList, boolean highlightLast){
    	tmchart = new TimeSeriesChart(timeSeriesList, highlightLast);
    	
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.add(tmchart);
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }
}
