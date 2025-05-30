package cz.honza.pocasi.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import cz.honza.pocasi.matematika.Funkce;
import cz.honza.pocasi.metoda.Vysledek;

public class PanelFunkce extends PanelSGrafem {

	private static final long serialVersionUID = 7562798847917999553L;
		
	private List<Vysledek> vysledky;
	private List<Double> historickaData;
	
	private List<Color> barvy = Arrays.asList(Color.WHITE, Color.GRAY, Color.GREEN, Color.ORANGE);	
	
	public PanelFunkce(double fromX, double toX, double fromY, double toY, List<Vysledek> vysledky, List<Double> historickaData) {
		super(fromX, toX, fromY, toY);
		
		this.vysledky = vysledky;
		this.historickaData = historickaData;
		
		setBackground(Color.BLACK);
		
		int barva = 0;
		add(Box.createRigidArea(new Dimension(0, 50)));
		for (Vysledek vysledek : vysledky) {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JLabel label = new JLabel(" " + vysledek.jmeno + ", kurs menší: " + Math.round(vysledek.kurzLt * 1000) / 1000.0 + ", kurs větší rovno: " + Math.round(vysledek.kurzGe * 1000) / 1000.0);
			label.setForeground(barvy.get(barva % barvy.size()));
			label.setAlignmentX(Component.LEFT_ALIGNMENT);
			add(label);
			add(Box.createRigidArea(new Dimension(0, 5)));
			barva++;
		}
	}

	protected void vykresliBody(Graphics g) {
		final Rectangle r = g.getClipBounds();
		g.setColor(Color.WHITE);
		Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));
		for (Double teplota : historickaData) {
			int x = scaleToScreanX(teplota, r.width);
			g.drawOval(x - 5, r.height - r.height / 50, 10, 10);
		}
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        vykresliMeritko(g);
        vykresliCaru(g);
        vykresliFunkce(g);
        vykresliBody(g);
    }
	
	private void vykresliCaru(Graphics g) {
		g.setColor(Color.RED);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));

		final Rectangle r = g.getClipBounds();
		final double teplota = vysledky.get(0).co.teplota;
		int x = scaleToScreanX(teplota, r.width);
		
		g.drawLine(x, 0, x, r.height - 1);
		final String s = String.valueOf(teplota) + "°C";
		g.drawChars(s.toCharArray(), 0, s.length(), x + r.height / 80, r.height / 18);
	}
	
	private void vykresliMeritko(Graphics g) {
		final Rectangle r = g.getClipBounds();
		g.setColor(Color.LIGHT_GRAY);
		int from = (int) fromX + 1;
        int to = (int) toX;
        for (int i = from; i <= to; i++) {
        	int x = scaleToScreanX((double)i, r.width);
       		g.drawLine(x, 0, x, r.height / 30);
       		final String s = String.valueOf(i) + "°C";
       		g.drawChars(s.toCharArray(), 0, s.length(), x, r.height / 25);
        }
	}
	
	private void vykresliFunkce(Graphics g) {
        int barva = 0;
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));
        
        for (Vysledek v : vysledky) {
        	Funkce hustota = v.funkceHustoty;
        	if (hustota == null) {
        		continue;
        	}
        	g.setColor(barvy.get(barva));
        	kresliJednuFunkci(g, hustota);
        	barva = (barva + 1) % barvy.size();
        }
	}

}
