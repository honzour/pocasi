package cz.honza.pocasi.gui;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

import cz.honza.pocasi.matematika.Funkce;

public class PanelSGrafem extends JPanel {

	private static final long serialVersionUID = -124023812866506230L;
	
	protected double fromX;
	protected double toX;
	protected double fromY;
	protected double toY;
	
	public PanelSGrafem(double fromX, double toX, double fromY, double toY) {
		super();
		this.fromX = fromX;
		this.toX = toX;
		this.fromY = fromY;
		this.toY = toY;
	}
	
	protected int scaleToScreanY(double y, int windowHeight) {
		return windowHeight - (int) Math.round((y - fromY) * windowHeight / (toY - fromY));
	}
	
	protected int scaleToScreanX(double x, int windowWidth) {
		return (int) Math.round((x - fromX) * windowWidth / (toX - fromX)); 
	}
	
	protected void kresliJednuFunkci(Graphics g, Funkce f) {
		final Rectangle r = g.getClipBounds();
		int oldj = 0;
        
    	for (int i = 0; i < r.width; i++) {
    		double x = fromX + (toX - fromX) * i / r.width;
    		double y = f.f(x);
    		int j = scaleToScreanY(y, r.height);
    		if (i > 0) {
    			g.drawLine(i - 1, oldj, i, j);
    		}
    		oldj = j;
    	}
	}
	
	protected void kolecko(double x, double y, Graphics g, Rectangle r, String popis) {
		int i = scaleToScreanX(x, r.width);
		int j = scaleToScreanY(y, r.height);
		
		g.drawOval(i - 5, j - 5, 10, 10);
		if (popis != null) {
			g.drawChars(popis.toCharArray(), 0, popis.length(), i, j - 6);
		}
	}
}
