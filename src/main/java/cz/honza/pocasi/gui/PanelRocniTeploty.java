package cz.honza.pocasi.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.time.LocalDate;
import java.util.List;

import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.kalendar.Utils;
import cz.honza.pocasi.matematika.Polynom;
import cz.honza.pocasi.metoda.ObecnaMetodaDataUtils;

public class PanelRocniTeploty extends PanelSGrafem {

	private static final long serialVersionUID = -7663335420184848126L;
	private Polynom prubehRoku;
	
	private List<Radek> historickaData;
	
	public PanelRocniTeploty(List<Radek> historickaData) {
		super(0, 366, -15, 40);
		this.historickaData = historickaData;
		prubehRoku = ObecnaMetodaDataUtils.polynomRoku(historickaData);
		setBackground(Color.WHITE);
		
	}

	
	protected void kresliData(Graphics g) {
		final Rectangle r = g.getClipBounds();
		
		for (Radek radek : historickaData) {
			double denZadani = Utils.dayIndexInYear(radek.datum);
			kolecko(denZadani, radek.teplota, g, r, null);
		}
	}
	
	protected void kresliCaryTeplot(Graphics g) {
		final Rectangle r = g.getClipBounds();
		g.setColor(Color.BLACK);
		for (double y = -10; y < 50; y += 5) {
			int j = scaleToScreanY(y, r.height);
			g.drawLine(0, j, r.width - 1, j);
			final String text = String.valueOf(y) + "°C";
			g.drawChars(text.toCharArray(), 0, text.length(), 10, j - 10);
		}
	}
	
	protected String mesic(int m) {
		switch (m) {
		case 1: return "Leden";
		case 2: return "Únor";
		case 3: return "Březen";
		case 4: return "Duben";
		case 5: return "Květen";
		case 6: return "Červen";
		case 7: return "Červenec";
		case 8: return "Srpen";
		case 9: return "Září";
		case 10: return "Říjen";
		case 11: return "Listopad";
		case 12: return "Prosinec";
		default: return null;
		}
	}
	
	protected void kresliCaryMesicu(Graphics g) {
		final Rectangle r = g.getClipBounds();
		g.setColor(Color.BLACK);
		for (int mesic = 1; mesic < 13; mesic++) {
			double x = LocalDate.of(2025, mesic, 1).getDayOfYear();
			
			int i = scaleToScreanX(x, r.width);
			g.drawLine(i, 0, i, r.height - 1);
			final String text = mesic(mesic);
			g.drawChars(text.toCharArray(), 0, text.length(), i + 5, 15);
		}
	}
	
	protected void kresliKrivku(Graphics g) {
		final Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));
        g.setColor(Color.RED);
        kresliJednuFunkci(g, prubehRoku);
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        kresliData(g);
        kresliCaryTeplot(g);
        kresliCaryMesicu(g);
        kresliKrivku(g);
	}
	
}
