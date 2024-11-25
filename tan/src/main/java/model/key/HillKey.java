package model.key;

import java.io.DataOutputStream;
import java.io.IOException;

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

    @Override
    public void saveToFile(DataOutputStream outputStream) throws IOException {
        outputStream.writeInt(key.length);
        for (double[] doubles : key) {
            for (double aDouble : doubles) {
                outputStream.writeDouble(aDouble);
            }
        }
    }


}
