package cz.honza.pocasi.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JPanel;

import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.kalendar.Utils;
import cz.honza.pocasi.matematika.Funkce;
import cz.honza.pocasi.matematika.Polynom;
import cz.honza.pocasi.metoda.ObecnaMetodaDataUtils;

public class PanelRocniTeploty extends JPanel {

	private static final long serialVersionUID = -7663335420184848126L;
	private Polynom prubehRoku;
	private double fromX = 0;
	private double toX = 366;
	private double fromY = -15;
	private double toY = 40;
	
	private List<Radek> historickaData;
	
	public PanelRocniTeploty(List<Radek> historickaData) {
		super();
		this.historickaData = historickaData;
		prubehRoku = ObecnaMetodaDataUtils.polynomRoku(historickaData);
		setBackground(Color.WHITE);
		
	}
	
	protected void kresliJednuFunkci(Graphics g, Funkce hustota) {
		final Rectangle r = g.getClipBounds();
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
	}
	
	protected void kresliData(Graphics g) {
		final Rectangle r = g.getClipBounds();
		
		for (Radek radek : historickaData) {
			int y = r.height - (int) Math.round((radek.teplota - fromY) * r.height / (toY - fromY));
			double denZadani = Utils.dayIndexInYear(radek.datum);
			int x = (int) Math.round(denZadani * r.width / toX);
			
			g.drawOval(x - 5, y - 5, 10, 10);
		}
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        kresliData(g);
        final Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));
        g.setColor(Color.RED);
        kresliJednuFunkci(g, prubehRoku);
	}
	
}
