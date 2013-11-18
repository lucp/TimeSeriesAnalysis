package gui;
import data.CSVDataAcquisitor;
import data.SwingTableDataAcquisitor;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import javax.swing.ButtonGroup;

import org.jfree.data.time.TimeSeries;

import action.ShowTimeSeriesWithForecastAction;
import service.action.GAChartObserver;
import service.chart.FitnessChart;
import service.chart.TimeSeriesChart;
import statistics.Statistics;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.text.NumberFormat;
import java.util.LinkedList;

import javax.swing.JComboBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.JFormattedTextField;

public class main extends JFrame {
		
	//----------------------------Genetic----------------------------
	
	LinkedList<TimeSeries> timeSeries;
	TimeSeries currentTimeSeries;
	private FitnessChart fitnessChart;
	private TimeSeriesChart timeSeriesChart;
	JTable dataTable;
	
	//-----------------------------ImportantFrame---------------------------

	JPanel dataTablePanel;
	JScrollPane tableScrollPane;
	JTextField dateFormatTextField;
	JComboBox<String> dataComboBox;
	JTextField valueColumnTextField;
	JTextField timeColumnTextField;
	
	//-----------------------------Frme-------------------------------------
	private JFormattedTextField populSizeField;
	private JFormattedTextField iterNumberField;
	private JFormattedTextField periodOfPredField;
	private JTextField timeWindowField;
	private JLabel timeSeriesField;
	
	private JSlider sliderSelekcji;
	private JSlider sliderKrzyzowania;
	private JSlider sliderMutacji;
	private JSlider sliderProbOfMutat;
	private JSlider sliderProbOfCross;
	
	private ButtonGroup radioBtnGroup;
	private ButtonGroup radioBtnGroup2;
	private JRadioButton rdBtnRoulette;
	private JRadioButton rdBtnStochastic;
	private JRadioButton rdbtnLinearCombination;
	private JRadioButton rdbtnArmaForecast;
	
	private JTabbedPane tabbedPane;
	private JLabel selectionValueLabel;
	private JLabel crossingValueLabel;
	private JLabel mutationValueLabel;
	private JLabel probabilityMutationValueLabel;
	private JLabel probabilityCrossingValueLabel;

	
	private Statistics stat;
	private JLabel lblLowerBound;
	private JLabel lblUpperBound;
	private JLabel lblMeanSeries1;
	private JLabel lblMeanSeries2;
	private JLabel lblVarianceSeries1;
	private JLabel lblVarianceSeries2;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {			        
				    UIManager.setLookAndFeel(
				        UIManager.getSystemLookAndFeelClassName());
				}catch (Exception e){}
				
				try {
					main frame = new main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public main() {
		
		//--------------------------------MainFieldsInit-------------------------
		
		this.timeSeries=new LinkedList<TimeSeries>();
		this.currentTimeSeries=null;
		this.stat = new Statistics();
		
		//-----------------------------------------------------------------------
		
		setTitle("Time Series Analisys");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 870, 550);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JButton btnCustomValues = new JButton("Custom values");
		btnCustomValues.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		menuBar.add(btnCustomValues);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		menuBar.add(btnReset);
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ShowTimeSeriesWithForecastAction(this));
		menuBar.add(btnRun);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		NumberFormat format2 = NumberFormat.getInstance();
		NumberFormatter formatter2 = new NumberFormatter(format2);
		NumberFormat format3 = NumberFormat.getInstance();
		NumberFormatter formatter3 = new NumberFormatter(format3);
		formatter.setValueClass(Integer.class);
		formatter2.setValueClass(Integer.class);
		formatter3.setValueClass(Integer.class);
		
		radioBtnGroup = new ButtonGroup();
		radioBtnGroup2 = new ButtonGroup();
		
		//-------------------DataTable-------------------
		
		dataTablePanel = new JPanel();
		tabbedPane.addTab("Data table", null, dataTablePanel, null);
		
