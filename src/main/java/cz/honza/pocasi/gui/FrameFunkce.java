package cz.honza.pocasi.gui;

import java.awt.HeadlessException;
import java.util.List;

import javax.swing.JFrame;

import cz.honza.pocasi.metoda.Vysledek;

public class FrameFunkce extends JFrame {

	private static final long serialVersionUID = -1609643711758746388L;
		
	public FrameFunkce(List<Vysledek> vysledky, List<Double> historickaData) throws HeadlessException {
		super("P(na Ruzyni bude maximum " + vysledky.get(0).co.datum.toString() +" < " + vysledky.get(0).co.teplota + " °C)");
			      
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    final double epsilon = 1;
	    final double min = historickaData.stream().min(Double::compareTo).get() - epsilon;
	    final double max = historickaData.stream().max(Double::compareTo).get() + epsilon;
	      
	    add(new PanelFunkce(min, max, 0, 0.15, vysledky, historickaData));
	    setExtendedState(JFrame.MAXIMIZED_BOTH);
	    setVisible(true);
	}
}
