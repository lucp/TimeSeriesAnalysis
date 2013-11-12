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
	private JTextField textField_3;
	private JTextField textField_4;

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
		parametr1.setBounds(28, 81, 134, 16);
		panel.add(parametr1);
		
		JLabel parametr2 = new JLabel("Liczba iteracji:");
		parametr2.setBounds(28, 109, 134, 16);
		panel.add(parametr2);
		
		JLabel parametr3 = new JLabel("Prawdopodobie\u0144stwo mutacji:");
		parametr3.setBounds(28, 145, 266, 16);
		panel.add(parametr3);
		
		textField = new JTextField();
		textField.setBounds(160, 75, 134, 28);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(160, 103, 134, 28);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JSlider slider = new JSlider();
		slider.setBounds(28, 165, 190, 29);
		panel.add(slider);
		
		JLabel lblNewLabel = new JLabel("Prawdopodobie\u0144stwo krzy\u017Cowania:");
		lblNewLabel.setBounds(28, 205, 266, 16);
		panel.add(lblNewLabel);
		
		JSlider slider_1 = new JSlider();
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
		
		textField_2 = new JTextField();
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
		
		JLabel lblProcentOsobnikwPozostwionych = new JLabel("Procent osobnik—w pozostwionych po:");
		lblProcentOsobnikwPozostwionych.setBounds(528, 34, 298, 16);
		panel.add(lblProcentOsobnikwPozostwionych);
		
		JLabel lblProcentPoSelekcji = new JLabel("Selekcji");
		lblProcentPoSelekcji.setLabelFor(lblProcentPoSelekcji);
		lblProcentPoSelekcji.setBounds(528, 71, 315, 16);
		panel.add(lblProcentPoSelekcji);
		
		JLabel lblProcentPoKrzyzowaniu = new JLabel("Krzyýowaniu");
		lblProcentPoKrzyzowaniu.setLabelFor(lblProcentPoKrzyzowaniu);
		lblProcentPoKrzyzowaniu.setBounds(528, 101, 315, 16);
		panel.add(lblProcentPoKrzyzowaniu);
		
		JLabel lblProcentPoMutacji= new JLabel("Mutacji");
		lblProcentPoMutacji.setLabelFor(lblProcentPoMutacji);
		lblProcentPoMutacji.setBounds(528, 131, 315, 16);
		panel.add(lblProcentPoMutacji);
		
		JSlider sliderSelekcji = new JSlider();
		sliderSelekcji.setBounds(611, 61, 190, 29);
		panel.add(sliderSelekcji);
		
		JSlider sliderKrzyzowania = new JSlider();
		sliderKrzyzowania.setBounds(611, 91, 190, 29);
		panel.add(sliderKrzyzowania);
		
		JSlider sliderMutacji = new JSlider();
		sliderMutacji.setBounds(611, 121, 190, 29);
		panel.add(sliderMutacji);
		
		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Chart", null, tabbedPane_2, null);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Logi", null, tabbedPane_1, null);
		
		
	}
}
