
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

import org.jfree.data.time.TimeSeries;

import service.action.GAChartObserver;
import service.chart.FitnessChart;
import service.chart.TimeSeriesChart;

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
    FitnessChart fitnessChart;
    TimeSeriesChart timeSeriesChart;
    GAChartObserver gaChartObserver;
    JTable dataTable;

	//-----------------------------ImportantFrame---------------------------
    JPanel dataTablePanel;
    JScrollPane tableScrollPane;
    JTextField dateFormatTextField;
    JComboBox<String> dataComboBox;
    JTextField valueColumnTextField;
    JTextField timeColumnTextField;

    //-----------------------------Frme-------------------------------------
    private JFormattedTextField textField;
    private JFormattedTextField textField_1;
    private JFormattedTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;

    private ChangeListener listener;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                }

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
        this.timeSeries = new LinkedList<TimeSeries>();
        this.currentTimeSeries = null;

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
        btnRun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            }
        });
        menuBar.add(btnRun);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        tabbedPane.addTab("Parameters", null, panel, null);
        panel.setLayout(null);

        JLabel parametr1 = new JLabel("Rozmiar populacji:");
        parametr1.setBounds(28, 81, 134, 16);
        panel.add(parametr1);

        JLabel parametr2 = new JLabel("Liczba iteracji:");
        parametr2.setBounds(28, 109, 134, 16);
        panel.add(parametr2);

        JLabel parametr3 = new JLabel("Prawdopodobie\u0144stwo mutacji:");
        parametr3.setBounds(28, 145, 266, 16);
        panel.add(parametr3);

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(10);
        formatter.setMaximum(1000);
        formatter.setCommitsOnValidEdit(true);
        textField = new JFormattedTextField(formatter);
        textField.setToolTipText("(10-1000)");
        textField.setText("100");
        textField.setBounds(160, 75, 134, 28);
        panel.add(textField);
        textField.setColumns(10);

        formatter.setMinimum(0);
        formatter.setMaximum(10000);
        textField_1 = new JFormattedTextField(formatter);
        textField_1.setToolTipText("<10000");
        textField_1.setText("1000");
        textField_1.setBounds(160, 103, 134, 28);
        panel.add(textField_1);
        textField_1.setColumns(10);

        JSlider slider = new JSlider(0, 100);
        slider.setBounds(28, 165, 190, 29);
        slider.addChangeListener(listener);

        panel.add(slider);

        JLabel lblNewLabel = new JLabel("Prawdopodobie\u0144stwo krzy\u017Cowania:");
        lblNewLabel.setBounds(28, 205, 266, 16);
        panel.add(lblNewLabel);

        JSlider slider_1 = new JSlider(0, 100);
        slider_1.setBounds(28, 225, 190, 29);
        panel.add(slider_1);

        JLabel lblNewLabel_1 = new JLabel("Metody selekcji:");
        lblNewLabel_1.setBounds(321, 34, 170, 16);
        panel.add(lblNewLabel_1);

        JRadioButton rdbtnMetoda = new JRadioButton("metoda1");
        rdbtnMetoda.setBounds(399, 61, 141, 23);
        panel.add(rdbtnMetoda);

        JRadioButton rdbtnMetoda_1 = new JRadioButton("metoda2");
        rdbtnMetoda_1.setBounds(399, 91, 141, 23);
        panel.add(rdbtnMetoda_1);

        JRadioButton rdbtnMetoda_2 = new JRadioButton("metoda3");
        rdbtnMetoda_2.setBounds(399, 121, 141, 23);
        panel.add(rdbtnMetoda_2);

        JRadioButton rdbtnMetoda_3 = new JRadioButton("metoda4");
        rdbtnMetoda_3.setBounds(399, 151, 141, 23);
        panel.add(rdbtnMetoda_3);

        JLabel lblOkresPredykacji = new JLabel("Okres predykacji:");
        lblOkresPredykacji.setBounds(321, 209, 170, 16);
        panel.add(lblOkresPredykacji);

        formatter.setMinimum(1);
        formatter.setMaximum(Integer.MAX_VALUE);
        textField_2 = new JFormattedTextField(formatter);
        textField_2.setToolTipText(">0");
        textField_2.setBounds(331, 226, 160, 28);
        panel.add(textField_2);
        textField_2.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("Szereg czasowy:");
        lblNewLabel_2.setBounds(28, 53, 134, 16);
        panel.add(lblNewLabel_2);

        textField_3 = new JTextField();
        textField_3.setBounds(160, 47, 134, 28);
        panel.add(textField_3);
        textField_3.setColumns(10);

        JLabel lblOknoCzasowe = new JLabel("Okno czasowe:");
        lblOknoCzasowe.setBounds(28, 25, 134, 16);
        panel.add(lblOknoCzasowe);

        textField_4 = new JTextField();
        textField_4.setBounds(160, 19, 134, 28);
        panel.add(textField_4);
        textField_4.setColumns(10);

        JLabel lblProcentOsobnikwPozostwionych = new JLabel("Procent osobnikow pozostwionych po:");
        lblProcentOsobnikwPozostwionych.setBounds(528, 34, 298, 16);
        panel.add(lblProcentOsobnikwPozostwionych);

        JLabel lblProcentPoSelekcji = new JLabel("Selekcji");
        lblProcentPoSelekcji.setLabelFor(lblProcentPoSelekcji);
        lblProcentPoSelekcji.setBounds(540, 71, 80, 16);
        panel.add(lblProcentPoSelekcji);

        JLabel lblProcentPoKrzyzowaniu = new JLabel("Krzy\u017Cowaniu");
        lblProcentPoKrzyzowaniu.setLabelFor(lblProcentPoKrzyzowaniu);
        lblProcentPoKrzyzowaniu.setBounds(540, 101, 80, 16);
        panel.add(lblProcentPoKrzyzowaniu);

        JLabel lblProcentPoMutacji = new JLabel("Mutacji");
        lblProcentPoMutacji.setLabelFor(lblProcentPoMutacji);
        lblProcentPoMutacji.setBounds(540, 131, 80, 16);
        panel.add(lblProcentPoMutacji);

        // Listener dla kolejnych sliderow
        listener = new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                JSlider source = (JSlider) event.getSource();
                if (!source.getValueIsAdjusting()) {
                    int value = (int) source.getValue();
                    System.out.println(value);
                }
            }
        };

        JSlider sliderSelekcji = new JSlider(0, 100, 0);
        sliderSelekcji.setBounds(611, 61, 190, 29);
        sliderSelekcji.addChangeListener(listener);
        panel.add(sliderSelekcji);

        JSlider sliderKrzyzowania = new JSlider(0, 100, 0);
        sliderKrzyzowania.setBounds(611, 91, 190, 29);
        sliderKrzyzowania.addChangeListener(listener);
        panel.add(sliderKrzyzowania);

        JSlider sliderMutacji = new JSlider(0, 100, 0);
        sliderMutacji.setBounds(611, 121, 190, 29);
        sliderMutacji.addChangeListener(listener);
        panel.add(sliderMutacji);

		//-------------------DataTable-------------------
        dataTablePanel = new JPanel();
        tabbedPane.addTab("Data table", null, dataTablePanel, null);

        String[] columnNames = {"Date", "Value"};
        //Object[][] data = {{"", new Double(0)}};
        dataTablePanel.setLayout(null);
        dataTable = new JTable(new DefaultTableModel(columnNames, 1));

        tableScrollPane = new JScrollPane(dataTable);
        tableScrollPane.setBounds(198, 5, 452, 427);
        dataTable.setFillsViewportHeight(true);

        dataTablePanel.add(tableScrollPane);

        JButton btnAddDataRow = new JButton("Add data");
        btnAddDataRow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
                model.addRow(new Object[]{"", ""});
            }
        });
        btnAddDataRow.setBounds(10, 8, 113, 23);
        dataTablePanel.add(btnAddDataRow);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO saving current state
                try {
                    if (currentTimeSeries != null) {
                        String df = dateFormatTextField.getText();
                        SwingTableDataAcquisitor tableAcq = new SwingTableDataAcquisitor(dataTable, df);
                        int currentIndex = timeSeries.indexOf(currentTimeSeries);
                        timeSeries.remove(currentIndex);
                        currentTimeSeries = tableAcq.readData_TimeSeries((String) currentTimeSeries.getKey());
                        timeSeries.add(currentIndex, currentTimeSeries);
                        dataComboBox.firePopupMenuWillBecomeVisible();
                        dataComboBox.setSelectedIndex(timeSeries.indexOf(currentTimeSeries));
                    } else {
                        String df = dateFormatTextField.getText();
                        String name = JOptionPane.showInputDialog(dataTablePanel, "Choose name for data");
                        if (name != null) {
                            SwingTableDataAcquisitor tableAcq = new SwingTableDataAcquisitor(dataTable, df);
                            timeSeries.add(tableAcq.readData_TimeSeries(name));
                            currentTimeSeries = timeSeries.getLast();
                            dataComboBox.firePopupMenuWillBecomeVisible();
                            dataComboBox.setSelectedIndex(timeSeries.indexOf(currentTimeSeries));
                        }
                    }
                } catch (Exception exc) {
                    exc.printStackTrace();
                    JOptionPane.showMessageDialog(dataTablePanel, "Table data parse error", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnSave.setBounds(10, 76, 113, 23);
        dataTablePanel.add(btnSave);

        JButton btnDeleteDataRow = new JButton("Delete data");
        btnDeleteDataRow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
                for (@SuppressWarnings("unused") int i : dataTable.getSelectedRows()) {
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
                try {
                    if (!timeSeries.isEmpty()) {
                        currentTimeSeries = timeSeries.get(dataComboBox.getSelectedIndex());
                        SwingTableDataAcquisitor.updateJTable(dataTable, currentTimeSeries, dateFormatTextField.getText());
                    }
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }

            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                dataComboBox.removeAllItems();
                if (!timeSeries.isEmpty()) {
                    for (int i = 0; i < timeSeries.size(); i++) {
                        dataComboBox.addItem((String) timeSeries.get(i).getKey());
                    }
                }
            }
        });
        dataComboBox.setBounds(660, 9, 179, 20);
        dataTablePanel.add(dataComboBox);

        JButton btnSaveAs = new JButton("Save as...");
        btnSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String df = dateFormatTextField.getText();
                    String name = JOptionPane.showInputDialog(dataTablePanel, "Choose name for data");
                    if (name != null) {
                        SwingTableDataAcquisitor tableAcq = new SwingTableDataAcquisitor(dataTable, df);
                        timeSeries.add(tableAcq.readData_TimeSeries(name));
                        currentTimeSeries = timeSeries.getLast();
                        dataComboBox.firePopupMenuWillBecomeVisible();
                        dataComboBox.setSelectedIndex(timeSeries.indexOf(currentTimeSeries));
                    }
                } catch (Exception exc) {
                    exc.printStackTrace();
                    JOptionPane.showMessageDialog(dataTablePanel, "Table data parse error", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnSaveAs.setBounds(10, 110, 113, 23);
        dataTablePanel.add(btnSaveAs);

        JButton btnImport = new JButton("Import");
        btnImport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser importFileChooser = new JFileChooser();
                int returnVal = importFileChooser.showOpenDialog(main.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file = importFileChooser.getSelectedFile();
                        CSVDataAcquisitor csvDataAcquisitor = new CSVDataAcquisitor(file.getAbsolutePath(), Integer.parseInt(timeColumnTextField.getText()), Integer.parseInt(valueColumnTextField.getText()), dateFormatTextField.getText()); //TODO wybranie column
                        timeSeries.add(csvDataAcquisitor.readData_TimeSeries());
                        currentTimeSeries = timeSeries.getLast();
                        dataComboBox.firePopupMenuWillBecomeVisible();
                        dataComboBox.setSelectedIndex(timeSeries.indexOf(currentTimeSeries));
                        SwingTableDataAcquisitor.updateJTable(dataTable, currentTimeSeries, dateFormatTextField.getText());
                    } catch (Exception exc) {
                        exc.printStackTrace();
                        JOptionPane.showMessageDialog(dataTablePanel, "File parsing error", "Error", JOptionPane.ERROR_MESSAGE);
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
        timeSeriesChart.createChartPanel(new LinkedList<TimeSeries>(), 0);

        fitnessChart = new FitnessChart();
        tabbedPane.addTab("Fitness Chart", null, fitnessChart, null);

        // ============ STATYSTYKI =================
        JPanel statisticsPanel = new JPanel();
        tabbedPane.addTab("Statistics", null, statisticsPanel, null);
        statisticsPanel.setLayout(null);

        //TODO: trzeba wprowadzić zmienne z szeregami do porównania
        Statistics stat = new Statistics(timeSeries1, timeSeries2);
        double[] results = stat.findBestCoefficient();
        JLabel correlationCoefficientLabel = new JLabel("Wsp\\u00f3\\u0142czynnik korelacji: ");
        JLabel correlationCoefficientValue = new JLabel(String.valueOf(results[0]));
        JLabel lowerLimitLabel = new JLabel("Dolna granica: ");
        JLabel lowerLimitValue = new JLabel(String.valueOf(results[1]));
        JLabel upperLimitLabel = new JLabel("G\\u00f3rna granica: ");
        JLabel upperLimitValue = new JLabel(String.valueOf(results[2]));
        JLabel mean1Label = new JLabel("\\u015arednia szeregu 1: ");
        JLabel mean1Value = new JLabel(String.valueOf(stat.getMeanOfSeries1()));
        JLabel mean2Label = new JLabel("\\u015arednia szeregu 2: ");
        JLabel mean2Value = new JLabel(String.valueOf(stat.getMeanOfSeries2()));
        JLabel var1Label = new JLabel("Wariancja szeregu 1: ");
        JLabel var1Value = new JLabel(String.valueOf(stat.getVarOfSeries1()));
        JLabel var2Label = new JLabel("Wariancja szeregu 2: ");
        JLabel var2Value = new JLabel(String.valueOf(stat.getVarOfSeries2()));

        statisticsPanel.add(correlationCoefficientLabel);
        statisticsPanel.add(correlationCoefficientValue);
        statisticsPanel.add(lowerLimitLabel);
        statisticsPanel.add(lowerLimitValue);
        statisticsPanel.add(upperLimitLabel);
        statisticsPanel.add(upperLimitValue);
        statisticsPanel.add(mean1Label);
        statisticsPanel.add(mean1Value);
        statisticsPanel.add(mean2Label);
        statisticsPanel.add(mean2Value);
        statisticsPanel.add(var1Label);
        statisticsPanel.add(var1Value);
        statisticsPanel.add(var2Label);
        statisticsPanel.add(var2Value);
        //================================
        
        
        
        JPanel logsPanel = new JPanel();
        tabbedPane.addTab("Logi", null, logsPanel, null);

    }
}
