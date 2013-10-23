package demo.chart;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.event.ChartChangeEvent;

/**
 * 
 * An extension of ChartPanel class done only to override standard chartChanged event handler 
 * to add the functionality of user defined chart repaint intervals, to make the app more efficient
 * 
 * @author Olo
 *
 */
public class MyChartPanel extends ChartPanel{

	private static final long serialVersionUID = 6046366297214274674L;
	

	
	public MyChartPanel(JFreeChart chart) {
		super(chart);
	}

	public void chartChanged(ChartChangeEvent event) {
    }
	
}
