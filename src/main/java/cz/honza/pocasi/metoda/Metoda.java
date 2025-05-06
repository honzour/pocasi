package cz.honza.pocasi.metoda;

import java.util.List;

import cz.honza.pocasi.io.Radek;

public interface Metoda {
	Vysledek spocitej(Radek zadani, List<Radek> historickaData);
	double otestuj(List<Radek> historickaData, double kurz);
}
