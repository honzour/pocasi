package cz.honza.pocasi.matematika;

public class Gaus {
	
	 public static double[] solve(double[][] A, double[] b) {
	        int n = b.length;

	        // Vytvoříme rozšířenou matici (n x n+1)
	        double[][] augmentedMatrix = new double[n][n + 1];
	        for (int i = 0; i < n; i++) {
	            System.arraycopy(A[i], 0, augmentedMatrix[i], 0, n);
	            augmentedMatrix[i][n] = b[i];
	        }

	        // Eliminace
	        for (int pivot = 0; pivot < n; pivot++) {
	            // Najdi řádek s maximálním prvkem ve sloupci (pivoting)
	            int maxRow = pivot;
	            for (int i = pivot + 1; i < n; i++) {
	                if (Math.abs(augmentedMatrix[i][pivot]) > Math.abs(augmentedMatrix[maxRow][pivot])) {
	                    maxRow = i;
	                }
	            }

	            // Prohoď řádky
	            double[] temp = augmentedMatrix[pivot];
	            augmentedMatrix[pivot] = augmentedMatrix[maxRow];
	            augmentedMatrix[maxRow] = temp;

	            // Zkontroluj, zda není hlavní prvek nulový
	            if (Math.abs(augmentedMatrix[pivot][pivot]) < 1e-10) {
	                throw new RuntimeException("Soustava nemá jednoznačné řešení.");
	            }

	            // Vynuluj spodní část sloupce
	            for (int i = pivot + 1; i < n; i++) {
	                double factor = augmentedMatrix[i][pivot] / augmentedMatrix[pivot][pivot];
	                for (int j = pivot; j <= n; j++) {
	                    augmentedMatrix[i][j] -= factor * augmentedMatrix[pivot][j];
	                }
	            }
	        }

	        // Zpětná substituce
	        double[] x = new double[n];
	        for (int i = n - 1; i >= 0; i--) {
	            double sum = augmentedMatrix[i][n];
	            for (int j = i + 1; j < n; j++) {
	                sum -= augmentedMatrix[i][j] * x[j];
	            }
	            x[i] = sum / augmentedMatrix[i][i];
	        }

	        return x;
	    }

}