		String[] columnNames = {"Date","Value"};
		//Object[][] data = {{"", new Double(0)}};
		dataTablePanel.setLayout(null);
		dataTable = new JTable(new DefaultTableModel(columnNames,1));
		
		tableScrollPane = new JScrollPane(dataTable);
		tableScrollPane.setBounds(198, 5, 452, 427);
		dataTable.setFillsViewportHeight(true);
		
		dataTablePanel.add(tableScrollPane);
		
		JButton btnAddDataRow = new JButton("Add data");
		btnAddDataRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model=(DefaultTableModel)dataTable.getModel();
				model.addRow(new Object[]{"", ""});
			}
		});
		btnAddDataRow.setBounds(10, 8, 113, 23);
		dataTablePanel.add(btnAddDataRow);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO saving current state
				try{
					if (currentTimeSeries!=null){
						String df=dateFormatTextField.getText();
						SwingTableDataAcquisitor tableAcq=new SwingTableDataAcquisitor(dataTable,df);
						int currentIndex=timeSeries.indexOf(currentTimeSeries);
						currentTimeSeries=tableAcq.readData_TimeSeries((String)currentTimeSeries.getKey());
						timeSeries.remove(currentIndex);
						timeSeries.add(currentIndex, currentTimeSeries);
						dataComboBox.firePopupMenuWillBecomeVisible();
						dataComboBox.setSelectedIndex(timeSeries.indexOf(currentTimeSeries));
						timeSeriesField.setText((String)currentTimeSeries.getKey());
					}
					else{
						String df=dateFormatTextField.getText();
						String name=JOptionPane.showInputDialog(dataTablePanel,"Choose name for data");
						if (name!=null){
							SwingTableDataAcquisitor tableAcq=new SwingTableDataAcquisitor(dataTable,df);
							currentTimeSeries=tableAcq.readData_TimeSeries(name);
							timeSeries.add(currentTimeSeries);
							dataComboBox.firePopupMenuWillBecomeVisible();
							dataComboBox.setSelectedIndex(timeSeries.indexOf(currentTimeSeries));
							timeSeriesField.setText((String)currentTimeSeries.getKey());
						}
					}
				}
				catch(Exception exc){
					exc.printStackTrace();
					JOptionPane.showMessageDialog(dataTablePanel,"Table data parse error","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSave.setBounds(10, 76, 113, 23);
		dataTablePanel.add(btnSave);
		
		JButton btnDeleteDataRow = new JButton("Delete data");
		btnDeleteDataRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model=(DefaultTableModel)dataTable.getModel();
				for (@SuppressWarnings("unused") int i : dataTable.getSelectedRows()){
					model.removeRow(dataTable.getSelectedRow());
				}
			}
		});
		btnDeleteDataRow.setBounds(10, 42, 113, 23);
		dataTablePanel.add(btnDeleteDataRow);
		
		dataComboBox = new JComboBox<String>();
		dataComboBox.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				try{
					if (!timeSeries.isEmpty()){
						currentTimeSeries=timeSeries.get(dataComboBox.getSelectedIndex());
						SwingTableDataAcquisitor.updateJTable(dataTable,currentTimeSeries,dateFormatTextField.getText());
						timeSeriesField.setText((String)currentTimeSeries.getKey());
					}
				}
				catch(Exception exc){
					exc.printStackTrace();
				}
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				dataComboBox.removeAllItems();
				if (!timeSeries.isEmpty()){
					for (int i=0;i<timeSeries.size();i++){
						dataComboBox.addItem((String)timeSeries.get(i).getKey());
					}
				}
			}
		});
		dataComboBox.setBounds(660, 9, 179, 20);
		dataTablePanel.add(dataComboBox);
		
		JButton btnSaveAs = new JButton("Save as...");
		btnSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String df=dateFormatTextField.getText();
					String name=JOptionPane.showInputDialog(dataTablePanel,"Choose name for data");
					if (name!=null){
						SwingTableDataAcquisitor tableAcq=new SwingTableDataAcquisitor(dataTable,df);
						timeSeries.add(tableAcq.readData_TimeSeries(name));
						currentTimeSeries=timeSeries.getLast();
						dataComboBox.firePopupMenuWillBecomeVisible();
						dataComboBox.setSelectedIndex(timeSeries.indexOf(currentTimeSeries));
						timeSeriesField.setText((String)currentTimeSeries.getKey());
					}
				}
				catch(Exception exc){
					exc.printStackTrace();
					JOptionPane.showMessageDialog(dataTablePanel,"Table data parse error","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSaveAs.setBounds(10, 110, 113, 23);
		dataTablePanel.add(btnSaveAs);
		
		JButton btnImport = new JButton("Import");
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser importFileChooser=new JFileChooser();
				int returnVal = importFileChooser.showOpenDialog(main.this);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		        	try{
			            File file = importFileChooser.getSelectedFile();
			            CSVDataAcquisitor csvDataAcquisitor=new CSVDataAcquisitor(file.getAbsolutePath(),Integer.parseInt(timeColumnTextField.getText()),Integer.parseInt(valueColumnTextField.getText()),dateFormatTextField.getText()); //TODO wybranie column
			            timeSeries.add(csvDataAcquisitor.readData_TimeSeries());
			            currentTimeSeries=timeSeries.getLast();
			            dataComboBox.firePopupMenuWillBecomeVisible();
						dataComboBox.setSelectedIndex(timeSeries.indexOf(currentTimeSeries));
						SwingTableDataAcquisitor.updateJTable(dataTable, currentTimeSeries, dateFormatTextField.getText());
						timeSeriesField.setText((String)currentTimeSeries.getKey());
		        	}
		        	catch(Exception exc){
		        		exc.printStackTrace();
		        		JOptionPane.showMessageDialog(dataTablePanel,"File parsing error","Error",JOptionPane.ERROR_MESSAGE);
		        	}
		        } 
			}
		});
		btnImport.setBounds(10, 175, 113, 23);
		dataTablePanel.add(btnImport);
		
		dateFormatTextField = new JTextField();
		dateFormatTextField.setText("yyyy-MM-dd");
		dateFormatTextField.setBounds(743, 45, 96, 20);
		dataTablePanel.add(dateFormatTextField);
		dateFormatTextField.setColumns(10);
		
		JLabel dateFormatLabel = new JLabel("Date format:");
		dateFormatLabel.setBounds(660, 48, 86, 14);
		dataTablePanel.add(dateFormatLabel);
		
		valueColumnTextField = new JTextField();
		valueColumnTextField.setText("1");
		valueColumnTextField.setBounds(62, 231, 42, 20);
		dataTablePanel.add(valueColumnTextField);
		valueColumnTextField.setColumns(10);
		
		timeColumnTextField = new JTextField();
		timeColumnTextField.setText("0");
		timeColumnTextField.setBounds(10, 231, 42, 20);
		dataTablePanel.add(timeColumnTextField);
		timeColumnTextField.setColumns(10);
		
		JLabel lblImportColumns = new JLabel("Import columns (date-value):");
		lblImportColumns.setBounds(10, 209, 178, 14);
		dataTablePanel.add(lblImportColumns);
		
		//-------------------Charts----------------------
        //TODO chart-service
        timeSeriesChart = new TimeSeriesChart();
        tabbedPane.addTab("Time Series Chart", null, timeSeriesChart, null);
        //timeSeriesChart.createChartPanel(new LinkedList<TimeSeries>(), 0);

        fitnessChart = new FitnessChart();
        tabbedPane.addTab("Fitness Chart", null, fitnessChart, null);

        // ============ STATYSTYKI =================
        JPanel statisticsPanel = new JPanel();
        tabbedPane.addTab("Statistics", null, statisticsPanel, null);
        statisticsPanel.setLayout(null);
        
