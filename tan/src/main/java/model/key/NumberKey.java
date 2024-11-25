package model.key;

import java.io.DataOutputStream;
import java.io.IOException;

public class NumberKey implements IKey<Integer> {
    private int key;

    public NumberKey(int key) {
        this.key = key;
    }

    @Override
    public Integer getKey() {
        return key;
    }

    @Override
    public void saveToFile(DataOutputStream outputStream) throws IOException {
        outputStream.writeInt(key);
    }

}
