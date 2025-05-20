package cz.honza.pocasi.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.matematika.Funkce;
import cz.honza.pocasi.metoda.ObecnaMetodaDataUtils;
import cz.honza.pocasi.metoda.ObecnaMetodaDataUtils.RokTeplota;

public class PanelLinearniOteplovani extends PanelSGrafem {
	private static final long serialVersionUID = 6655965053747557021L;
	final List<RokTeplota> teploty;
	final Funkce polynomMaxim;
	
	public PanelLinearniOteplovani(List<Radek> historickaData) {
		super(2013, 2026, 11, 17);

		final List<RokTeplota> teploty = ObecnaMetodaDataUtils.spocitejRocniPrumery(historickaData);
		int max = teploty.stream().map(t -> t.rok).max(Comparator.naturalOrder()).get();
		this.teploty = teploty.stream().filter(t -> t.rok > max - 10).collect(Collectors.toList());
		polynomMaxim = ObecnaMetodaDataUtils.spocitejPolynomMaxim(this.teploty, 1); 
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
		for (int rok = 2010; rok < 2030; rok += 2) {
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
