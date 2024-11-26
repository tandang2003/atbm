package model.key;

import java.io.DataInputStream;
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
        outputStream.writeUTF("HillKey");
        outputStream.writeInt(key.length);
        for (double[] doubles : key) {
            for (double aDouble : doubles) {
                outputStream.writeDouble(aDouble);
            }
        }
    }

    @Override
    public void loadFromFile(DataInputStream inputStream) throws IOException {
        String alg = inputStream.readUTF();
        if (!alg.equals("HillKey")) {
            throw new IOException("Invalid key file");
        }
        if (key == null) {
            key = new double[2][2];
        }
        int size = inputStream.readInt();
        if (size != key.length) {
            throw new IOException("Invalid key file. This key is for " + size + " key size but the current key is " + key.length);
        }
        try {
            key = new double[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    key[i][j] = inputStream.readDouble();
                }
            }
        } catch (Exception e) {
            throw new IOException("Invalid key file");
        }
    }


}
