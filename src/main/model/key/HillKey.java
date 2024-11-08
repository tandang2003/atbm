package main.model.key;

public class HillKey implements IKey<double[][]> {
    private double[][] key;

    public HillKey(double[][] key) {
        this.key = key;
    }

    public HillKey() {
    }

    @Override
    public double[][] getKey() {
        return key;
    }

}
