package cz.honza.pocasi.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;
import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.matematika.Funkce;
import cz.honza.pocasi.metoda.ObecnaMetodaDataUtils;
import cz.honza.pocasi.metoda.ObecnaMetodaDataUtils.RokTeplota;


public class PanelOteplovani extends PanelSGrafem {

	private static final long serialVersionUID = 6655965056747557021L;
	final List<RokTeplota> teploty;
	final Funkce polynomMaxim;
	

	
	public PanelOteplovani(List<Radek> historickaData) {
		super(1960, 2026, 9, 17);

		teploty = ObecnaMetodaDataUtils.spocitejRocniPrumery(historickaData);
		polynomMaxim = ObecnaMetodaDataUtils.spocitejPolynomMaxim(teploty, 20); 
	}
	
	protected void kresliCaryTeplot(Graphics g) {
		final Rectangle r = g.getClipBounds();
		g.setColor(Color.BLACK);
		for (double y = 10; y < 17; y++) {
			int j = scaleToScreanY(y, r.height);
			g.drawLine(0, j, r.width - 1, j);
			final String text = String.valueOf(y) + "°C";
			g.drawChars(text.toCharArray(), 0, text.length(), 10, j - 10);
		}
	}
	
	protected void kresliCaryLet(Graphics g) {
		final Rectangle r = g.getClipBounds();
		g.setColor(Color.BLACK);
		for (int rok = 1970; rok < 2030; rok += 10) {
			int i = scaleToScreanX(rok, r.width);
			g.drawLine(i, 0, i, r.height - 1);
			final String text = String.valueOf(rok);
			g.drawChars(text.toCharArray(), 0, text.length(), i + 5, 15);
		}
	}

	
	protected void kresliData(Graphics g) {
		final Rectangle r = g.getClipBounds();
		
		for (RokTeplota rt: teploty) {
			kolecko(rt.rok, rt.teplota, g, r, String.valueOf(Math.round(rt.teplota * 100) / 100.0));
		}
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        kresliData(g);
        kresliCaryTeplot(g);
        kresliCaryLet(g);
        final Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));
        g.setColor(Color.RED);
        kresliJednuFunkci(g, polynomMaxim);
	}
}
