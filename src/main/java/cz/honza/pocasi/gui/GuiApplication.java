package cz.honza.pocasi.gui;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import cz.honza.pocasi.matematika.Funkce;

public class GuiApplication {
	public static void start() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}
	
   private static void createAndShowGUI() {
      JFrame frame = new JFrame("Počasí");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.add(createPanel());
      frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
      frame.setVisible(true);
   }

   private static JPanel createPanel() {
	   return new JPanel()  {
           private static final long serialVersionUID = -3044246815373806976L;

		@Override
           protected void paintComponent(Graphics g) {
               super.paintComponent(g);
               
               Funkce f = new Funkce() {
				
				@Override
				public double f(double x) {
					
					return Math.exp(- x * x);
				}
			};
               
               Rectangle r = g.getClipBounds();
               double fromX = -3;
               double toX = 3;
               
               double fromY = 0;
               double toY = 1;
               
               int oldj = 0;
               
               for (int i = 0; i < r.width; i++) {
            	   double x = fromX + (toX - fromX) * i / r.width;
            	   double y = f.f(x);
            	   int j = (int)Math.round((r.height - 1) * (y - fromY) / (toY - fromY));
            	   if (i > 0) {
            		   g.drawLine(i - 1, r.height - oldj - 1, i, r.height - j - 1);
            	   }
            	   oldj = j;
               }
               
           }
       };
   }

}
