package cz.honza.pocasi.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataReader {
	public static List<Radek> read(String filename) {
		
		try {
		
			List<Radek> data = new ArrayList<Radek>();
			final File file = new File(filename);
			try (final BufferedReader br = new BufferedReader(new FileReader(file))) {
				String s;
				while ((s = br.readLine()) != null) {
					if (s.length() == 0 || s.charAt(0) == '#') {
						continue;
					}
					final String[] fields = s.split(";");
					if (fields.length < 4) {
						throw new IOException("Field " + s);
					}
					Radek radek = new Radek(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), Integer.parseInt(fields[2]), Double.parseDouble(fields[3]));
					data.add(radek);
				}
			}
			return data;
		} catch (IOException e) {
			return null;
		}
	}

}
