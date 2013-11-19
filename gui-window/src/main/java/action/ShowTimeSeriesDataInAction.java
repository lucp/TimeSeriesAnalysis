
package action;

import gui.TSAFrame;

import org.bouncycastle.crypto.DataLengthException;
import org.jfree.data.time.TimeSeries;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class ShowTimeSeriesDataInAction implements ActionListener {

    TSAFrame window;

    public ShowTimeSeriesDataInAction(TSAFrame window){
        this.window = window;
    }
    
    public void actionPerformed(ActionEvent e){
    	
    	try{
    		List<TimeSeries> timeSeriesWithForecastList = new ArrayList<>();
    	
	        timeSeriesWithForecastList.add(window.getCurrentTimeSeries());
	        
	        window.getTimeSeriesChartDataIn().createChartPanel(timeSeriesWithForecastList, 0);
	        window.getTimeSeriesChartDataIn().validate();
	        window.getTabbedPane().setSelectedIndex(1);
        }
        catch (IllegalArgumentException iae){
    		JOptionPane.showMessageDialog(window, "Current data is not set or empty", "Error", JOptionPane.ERROR_MESSAGE);
    	}
    }   
}
