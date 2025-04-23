package cz.honza.pocasi.gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import cz.honza.pocasi.matematika.Funkce;

public class GuiApplication {
	public static void start(Funkce f) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI(f);
			}
		});
	}
	
   private static void createAndShowGUI(Funkce f) {
      JFrame frame = new JFrame("Počasí");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      
      frame.add(new PanelFunkce(0, 35, 0, 0.1, f));
      frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
      frame.setVisible(true);
   }
}
