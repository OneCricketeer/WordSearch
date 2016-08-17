public class CharScannerFactory extends AbstractScannerFactory {

    public CharScannerFactory() {
        super();
    }

    @Override
    public CharScanner getVerticalScanner() {
        return new CharScanner((Character[][]) super.grid, super.scannerSize,
                AbstractScanner.Dir.VERT);
    }

    @Override
    public CharScanner getHorizontalScanner() {
        return new CharScanner((Character[][]) super.grid, super.scannerSize,
                AbstractScanner.Dir.HORIZ);
    }

    @Override
    public CharScanner getRightDiagScanner() {
        return new CharScanner((Character[][]) super.grid, super.scannerSize,
                AbstractScanner.Dir.RDIAG);
    }

    @Override
    public CharScanner getLeftDiagScanner() {
        return new CharScanner((Character[][]) super.grid, super.scannerSize,
                AbstractScanner.Dir.LDIAG);
    }

}
