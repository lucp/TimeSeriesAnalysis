package demo.action;

import demo.chart.TimeSeriesChart;
import demo.gui.MainWindow;
import org.jfree.ui.RefineryUtilities;

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

        JPanel panel = new TimeSeriesChart(Arrays.asList(window.getTimeSeries()));

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setVisible(true);
    }

}
