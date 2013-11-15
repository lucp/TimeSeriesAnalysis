package demo.gui;

import demo.action.ShowInitialTimeSeriesAction;
import demo.action.ShowTimeSeriesWithForecastAction;
import demo.action.ShowTimeSeriesWithForecastConcurrentAction;
import mock.MockTimeSeries;
import org.jfree.data.time.TimeSeries;

import javax.swing.*;
import java.awt.*;


public class MainWindow extends JFrame{

    private TimeSeries timeSeries;

    public MainWindow(){

        timeSeries = new MockTimeSeries().getMySeries();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.getContentPane().setLayout(new BorderLayout());

        JPanel textPanel = new JPanel();
        textPanel.add(new JLabel("Time Series Forecasting Demo"));
        this.add(textPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();

        JButton showInitialTimeSeries = new JButton("Show initial time series");
        showInitialTimeSeries.addActionListener(new ShowInitialTimeSeriesAction(this));
        buttonPanel.add(showInitialTimeSeries);

        JButton showTimeSeriesWithForecast = new JButton("Show time series with forecast");
        showTimeSeriesWithForecast.addActionListener(new ShowTimeSeriesWithForecastAction(this));
        buttonPanel.add(showTimeSeriesWithForecast);

        JButton showTimeSeriesWithForecastConcurrent = new JButton("Show time series with forecast concurrent");
        showTimeSeriesWithForecastConcurrent.addActionListener(new ShowTimeSeriesWithForecastConcurrentAction(this));
        buttonPanel.add(showTimeSeriesWithForecastConcurrent);

        this.add(buttonPanel, BorderLayout.CENTER);

        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args){

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainWindow();
            }
        });
    }

    public TimeSeries getTimeSeries(){
        return timeSeries;
    }

}
