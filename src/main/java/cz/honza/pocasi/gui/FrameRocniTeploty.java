package cz.honza.pocasi.gui;

import java.util.List;

import javax.swing.JFrame;

import cz.honza.pocasi.io.Radek;

public class FrameRocniTeploty extends JFrame {

	private static final long serialVersionUID = -3392577614845667972L;
	
	public FrameRocniTeploty(List<Radek> historickaData) {
		super("Průběh roční maxim na Ruzyni, regrese polynomem stupně 36");
		
		add(new PanelRocniTeploty(historickaData));
		setSize(200, 200);
	    setVisible(true);
	}
}
