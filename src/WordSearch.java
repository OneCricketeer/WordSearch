import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Hashtable;

public class WordSearch {
	private final Hashtable<String, Short> wordList = new Hashtable<String, Short>();
	// private BufferedReader br;
	private Character[][] grid;
	private final String f_grid, f_wordList;
	private int max_size, min_size;

	public WordSearch(String grid, String wordList) {
		f_grid = grid;
		f_wordList = wordList;
		min_size = Integer.MAX_VALUE;
		buildWordList();
		buildGrid();
	}

	private void buildWordList() {
		String str = null;
		String path = System.getProperty("user.dir");
		path += "/src/" + f_wordList;
		BufferedReader br = null;
		try {
			// Read the file
			FileInputStream fs = new FileInputStream(path);
			br = new BufferedReader(new InputStreamReader(fs));

			// Read each word
			while ((str = br.readLine()) != null) {
				// Put each word into a table
				wordList.put(str.toLowerCase(), (short) 0);
				// Set the max&min size of the scanner
				max_size = str.length() > max_size ? str.length() : max_size;
				min_size = str.length() < min_size ? str.length() : min_size;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void buildGrid() {
		LineNumberReader lnr = null;
		BufferedReader br = null;
		String str = null;
		int w, h, row = 0, col = -1;
		String path = System.getProperty("user.dir");
		path += "/src/" + f_grid;
		try {
			// Read the file
			FileInputStream fs = new FileInputStream(path);
			br = new BufferedReader(new InputStreamReader(fs));

			// Get the number of rows (height)
			lnr = new LineNumberReader(new FileReader(path));
			lnr.skip(Long.MAX_VALUE);
			h = lnr.getLineNumber() + 1;

			// Get the number of columns (width)
			br.mark(1);
			w = br.readLine().split(" ").length;
			br.reset();

			// Setup
			grid = new Character[w][h];

			// Convert into characters
			while ((str = br.readLine()) != null) {
				col = 0;
				for (String c : str.split(" ")) {
					grid[row][col++] = c.toLowerCase().toCharArray()[0];
				}
				row++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Hashtable<String, Short> getWordList() {
		return this.wordList;
	}

	public Character[][] getGrid() {
		return this.grid;
	}

	public int[] getScannerLength() {
		return new int[] { min_size, max_size };
	}

	public static void main(String[] args) throws InterruptedException {

		WordSearch ws = new WordSearch("50States.txt", "50StateNames.txt");

		Character[][] grid = ws.getGrid();
		int min_size = ws.getScannerLength()[0];
		int max_size = ws.getScannerLength()[1];
		CharScannerFactory fact = new CharScannerFactory();
		fact.setGrid(grid);

		ArrayList<CharScanner> scanPool = new ArrayList<CharScanner>();
		for (int i = min_size; i <= max_size; i++) {
			fact.setScannerSize(i);
			scanPool.add(fact.getRightDiagScanner());
			scanPool.add(fact.getLeftDiagScanner());
			scanPool.add(fact.getVerticalScanner());
			scanPool.add(fact.getHorizontalScanner());
		}

		for (CharScanner sc : scanPool) {
			sc.setWordList(ws.getWordList());
		}

		PrintStream os = null;
		try {
			os = new PrintStream("out.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setOut(os);

		System.out.println("Location Letter Direction Word");
		System.out.println("----------------------------------");

		for (int j = 0; j < scanPool.size(); j++) {
			scanPool.get(j).start();
		}

	}

}
