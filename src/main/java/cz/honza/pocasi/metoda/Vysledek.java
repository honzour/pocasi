package cz.honza.pocasi.metoda;

import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.matematika.Funkce;

public class Vysledek {
	public String jmeno;
	public Radek co;
	public double kurzLt;
	public double kurzGe;
	public String extraInfo;
	public Funkce funkceHustoty;
	
	
	public Vysledek(String jmeno, Radek co, double kurzLt, double kurzGe, String extraInfo, Funkce funkceHustoty) {
		super();
		this.jmeno = jmeno;
		this.co = co;
		this.kurzLt = kurzLt;
		this.kurzGe = kurzGe;
		this.extraInfo = extraInfo;
		this.funkceHustoty = funkceHustoty;
	}


	public String toString() {
		return "Metoda " + jmeno + " " + co.datum.getDayOfMonth() + "." + co.datum.getMonthValue() + "." + co.datum.getYear() + " teplota " + co.teplota + "\n" +
				"kurzLt " + kurzLt + "\n" +
				"kurzGe " + kurzGe +
				(extraInfo == null ? "" : "\n" + extraInfo) +
				"\n";
	}
}
