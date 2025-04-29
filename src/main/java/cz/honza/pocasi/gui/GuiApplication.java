package cz.honza.pocasi.gui;

import java.util.List;

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
	   new FrameFunkce(vysledky, historickaData);
   }
}
