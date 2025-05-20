package cz.honza.pocasi.gui;

import java.util.List;

import javax.swing.JFrame;

import cz.honza.pocasi.io.Radek;

public class FrameLinearniOteplovani extends JFrame {
	private static final long serialVersionUID = -545629501905849275L;
	
	public FrameLinearniOteplovani(List<Radek> historickaData) {
		super("Lokální oteplování ročních průměrů denních maxim na Ruzyni, lineární regrese");
		
		add(new PanelOteplovani(historickaData));
		setSize(200, 200);
	    setVisible(true);
	}

}
