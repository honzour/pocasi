package cz.honza.pocasi.gui;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import cz.honza.pocasi.matematika.Funkce;

public class GuiApplication {
	public static void start(List<Funkce> f, List<Double> historickaData) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI(f, historickaData);
			}
		});
	}
	
   private static void createAndShowGUI(List<Funkce> f, List<Double> historickaData) {
      JFrame frame = new JFrame("Počasí");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      final double epsilon = 1;
      final double min = historickaData.stream().min(Double::compareTo).get() - epsilon;
      final double max = historickaData.stream().max(Double::compareTo).get() + epsilon;
      
      frame.add(new PanelFunkce(min, max, 0, 0.12, f));
      frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
      frame.setVisible(true);
   }
}