//        double[] results = stat.findBestCoefficient();
        JButton statisticRun = new JButton("Show statistics");
        statisticRun.setBounds(25, 10, 134, 16);
        statisticsPanel.add(statisticRun);
        statisticRun .addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
        JLabel correlationCoefficientLabel = new JLabel("Correlation of coeffivient: ");
        correlationCoefficientLabel.setBounds(25, 45, 174, 16);
//        JLabel correlationCoefficientValue = new JLabel(String.valueOf(results[0]));
        JLabel lowerLimitLabel = new JLabel("Lower boundary: ");
        lowerLimitLabel .setBounds(28, 73, 114, 16);
//        JLabel lowerLimitValue = new JLabel(String.valueOf(results[1]));
        JLabel upperLimitLabel = new JLabel("Upper boundary: ");
        upperLimitLabel.setBounds(28, 101, 114, 16);
//        JLabel upperLimitValue = new JLabel(String.valueOf(results[2]));
        JLabel mean1Label = new JLabel("Mean of series 1: ");
        mean1Label.setBounds(28, 129, 114, 16);
//        JLabel mean1Value = new JLabel(String.valueOf(stat.getMeanOfSeries1()));
        JLabel mean2Label = new JLabel("Mean of series 2: ");
        mean2Label.setBounds(28, 157, 114, 16);
