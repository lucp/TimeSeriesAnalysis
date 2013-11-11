// Created by üukasz Szarkowicz
// 2013


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JRadioButton;

public class main extends JFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 870, 450);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JButton Import = new JButton("Import");
		menuBar.add(Import);
		
		JButton btnCustomValues = new JButton("Custom values");
		menuBar.add(btnCustomValues);
		
		JButton btnReset = new JButton("Reset");
		menuBar.add(btnReset);
		
		JButton btnRun = new JButton("Run");
		menuBar.add(btnRun);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Parametry", null, panel, null);
		panel.setLayout(null);
		
		JLabel parametr1 = new JLabel("Rozmiar populacji:");
		parametr1.setBounds(31, 34, 134, 16);
		panel.add(parametr1);
		
		JLabel parametr2 = new JLabel("Liczba iteracji:");
		parametr2.setBounds(31, 62, 134, 16);
		panel.add(parametr2);
		
		JLabel parametr3 = new JLabel("Prawdopodobie\u0144stwo mutacji:");
		parametr3.setBounds(31, 98, 266, 16);
		panel.add(parametr3);
		
		textField = new JTextField();
		textField.setBounds(163, 28, 134, 28);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(163, 56, 134, 28);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JSlider slider = new JSlider();
		slider.setBounds(31, 118, 190, 29);
		panel.add(slider);
		
		JLabel lblNewLabel = new JLabel("Prawdopodobie\u0144stwo krzy\u017Cowania:");
		lblNewLabel.setBounds(31, 158, 266, 16);
		panel.add(lblNewLabel);
		
		JSlider slider_1 = new JSlider();
		slider_1.setBounds(31, 178, 190, 29);
		panel.add(slider_1);
		
		JLabel lblNewLabel_1 = new JLabel("Metody selekcji:");
		lblNewLabel_1.setBounds(423, 34, 170, 16);
		panel.add(lblNewLabel_1);
		
		JRadioButton rdbtnMetoda = new JRadioButton("metoda1");
		rdbtnMetoda.setBounds(501, 61, 141, 23);
		panel.add(rdbtnMetoda);
		
		JRadioButton rdbtnMetoda_1 = new JRadioButton("metoda2");
		rdbtnMetoda_1.setBounds(501, 91, 141, 23);
		panel.add(rdbtnMetoda_1);
		
		JRadioButton rdbtnMetoda_2 = new JRadioButton("metoda3");
		rdbtnMetoda_2.setBounds(501, 121, 141, 23);
		panel.add(rdbtnMetoda_2);
		
		JRadioButton rdbtnMetoda_3 = new JRadioButton("metoda4");
		rdbtnMetoda_3.setBounds(501, 151, 141, 23);
		panel.add(rdbtnMetoda_3);
		
		JLabel lblOkresPredykacji = new JLabel("Okres predykacji:");
		lblOkresPredykacji.setBounds(423, 209, 170, 16);
		panel.add(lblOkresPredykacji);
		
		textField_2 = new JTextField();
		textField_2.setBounds(433, 226, 160, 28);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Chart", null, tabbedPane_2, null);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Logi", null, tabbedPane_1, null);
		
		
	}
}
