package cz.honza.pocasi.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.matematika.Bod2D;
import cz.honza.pocasi.matematika.Funkce;
import cz.honza.pocasi.matematika.PolynomialRegressionNoLib;


public class PanelOteplovani extends PanelSGrafem {

	private static final long serialVersionUID = 6655965056747557021L;
	final List<RokTeplota> teploty;
	final Funkce polynomMaxim;
	
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
		super(1960, 2026, 9, 17);
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

	
	protected void kresliData(Graphics g) {
		final Rectangle r = g.getClipBounds();
		
		for (RokTeplota rt: teploty) {
			kolecko(rt.rok, rt.teplota, g, r, String.valueOf(rt.rok).substring(2) + " " + String.valueOf(Math.round(rt.teplota * 10) / 10.0));
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
