package cz.honza.pocasi.gui;

import java.util.List;

import javax.swing.SwingUtilities;

import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.metoda.Vysledek;

public class GuiApplication {
	public static void start(List<Vysledek> vysledky, List<Double> historickaDataUpravena, List<Radek> historickaData) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI(vysledky, historickaDataUpravena, historickaData);
			}
		});
	}
	
   private static void createAndShowGUI(List<Vysledek> vysledky, List<Double> historickaDataUpravena, List<Radek> historickaData) {
	   new FrameFunkce(vysledky, historickaDataUpravena);
	   new FrameRocniTeploty(historickaData);
	   new FrameOteplovani(historickaData);
   }
}
