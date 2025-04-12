package cz.honza.pocasi.metoda;


import java.util.List;

import cz.honza.pocasi.io.Radek;

public class Matfyzacka extends ObecnaMetoda {

	@Override
	public Vysledek spocitej(Radek zadani, List<Radek> historickaData) {
		return new Vysledek("Matfyzácká", zadani, 2.0, 2.0, "TODO");
	}

}
