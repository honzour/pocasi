package cz.honza.pocasi.gui;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

import cz.honza.pocasi.matematika.Funkce;

public class PanelFunkce extends JPanel {

	private static final long serialVersionUID = 7562798847917999553L;
	
	private double fromX;
	private double toX;
	private double fromY;
	private double toY;
	private Funkce f;
		
	
	public PanelFunkce(double fromX, double toX, double fromY, double toY, Funkce f) {
		super();
		this.fromX = fromX;
		this.toX = toX;
		this.fromY = fromY;
		this.toY = toY;
		this.f = f;
	}


	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Rectangle r = g.getClipBounds();
        
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

}
