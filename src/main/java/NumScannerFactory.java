public class NumScannerFactory extends AbstractScannerFactory {

    public NumScannerFactory() {
        super();
    }

    @Override
    public NumScanner getVerticalScanner() {
        return new NumScanner((Double[][]) super.grid, super.scannerSize,
                AbstractScanner.Dir.VERT);
    }

    @Override
    public NumScanner getHorizontalScanner() {
        return new NumScanner((Double[][]) super.grid, super.scannerSize,
                AbstractScanner.Dir.HORIZ);
    }

    @Override
    public NumScanner getRightDiagScanner() {
        return new NumScanner((Double[][]) super.grid, super.scannerSize,
                AbstractScanner.Dir.RDIAG);
    }

    @Override
    public NumScanner getLeftDiagScanner() {
        return new NumScanner((Double[][]) super.grid, super.scannerSize,
                AbstractScanner.Dir.LDIAG);
    }

}
