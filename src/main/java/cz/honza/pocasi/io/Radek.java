package cz.honza.pocasi.io;

import java.time.LocalDate;

public class Radek {
    public LocalDate datum;
    public double teplota;
    
	public Radek(LocalDate datum, double teplota) {
		super();
		this.datum = datum;
		this.teplota = teplota;
	}

	public Radek(int rok, int mesic, int den, double teplota) {
		datum = LocalDate.of(rok, mesic, den);
		this.teplota = teplota;
	}
	
	public Radek copy() {
		return new Radek(datum, teplota);
	}
}
