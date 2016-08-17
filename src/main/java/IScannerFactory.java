public interface IScannerFactory {

    IScanner getVerticalScanner();

    IScanner getHorizontalScanner();

    IScanner getRightDiagScanner();

    IScanner getLeftDiagScanner();

}
