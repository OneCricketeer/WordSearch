import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class WordSearch {
    private final Hashtable<String, Short> wordTable = new Hashtable<String, Short>();
    private final String gridFile, wordList;
    // private BufferedReader br;
    private Character[][] grid;
    private int maxScannerSize, minScannerSize;

    public WordSearch(String gridFile, String wordList) {
        this.gridFile = gridFile;
        this.wordList = wordList;
        minScannerSize = Integer.MAX_VALUE;
        buildWordList();
        buildGrid();
    }

    private void buildWordList() {
        String str;
        String path = ClassLoader.getSystemResource(wordList).getFile();
        BufferedReader br;
        try {
            // Read the file
            FileInputStream fs = new FileInputStream(path);
            br = new BufferedReader(new InputStreamReader(fs));

            // Read each word
            while ((str = br.readLine()) != null) {
                // Put each word into a table
                wordTable.put(str.toLowerCase(), (short) 0);
                // Set the max&min size of the scanner
                maxScannerSize = Math.max(str.length(), maxScannerSize);
                minScannerSize = Math.min(str.length(), minScannerSize);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildGrid() {
        LineNumberReader lnr;
        BufferedReader br;
        String str;
        int w, h, row = 0, col;
        String path = ClassLoader.getSystemResource(gridFile).getFile();
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

    public Hashtable<String, Short> getWordTable() {
        return this.wordTable;
    }

    public Character[][] getGrid() {
        return this.grid;
    }

    public int[] getScannerLength() {
        return new int[]{minScannerSize, maxScannerSize};
    }

    public void run() throws InterruptedException {

        Character[][] grid = getGrid();
        int min_size = getScannerLength()[0];
        int max_size = getScannerLength()[1];
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

        final Hashtable<String, Short> wordList = getWordTable();
        scanPool.forEach(charScanner -> charScanner.setWordList(wordList));

        PrintStream os = null;
        try {
            os = new PrintStream("out.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.setOut(os);

        System.out.println("Location Letter Direction Word");
        System.out.println("----------------------------------");

        scanPool.forEach(CharScanner::start);
    }

}
