import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.DecimalFormat;

/**
 * Find the maximum product of a certain number of consecutive numbers in a 2D
 * grid
 *
 * @author Jordan
 *
 */
public class Euler11 {

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		BufferedReader br = null;
		LineNumberReader lnr = null;
		String str = null;
		int w, h, row = 0, col = -1;
		String path = System.getProperty("user.dir");
		path += "/src/numberGrid.txt";
		Double[][] nums = null;
		try {
			// Read the file
			FileInputStream fs = new FileInputStream(path);
			br = new BufferedReader(new InputStreamReader(fs));

			// Get the number of lines (height)
			lnr = new LineNumberReader(new FileReader(path));
			lnr.skip(Long.MAX_VALUE);
			h = lnr.getLineNumber() + 1;

			// Get the width
			br.mark(1);
			w = br.readLine().split(" ").length;
			br.reset();

			// Setup
			nums = new Double[w][h];

			// Convert into numbers
			while ((str = br.readLine()) != null) {
				col = 0;
				for (String numStr : str.split(" ")) {
					nums[row][col++] = Double.valueOf(numStr);
				}
				row++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Setup the scanners
		int size = 4;
		NumScannerFactory fact = new NumScannerFactory();
		fact.setGrid(nums);
		fact.setScannerSize(size);

		NumScanner sc = fact.getHorizontalScanner();
		NumScanner sc1 = fact.getVerticalScanner();
		NumScanner sc2 = fact.getLeftDiagScanner();
		NumScanner sc3 = fact.getRightDiagScanner();

		// Threading!!!
		sc.start();
		sc1.start();
		sc2.start();
		sc3.start();

		// Wait for them to finish...
		sc.join();
		sc1.join();
		sc2.join();
		sc3.join();

		// Find the answer
		DecimalFormat fmt = new DecimalFormat("#.###");
		double m = max(sc.getMaxProduct(), sc1.getMaxProduct(),
				sc2.getMaxProduct(), sc3.getMaxProduct());

		System.out.printf("Max Product of size %d: %s.\n%s", size,
				fmt.format(m), sc3.getInfo());
	}

	private static double max(double... params) {
		double max = Double.MIN_VALUE;
		if (params.length == 0)
			return -1;
		else if (params.length == 1)
			return params[0];
		else {
			for (double e : params) {
				if (e > max)
					max = e;
			}
		}
		return max;
	}

}