//        JLabel mean2Value = new JLabel(String.valueOf(stat.getMeanOfSeries2()));
        JLabel var1Label = new JLabel("Variance of series 1: ");
        var1Label.setBounds(28, 185, 134, 16);
//        JLabel var1Value = new JLabel(String.valueOf(stat.getVarOfSeries1()));
        JLabel var2Label = new JLabel("Variance of series 2: ");
        var2Label.setBounds(28, 213, 134, 16);
//        JLabel var2Value = new JLabel(String.valueOf(stat.getVarOfSeries2()));
//
        statisticsPanel.add(correlationCoefficientLabel);
//        statisticsPanel.add(correlationCoefficientValue);
        statisticsPanel.add(lowerLimitLabel);
//        statisticsPanel.add(lowerLimitValue);
        statisticsPanel.add(upperLimitLabel);
//        statisticsPanel.add(upperLimitValue);
        statisticsPanel.add(mean1Label);
//        statisticsPanel.add(mean1Value);
        statisticsPanel.add(mean2Label);
//        statisticsPanel.add(mean2Value);
        statisticsPanel.add(var1Label);
//        statisticsPanel.add(var1Value);
        statisticsPanel.add(var2Label);
        
        lblLowerBound = new JLabel("0");
        lblLowerBound.setBounds(154, 73, 61, 16);
        statisticsPanel.add(lblLowerBound);
        
        lblUpperBound = new JLabel("0");
        lblUpperBound.setBounds(154, 101, 61, 16);
        statisticsPanel.add(lblUpperBound);
        
        lblMeanSeries1 = new JLabel("0");
        lblMeanSeries1.setBounds(154, 129, 61, 16);
        statisticsPanel.add(lblMeanSeries1);
        
        lblMeanSeries2 = new JLabel("0");
        lblMeanSeries2.setBounds(154, 157, 61, 16);
        statisticsPanel.add(lblMeanSeries2);
        
        lblVarianceSeries1 = new JLabel("0");
        lblVarianceSeries1.setBounds(174, 185, 61, 16);
        statisticsPanel.add(lblVarianceSeries1);
        
        lblVarianceSeries2 = new JLabel("0");
        lblVarianceSeries2.setBounds(174, 213, 61, 16);
        statisticsPanel.add(lblVarianceSeries2);
        
        JPanel panel = new JPanel();
        tabbedPane.addTab("Parameters", null, panel, null);
        panel.setLayout(null);
        
        JLabel parametr1 = new JLabel("Population size");
        parametr1.setBounds(25, 81, 134, 16);
        panel.add(parametr1);
        
        JLabel parametr2 = new JLabel("Iteration number");
        parametr2.setBounds(25, 109, 134, 16);
        panel.add(parametr2);
        
        JLabel parametr3 = new JLabel("Probability of mutation");
        parametr3.setBounds(415, 156, 170, 16);
        panel.add(parametr3);
        
        //formatowanie ograniczen dla populSizeField
        formatter.setMinimum(10);
		formatter.setMaximum(1000);
		formatter.setCommitsOnValidEdit(true);
        populSizeField = new JFormattedTextField(formatter);
        populSizeField.setToolTipText("(10-1000)");
        populSizeField.setText("100");
        populSizeField.setBounds(176, 75, 180, 28);
        panel.add(populSizeField);
        populSizeField.setColumns(10);
        
        //formatowanie ograniczen dla iterNumberField
		formatter2.setMinimum(0);
		formatter2.setMaximum(10000);
		formatter2.setCommitsOnValidEdit(true);
        iterNumberField = new JFormattedTextField(formatter2);
        iterNumberField.setToolTipText("<10000");
        iterNumberField.setText("1000");
        iterNumberField.setBounds(176, 103, 180, 28);
        panel.add(iterNumberField);
        iterNumberField.setColumns(10);
        
        sliderProbOfMutat = new JSlider(0,100,50);
        sliderProbOfMutat.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent e) {
        		int value = sliderProbOfMutat.getValue();
    			probabilityMutationValueLabel.setText(value + "%");
        	}
        });
        sliderProbOfMutat.setBounds(566, 150, 190, 29);
        panel.add(sliderProbOfMutat);
        
        JLabel lblNewLabel = new JLabel("Probability of crossing");
        lblNewLabel.setBounds(415, 188, 154, 16);
        panel.add(lblNewLabel);
        
        sliderProbOfCross = new JSlider(0,100,50);
        sliderProbOfCross.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent e) {
        		int value = sliderProbOfCross.getValue();
    			probabilityCrossingValueLabel.setText(value + "%");
        	}
        });
        sliderProbOfCross.setBounds(566, 182, 190, 29);
        panel.add(sliderProbOfCross);
        
        JLabel lblNewLabel_1 = new JLabel("Method of selection:");
        lblNewLabel_1.setBounds(25, 185, 170, 16);
        panel.add(lblNewLabel_1);
        
        rdBtnRoulette = new JRadioButton("Roulette");
        rdBtnRoulette.setBounds(50, 204, 141, 23);
        rdBtnRoulette.setSelected(true);
        panel.add(rdBtnRoulette);
        
        rdBtnStochastic = new JRadioButton("Stochastic Universal Sampling");
        rdBtnStochastic.setBounds(50, 231, 221, 23);
        panel.add(rdBtnStochastic);
        radioBtnGroup.add(rdBtnRoulette);
        radioBtnGroup.add(rdBtnStochastic);
        
        JLabel lblOkresPredykacji = new JLabel("Period of prediction");
        lblOkresPredykacji.setBounds(25, 137, 134, 16);
        panel.add(lblOkresPredykacji);
        
        //formatowanie ograniczen dla periodOfPredField
		formatter3.setMinimum(1);
		formatter3.setMaximum(Integer.MAX_VALUE);
		formatter3.setCommitsOnValidEdit(true);
        periodOfPredField = new JFormattedTextField(formatter3);
        periodOfPredField.setToolTipText(">0");
        periodOfPredField.setBounds(176, 131, 180, 28);
        panel.add(periodOfPredField);
        periodOfPredField.setColumns(10);
        
        JLabel lblNewLabel_2 = new JLabel("Time series");
        lblNewLabel_2.setBounds(25, 53, 134, 16);
        panel.add(lblNewLabel_2);
        
        timeWindowField = new JTextField();
        timeWindowField.setBounds(176, 19, 180, 28);
        panel.add(timeWindowField);
        timeWindowField.setColumns(10);
        
        JLabel lblOknoCzasowe = new JLabel("Time window");
        lblOknoCzasowe.setBounds(25, 25, 134, 16);
        panel.add(lblOknoCzasowe);
        
        timeSeriesField = new JLabel();
        timeSeriesField.setBounds(176, 47, 180, 28);
        timeSeriesField.setText("---");
        panel.add(timeSeriesField);
        
        JLabel lblProcentOsobnikwPozostwionych = new JLabel("Percent of species left after:");
        lblProcentOsobnikwPozostwionych.setBounds(415, 25, 281, 16);
        panel.add(lblProcentOsobnikwPozostwionych);
        
        JLabel lblProcentPoSelekcji = new JLabel("Selection");
        lblProcentPoSelekcji.setLabelFor(lblProcentPoSelekcji);
        lblProcentPoSelekcji.setBounds(503, 53, 66, 16);
        panel.add(lblProcentPoSelekcji);
        
        JLabel lblProcentPoKrzyzowaniu = new JLabel("Crossing");
        lblProcentPoKrzyzowaniu.setLabelFor(lblProcentPoKrzyzowaniu);
        lblProcentPoKrzyzowaniu.setBounds(503, 81, 66, 16);
        panel.add(lblProcentPoKrzyzowaniu);
        
        JLabel lblProcentPoMutacji= new JLabel("Mutation");
        lblProcentPoMutacji.setLabelFor(lblProcentPoMutacji);
        lblProcentPoMutacji.setBounds(503, 109, 66, 16);
        panel.add(lblProcentPoMutacji);
        
        	sliderSelekcji = new JSlider(0,100,40);
        	sliderSelekcji.addChangeListener(new ChangeListener() {
        		public void stateChanged(ChangeEvent e) {
        			int value = sliderSelekcji.getValue();
        			selectionValueLabel.setText(value + "%");
        			
        			int difference = sliderSelekcji.getValue() + sliderKrzyzowania.getValue() + sliderMutacji.getValue() - 100;
        			if (difference > 0) {
						sliderKrzyzowania.setValue(sliderKrzyzowania.getValue() - difference + difference / 2);
						sliderMutacji.setValue(sliderMutacji.getValue() - difference + difference / 2);
					}
        			
        		}
        	});
        	sliderSelekcji.setBounds(566, 47, 190, 29);
        	panel.add(sliderSelekcji);
        	
        	sliderKrzyzowania = new JSlider(0,100,40);
        	sliderKrzyzowania.addChangeListener(new ChangeListener() {
        		public void stateChanged(ChangeEvent e) {
        			int value = sliderKrzyzowania.getValue();
        			crossingValueLabel.setText(value + "%");
        			
        			int difference = sliderSelekcji.getValue() + sliderKrzyzowania.getValue() + sliderMutacji.getValue() - 100;
        			if (difference > 0) {
						sliderSelekcji.setValue(sliderSelekcji.getValue() - difference + difference / 2);
						sliderMutacji.setValue(sliderMutacji.getValue() - difference + difference / 2);
					}
        		}
        	});
        	sliderKrzyzowania.setBounds(566, 74, 190, 29);
        	panel.add(sliderKrzyzowania);
        	
        	sliderMutacji = new JSlider(0,100,20);
        	sliderMutacji.addChangeListener(new ChangeListener() {		
				public void stateChanged(ChangeEvent e) {
					int value = sliderMutacji.getValue();
        			mutationValueLabel.setText(value + "%");
        			
        			int difference = sliderSelekcji.getValue() + sliderKrzyzowania.getValue() + sliderMutacji.getValue() - 100;
        			if (difference > 0) {
						sliderKrzyzowania.setValue(sliderKrzyzowania.getValue() - difference + difference / 2);
						sliderSelekcji.setValue(sliderSelekcji.getValue() - difference + difference / 2);
					}
				}
			});
        	sliderMutacji.setBounds(566, 102, 190, 29);
        	panel.add(sliderMutacji);
        	
        	selectionValueLabel = new JLabel();
        	selectionValueLabel.setText("40%");
        	selectionValueLabel.setBounds(755, 47, 50, 28);
        	panel.add(selectionValueLabel);
        	
        	crossingValueLabel = new JLabel();
        	crossingValueLabel.setText("40%");
        	crossingValueLabel.setBounds(755, 75, 50, 28);
        	panel.add(crossingValueLabel);
        	
        	mutationValueLabel = new JLabel();
        	mutationValueLabel.setText("20%");
        	mutationValueLabel.setBounds(755, 103, 50, 28);
        	panel.add(mutationValueLabel);
        	
        	probabilityMutationValueLabel = new JLabel();
        	probabilityMutationValueLabel.setText("50%");
        	probabilityMutationValueLabel.setBounds(755, 150, 50, 28);
        	panel.add(probabilityMutationValueLabel);
        	
        	probabilityCrossingValueLabel = new JLabel();
        	probabilityCrossingValueLabel.setText("50%");
        	probabilityCrossingValueLabel.setBounds(755, 182, 50, 28);
        	panel.add(probabilityCrossingValueLabel);
        	
        	JLabel lblMethodOfCounting = new JLabel("Method of counting prediction:");
        	lblMethodOfCounting.setBounds(25, 282, 215, 16);
        	panel.add(lblMethodOfCounting);
        	
        	rdbtnLinearCombination = new JRadioButton("Linear combination");
        	rdbtnLinearCombination.setBounds(50, 304, 170, 23);
        	rdbtnLinearCombination.setSelected(true);
        	panel.add(rdbtnLinearCombination);
        	  
        	rdbtnArmaForecast = new JRadioButton("Arma forecast");
        	rdbtnArmaForecast.setBounds(50, 331, 141, 23);
        	panel.add(rdbtnArmaForecast);
        	        
        	radioBtnGroup2.add(rdbtnLinearCombination);
        	radioBtnGroup2.add(rdbtnArmaForecast);
        	
