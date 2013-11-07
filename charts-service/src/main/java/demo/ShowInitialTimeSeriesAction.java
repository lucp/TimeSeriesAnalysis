package demo;

import org.jfree.ui.RefineryUtilities;

import service.chart.TimeSeriesChart;
import service.chart.TimeSeriesChartFrame;

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

        JFrame frame = new TimeSeriesChartFrame(Arrays.asList(window.getTimeSeries()));
    }

}
