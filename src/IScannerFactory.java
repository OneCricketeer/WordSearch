public interface IScannerFactory {

	public IScanner getVerticalScanner();

	public IScanner getHorizontalScanner();

	public IScanner getRightDiagScanner();

	public IScanner getLeftDiagScanner();

}
