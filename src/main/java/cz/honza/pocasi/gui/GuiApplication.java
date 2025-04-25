package cz.honza.pocasi.gui;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import cz.honza.pocasi.metoda.Vysledek;

public class GuiApplication {
	public static void start(List<Vysledek> vysledky, List<Double> historickaData) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI(vysledky, historickaData);
			}
		});
	}
	
   private static void createAndShowGUI(List<Vysledek> vysledky, List<Double> historickaData) {
	   
      JFrame frame = new JFrame("P(na Ruzyni bude maximum " + vysledky.get(0).co.datum.toString() +" < " + vysledky.get(0).co.teplota + " °C)");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      final double epsilon = 1;
      final double min = historickaData.stream().min(Double::compareTo).get() - epsilon;
      final double max = historickaData.stream().max(Double::compareTo).get() + epsilon;
      
      frame.add(new PanelFunkce(min, max, 0, 0.15, vysledky, historickaData));
      frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
      frame.setVisible(true);
   }
}
