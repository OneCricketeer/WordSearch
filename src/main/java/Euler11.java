import java.io.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Find the maximum product of a certain number of consecutive numbers in a 2D
 * grid
 *
 * @author Jordan
 */
public class Euler11 {

    public void run() throws InterruptedException {
        Double[][] nums = null;

        BufferedReader br;
        LineNumberReader lnr;
        String str;
        int w, h, row = 0, col;
        String path = ClassLoader.getSystemResource("numberGrid.txt").getFile();

        try {
            // Read the file
            final File f = new File(path);
            FileInputStream fs = new FileInputStream(f);
            br = new BufferedReader(new InputStreamReader(fs));

            // Get the number of lines (height)
            lnr = new LineNumberReader(new FileReader(f));
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Setup the scanners
        int size = 4;
        NumScannerFactory fact = new NumScannerFactory();
        fact.setGrid(nums);
        fact.setScannerSize(size);

        List<NumScanner> scanners = Arrays.asList(
                fact.getHorizontalScanner(),
                fact.getVerticalScanner(),
                fact.getLeftDiagScanner(),
                fact.getRightDiagScanner()
        );

        scanners.forEach(NumScanner::start);
        for (NumScanner sc : scanners) {
            sc.join();
        }

        // Find the answer
        DecimalFormat fmt = new DecimalFormat("#.###");
        Optional<NumScanner> m = scanners.stream().max((sc1, sc2) -> Double.compare(sc1.getMaxProduct(), sc2.getMaxProduct()));

        if (m.isPresent()) {
            System.out.printf("Max Product of size %d: %s.\n", size,
                    fmt.format(m.get().getMaxProduct()));
        }
    }

}
