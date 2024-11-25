package model.key;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class AffineKey implements IKey<int[]> {
    private int a, b;

    public AffineKey(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public AffineKey() {
    }

    @Override
    public int[] getKey() {
        return new int[]{a, b};
    }

    @Override
    public void saveToFile(DataOutputStream outputStream) throws IOException {
        outputStream.writeUTF("AffineKey");
        outputStream.writeInt(a);
        outputStream.writeInt(b);
    }

    @Override
    public void loadFromFile(DataInputStream inputStream) throws IOException {
        String alg = inputStream.readUTF();
        if (!alg.equals("AffineKey")) {
            throw new IOException("Invalid key file");
        }
        try {
            a = inputStream.readInt();
            b = inputStream.readInt();
        } catch (Exception e) {
            throw new IOException("Invalid key file");
        }
    }
}
