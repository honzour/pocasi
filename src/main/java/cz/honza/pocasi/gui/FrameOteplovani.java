package cz.honza.pocasi.gui;

import java.util.List;

import javax.swing.JFrame;

import cz.honza.pocasi.io.Radek;

public class FrameOteplovani extends JFrame {

	private static final long serialVersionUID = -545629501905849295L;
	
	public FrameOteplovani(List<Radek> historickaData) {
		super("Lokální oteplování maxim na Ruzyni, regrese polynomem stupně 20");
		
		add(new PanelOteplovani(historickaData));
		setSize(200, 200);
	    setVisible(true);
	}

}
