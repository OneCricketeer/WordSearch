public class NumScanner extends AbstractScanner<Double> {

    private double product = 1;
    private double maxProduct = -1;
    private String info;

    public NumScanner(Double[][] arr, int size, AbstractScanner.Dir direction) {
        super(arr, size, direction);
    }

    public double getProduct() {
        for (int i = 0; product != 0 && i < super.data.size(); i++) {
            double x = Double.valueOf("" + super.data.get(i));
            if (x == 0)
                return 0;
            else {
                product *= x;
            }
        }
        if (product > maxProduct) {
            maxProduct = product;
            // save the start location of the max product
            info = super.getInfo();
        }
        return product;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    protected void empty() {
        getProduct();
        product = 1;
        super.data.clear();
    }

    public double getMaxProduct() {
        return maxProduct;
    }

}