//        statisticsPanel.add(var2Value);
        //================================
        
        JPanel logsPanel = new JPanel();
        tabbedPane.addTab("Logi", null, logsPanel, null);
    }
	
	public JLabel getLblLowerBound() {
		return lblLowerBound;
	}

	public JLabel getLblUpperBound() {
		return lblUpperBound;
	}

	public JLabel getLblMeanSeries1() {
		return lblMeanSeries1;
	}

	public JLabel getLblMeanSeries2() {
		return lblMeanSeries2;
	}

	public JLabel getLblVarianceSeries1() {
		return lblVarianceSeries1;
	}

	public JLabel getLblVarianceSeries2() {
		return lblVarianceSeries2;
	}

	public JLabel getSelectionValueLabel() {
		return selectionValueLabel;
	}

	public JLabel getCrossingValueLabel() {
		return crossingValueLabel;
	}

	public JLabel getMutationValueLabel() {
		return mutationValueLabel;
	}

	public TimeSeries getCurrentTimeSeries() {
		return currentTimeSeries;
	}

	public JFormattedTextField getPopulSizeField() {
		return populSizeField;
	}

	public JFormattedTextField getIterNumberField() {
		return iterNumberField;
	}

	public JFormattedTextField getPeriodOfPredField() {
		return periodOfPredField;
	}

	public JTextField getTimeWindowField() {
		return timeWindowField;
	}

	public JLabel getTimeSeriesField() {
		return timeSeriesField;
	}

	public JSlider getSliderSelekcji() {
		return sliderSelekcji;
	}

	public JSlider getSliderKrzyzowania() {
		return sliderKrzyzowania;
	}

	public JSlider getSliderMutacji() {
		return sliderMutacji;
	}

	public JSlider getSliderProbOfMutat() {
		return sliderProbOfMutat;
	}

	public JSlider getSliderProbOfCross() {
		return sliderProbOfCross;
	}

	public ButtonGroup getRadioBtnGroup() {
		return radioBtnGroup;
	}

	public JRadioButton getRdBtnRoulette() {
		return rdBtnRoulette;
	}

	public JRadioButton getRdBtnStochastic() {
		return rdBtnStochastic;
	}

	public FitnessChart getFitnessChart() {
		return fitnessChart;
	}

	public TimeSeriesChart getTimeSeriesChart() {
		return timeSeriesChart;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public Statistics getStat() {
		return stat;
	}

	public JRadioButton getRdbtnArmaForecast() {
		return rdbtnArmaForecast;
	}
}
