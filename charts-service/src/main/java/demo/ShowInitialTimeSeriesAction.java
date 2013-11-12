package demo;

import org.jfree.ui.RefineryUtilities;

import service.chart.TimeSeriesChart;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class ShowInitialTimeSeriesAction implements ActionListener {

    MainWindow window;

    public ShowInitialTimeSeriesAction(MainWindow window){
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	TimeSeriesChart panel = new TimeSeriesChart(Arrays.asList(window.getTimeSeries()));

        JFrame frame = new JFrame("TimeSeriesChart1");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setVisible(true);
    }

}
