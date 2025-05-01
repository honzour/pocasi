package cz.honza.pocasi.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
