package cz.honza.pocasi.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;

import cz.honza.pocasi.matematika.Funkce;

public class PanelFunkce extends JPanel {

	private static final long serialVersionUID = 7562798847917999553L;
	
	private double fromX;
	private double toX;
	private double fromY;
	private double toY;
	private List<Funkce> f;
	private List<Color> barvy = Arrays.asList(Color.BLACK, Color.BLUE, Color.GREEN, Color.RED);	
	
	public PanelFunkce(double fromX, double toX, double fromY, double toY, List<Funkce> f) {
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
        
        
        final Rectangle r = g.getClipBounds();
        int barva = 0;
        
        for (Funkce hustota : f) {
        	if (hustota == null) {
        		continue;
        	}
        	g.setColor(barvy.get(barva));
        	int oldj = 0;
        
        	for (int i = 0; i < r.width; i++) {
        		double x = fromX + (toX - fromX) * i / r.width;
        		double y = hustota.f(x);
        		int j = (int)Math.round((r.height - 1) * (y - fromY) / (toY - fromY));
        		if (i > 0) {
        			g.drawLine(i - 1, r.height - oldj - 1, i, r.height - j - 1);
        		}
        		oldj = j;
        	}
        	barva = (barva + 1) % barvy.size();
        }
        
    }

}
