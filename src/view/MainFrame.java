package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

import model.Container;

public class MainFrame extends JFrame implements ActionListener {
	JProgressBar containerBar;
	JTextField subtractField, maxUnitsField, rateField;
	JButton subtractButton, setButton;
	JLabel containerInformation;
	
	Container container = new Container(1,0.1);

	public MainFrame() {
		setTitle("Equilibrium simulator");
		
		setupComponents();
		setupLayout();
		resetContainer();
		
		setSize(800, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void setupComponents() {
		maxUnitsField = new JTextField();
		maxUnitsField.setText("10000");
		rateField = new JTextField();
		rateField.setText("0.1");
		
		containerBar = new JProgressBar();
		
		// Periodically update the container bar from the model
		Timer timer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				containerBar.setValue(container.getUnits());
				
				containerInformation.setText(container.getUnits()+"/"+container.getMaxUnits());
			}
		});
		timer.start();
		
		subtractField = new JTextField();
		
		subtractButton = new JButton("Subtract");
		subtractButton.addActionListener(this);
		
		containerInformation = new JLabel("");
		
		setButton = new JButton("Set");
		setButton.addActionListener(this);
	}
	
	private void setupLayout() {
		setLayout(new GridLayout(2,1));
		JPanel top = new JPanel();
		top.setBorder(new TitledBorder("Settings"));
		top.setLayout(new GridLayout(1, 5));
		top.add(new JLabel("Units: "));
		top.add(maxUnitsField);
		top.add(new JLabel("Restore rate per seconds: "));
		top.add(rateField);
		top.add(setButton);
		add(top);
		
		JPanel bottom = new JPanel();
		bottom.setBorder(new TitledBorder("Simulation"));
		bottom.setLayout(new BorderLayout());
		bottom.add(containerInformation, BorderLayout.NORTH);
		bottom.add(containerBar, BorderLayout.SOUTH);
		bottom.add(new JLabel("Subtract ammount: "), BorderLayout.WEST);
		bottom.add(subtractField, BorderLayout.CENTER);
		bottom.add(subtractButton, BorderLayout.EAST);
		add(bottom);
	}
	
	private void resetContainer() {
		container = new Container(Integer.valueOf(maxUnitsField.getText())
				, Double.valueOf(rateField.getText()));
		containerBar.setMaximum(container.getMaxUnits());
		containerBar.setMinimum(0);
		containerBar.setValue(container.getUnits());
		subtractField.setText(String.valueOf(container.getMaxUnits()/2));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==subtractButton) {
			int u = Integer.valueOf(subtractField.getText());
			container.subtract(u);
		}
		else if (e.getSource()==setButton) {
			resetContainer();
		}
	}
	
	
}
