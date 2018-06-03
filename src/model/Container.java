package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Container {
	private final double maxUnits;
	private final double restoreRatePerSecond;
	private double units;
	
	public Container(int maxUnits, double restoreRatePerSecond) {
		super();
		this.maxUnits = maxUnits;
		this.units = maxUnits;
		this.restoreRatePerSecond = restoreRatePerSecond;
		
		double restoreRate = 1-Math.pow(1-restoreRatePerSecond, 0.1);
		System.out.println("[Container] Restore rate per second is "+restoreRatePerSecond);
		System.out.println("[Container] Restore rate per 0.1 seconds is "+restoreRate);
		
		// Set up restore rate
		Timer timer =  new Timer(100, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				restore(restoreRate);
			}
		});
		timer.start();
	}
	
	public int getMaxUnits() {
		return (int) maxUnits;
	}
	
	public int getUnits() {
		return (int) units;
	}
	
	public void subtract(int u) {
		units -= u;
		if (units<0) units = 0;
	}

	// Definition of restore:
	// For example, restoring 10% the missing unit
	public void restore(double rate) {
		double missingUnits = maxUnits - units;
		double ammount = missingUnits*rate;
		units = units + ammount;
		if (units>maxUnits) units = maxUnits;
	}
}
