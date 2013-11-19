package action;

import gui.TSAFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResetChartsAndParameters implements ActionListener {
	
	TSAFrame window;
	
	public ResetChartsAndParameters(TSAFrame window) {
		this.window = window;
	}

	public void actionPerformed(ActionEvent arg0) {
		window.getTimeWindowField().setText(null);
		window.getPopulSizeField().setText("100");
		window.getIterNumberField().setText("1000");
		window.getPeriodOfPredField().setText(null);
		window.getRdBtnRoulette().setSelected(true);
		window.getRdbtnLinearCombination().setSelected(true);
		window.getSliderSelekcji().setValue(40);
		window.getSliderKrzyzowania().setValue(40);
		window.getSliderMutacji().setValue(20);
		window.getSliderKrzyzowania().setValue(40);
		window.getSliderSelekcji().setValue(40);
		window.getSliderProbOfCross().setValue(50);
		window.getSliderProbOfMutat().setValue(50);
		
		window.getTimeSeriesChartDataIn().clear();
		window.getTimeSeriesChartWithForecast().clear();
		window.getFitnessChart().clear();
		
	}

}
