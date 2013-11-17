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
	private JTextField timeSeriesField;
	
	private JSlider sliderSelekcji;
	private JSlider sliderKrzyzowania;
	private JSlider sliderMutacji;
	private JSlider sliderProbOfMutat;
	private JSlider sliderProbOfCross;
	
	private ButtonGroup radioBtnGroup;
	private JRadioButton rdBtnRoulette;
	private JRadioButton rdBtnStochastic;
	
	private JTabbedPane tabbedPane;

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
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Parameters", null, panel, null);
		panel.setLayout(null);
		
		JLabel parametr1 = new JLabel("Population size");
		parametr1.setBounds(28, 81, 134, 16);
		panel.add(parametr1);
		
		JLabel parametr2 = new JLabel("Iteration number");
		parametr2.setBounds(28, 109, 134, 16);
		panel.add(parametr2);
		
		JLabel parametr3 = new JLabel("Probability of mutation");
		parametr3.setBounds(28, 145, 266, 16);
		panel.add(parametr3);
		
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(10);
		formatter.setMaximum(1000);
		formatter.setCommitsOnValidEdit(true);
		populSizeField = new JFormattedTextField(formatter);
		populSizeField.setToolTipText("(10-1000)");
		populSizeField.setText("100");
		populSizeField.setBounds(145, 77, 134, 28);
		panel.add(populSizeField);
		populSizeField.setColumns(10);
		
		formatter.setMinimum(0);
		formatter.setMaximum(10000);
		iterNumberField = new JFormattedTextField(formatter);
		iterNumberField.setToolTipText("<10000");
		iterNumberField.setText("1000");
		iterNumberField.setBounds(145, 105, 134, 28);
		panel.add(iterNumberField);
		iterNumberField.setColumns(10);
		
		sliderProbOfMutat = new JSlider(0,100,50);
		sliderProbOfMutat.setBounds(28, 165, 190, 29);
		panel.add(sliderProbOfMutat);
		
		JLabel lblNewLabel = new JLabel("Probability of crossing");
		lblNewLabel.setBounds(28, 205, 266, 16);
		panel.add(lblNewLabel);
		
		sliderProbOfCross = new JSlider(0,100,50);
		sliderProbOfCross.setBounds(28, 225, 190, 29);
		panel.add(sliderProbOfCross);
		
		JLabel lblNewLabel_1 = new JLabel("Method of selection");
		lblNewLabel_1.setBounds(297, 34, 170, 16);
		panel.add(lblNewLabel_1);
		
		rdBtnRoulette = new JRadioButton("Roulette");
		rdBtnRoulette.setBounds(317, 70, 141, 23);
		rdBtnRoulette.setSelected(true);
		panel.add(rdBtnRoulette);
		
		rdBtnStochastic = new JRadioButton("Stochastic Universal Sampling");
		rdBtnStochastic.setBounds(317, 97, 221, 23);
		panel.add(rdBtnStochastic);
		
		radioBtnGroup = new ButtonGroup();
		radioBtnGroup.add(rdBtnRoulette);
		radioBtnGroup.add(rdBtnStochastic);
		
		JLabel lblOkresPredykacji = new JLabel("Period of prediction");
		lblOkresPredykacji.setBounds(297, 149, 170, 16);
		panel.add(lblOkresPredykacji);
		
		formatter.setMinimum(1);
		formatter.setMaximum(Integer.MAX_VALUE);
		periodOfPredField = new JFormattedTextField(formatter);
		periodOfPredField.setToolTipText(">0");
		periodOfPredField.setBounds(307, 166, 160, 28);
		panel.add(periodOfPredField);
		periodOfPredField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Time series");
		lblNewLabel_2.setBounds(28, 53, 134, 16);
		panel.add(lblNewLabel_2);
		
		timeWindowField = new JTextField();
		timeWindowField.setBounds(145, 49, 134, 28);
		panel.add(timeWindowField);
		timeWindowField.setColumns(10);
		
		JLabel lblOknoCzasowe = new JLabel("Time window");
		lblOknoCzasowe.setBounds(28, 25, 134, 16);
		panel.add(lblOknoCzasowe);
		
		timeSeriesField = new JTextField();
		timeSeriesField.setBounds(145, 21, 134, 28);
		panel.add(timeSeriesField);
		timeSeriesField.setColumns(10);
		
		JLabel lblProcentOsobnikwPozostwionych = new JLabel("Percent of species left after:");
		lblProcentOsobnikwPozostwionych.setBounds(562, 34, 281, 16);
		panel.add(lblProcentOsobnikwPozostwionych);
		
		JLabel lblProcentPoSelekcji = new JLabel("Selection");
		lblProcentPoSelekcji.setLabelFor(lblProcentPoSelekcji);
		lblProcentPoSelekcji.setBounds(583, 71, 80, 16);
		panel.add(lblProcentPoSelekcji);
		
		JLabel lblProcentPoKrzyzowaniu = new JLabel("Crossing");
		lblProcentPoKrzyzowaniu.setLabelFor(lblProcentPoKrzyzowaniu);
		lblProcentPoKrzyzowaniu.setBounds(583, 101, 80, 16);
		panel.add(lblProcentPoKrzyzowaniu);
		
		JLabel lblProcentPoMutacji= new JLabel("Mutation");
		lblProcentPoMutacji.setLabelFor(lblProcentPoMutacji);
		lblProcentPoMutacji.setBounds(583, 131, 80, 16);
		panel.add(lblProcentPoMutacji);
	
		sliderSelekcji = new JSlider(0,100,40);
		sliderSelekcji.setBounds(653, 62, 190, 29);
		panel.add(sliderSelekcji);
		
		sliderKrzyzowania = new JSlider(0,100,40);
		sliderKrzyzowania.setBounds(653, 92, 190, 29);
		panel.add(sliderKrzyzowania);
		
		sliderMutacji = new JSlider(0,100,20);
		sliderMutacji.setBounds(653, 122, 190, 29);
		panel.add(sliderMutacji);
		
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
					}
					else{
						String df=dateFormatTextField.getText();
						String name=JOptionPane.showInputDialog(dataTablePanel,"Choose name for data");
						if (name!=null){
							SwingTableDataAcquisitor tableAcq=new SwingTableDataAcquisitor(dataTable,df);
							timeSeries.add(tableAcq.readData_TimeSeries(name));
							currentTimeSeries=timeSeries.getLast();
							dataComboBox.firePopupMenuWillBecomeVisible();
							dataComboBox.setSelectedIndex(timeSeries.indexOf(currentTimeSeries));
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

        //TODO: trzeba wprowadzi� zmienne z szeregami do por�wnania
//        Statistics stat = new Statistics(null, null);
//        double[] results = stat.findBestCoefficient();
//        JLabel correlationCoefficientLabel = new JLabel("Wsp\\u00f3\\u0142czynnik korelacji: ");
//        JLabel correlationCoefficientValue = new JLabel(String.valueOf(results[0]));
//        JLabel lowerLimitLabel = new JLabel("Dolna granica: ");
//        JLabel lowerLimitValue = new JLabel(String.valueOf(results[1]));
//        JLabel upperLimitLabel = new JLabel("G\\u00f3rna granica: ");
//        JLabel upperLimitValue = new JLabel(String.valueOf(results[2]));
//        JLabel mean1Label = new JLabel("\\u015arednia szeregu 1: ");
//        JLabel mean1Value = new JLabel(String.valueOf(stat.getMeanOfSeries1()));
//        JLabel mean2Label = new JLabel("\\u015arednia szeregu 2: ");
//        JLabel mean2Value = new JLabel(String.valueOf(stat.getMeanOfSeries2()));
//        JLabel var1Label = new JLabel("Wariancja szeregu 1: ");
//        JLabel var1Value = new JLabel(String.valueOf(stat.getVarOfSeries1()));
//        JLabel var2Label = new JLabel("Wariancja szeregu 2: ");
//        JLabel var2Value = new JLabel(String.valueOf(stat.getVarOfSeries2()));
//
//        statisticsPanel.add(correlationCoefficientLabel);
//        statisticsPanel.add(correlationCoefficientValue);
//        statisticsPanel.add(lowerLimitLabel);
//        statisticsPanel.add(lowerLimitValue);
//        statisticsPanel.add(upperLimitLabel);
//        statisticsPanel.add(upperLimitValue);
//        statisticsPanel.add(mean1Label);
//        statisticsPanel.add(mean1Value);
//        statisticsPanel.add(mean2Label);
//        statisticsPanel.add(mean2Value);
//        statisticsPanel.add(var1Label);
//        statisticsPanel.add(var1Value);
//        statisticsPanel.add(var2Label);
//        statisticsPanel.add(var2Value);
        //================================
        
        
        
        JPanel logsPanel = new JPanel();
        tabbedPane.addTab("Logi", null, logsPanel, null);
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

	public JTextField getTimeSeriesField() {
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
	
}
