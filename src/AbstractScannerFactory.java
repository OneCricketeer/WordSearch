public abstract class AbstractScannerFactory implements IScannerFactory {
	protected Comparable[][] grid;
	protected int scannerSize;

	public AbstractScannerFactory() {
		this.grid = new Comparable[][] {};
		this.scannerSize = 1;
	}

	public void setGrid(Comparable[][] grid) {
		this.grid = grid;
	}

	public void setScannerSize(int size) {
		this.scannerSize = size;
	}

	@Override
	public abstract IScanner getVerticalScanner();

	@Override
	public abstract IScanner getHorizontalScanner();

	@Override
	public abstract IScanner getRightDiagScanner();

	@Override
	public abstract IScanner getLeftDiagScanner();
}
