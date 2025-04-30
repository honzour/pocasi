package cz.honza.pocasi.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.matematika.Bod2D;
import cz.honza.pocasi.matematika.Funkce;
import cz.honza.pocasi.matematika.PolynomialRegressionNoLib;


public class PanelOteplovani extends JPanel {

	private static final long serialVersionUID = 6655965056747557021L;
	final List<RokTeplota> teploty;
	final Funkce polynomMaxim;
	
	private double fromX = 1960;
	private double toX = 2026;
	private double fromY = 9;
	private double toY = 17;
	
	private class RokTeplota {
		
		public RokTeplota(int rok, double teplota) {
			super();
			this.rok = rok;
			this.teplota = teplota;
		}
		public int rok;
		public double teplota;
	}
	
	public PanelOteplovani(List<Radek> historickaData) {
	
		
		final Map<Integer, List<Radek>> roky = historickaData.stream().collect(Collectors.groupingBy(r -> r.datum.getYear()));
		teploty = roky.keySet().stream().sorted().map(
				rok ->
					new RokTeplota(
						rok,
						roky.get(rok).stream().mapToDouble(radek -> radek.teplota).average().orElse(0)
					)
				).collect(Collectors.toList());
		
		polynomMaxim = PolynomialRegressionNoLib.fitPolynomial(
				teploty.stream().map(rt -> new Bod2D(rt.rok, rt.teplota)).collect(Collectors.toList()),
				20
			);

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
		
		for (RokTeplota rt: teploty) {
			int y = r.height - (int) Math.round((rt.teplota - fromY) * r.height / (toY - fromY));
			int x = (int) Math.round((rt.rok - fromX) * r.width / (toX - fromX));
			
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
        kresliJednuFunkci(g, polynomMaxim);
	}
}
