package model.key;

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
            outputStream.writeInt(a);
            outputStream.writeInt(b);
    }
}
