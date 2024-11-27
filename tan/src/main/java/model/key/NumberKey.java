package model.key;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NumberKey implements IKey<Integer> {
    private int key;

    public NumberKey(int key) {
        this.key = key;
    }

    public NumberKey() {
        this.key = 0;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public Integer getKey() {
        return key;
    }

    @Override
    public void saveToFile(DataOutputStream outputStream) throws IOException {
        outputStream.writeUTF("NumberKey");
        outputStream.writeInt(key);
    }

    @Override
    public void loadFromFile(DataInputStream inputStream) throws IOException {
        String alg = inputStream.readUTF();
        if (!alg.equals("NumberKey")) {
            throw new IOException("Invalid key file");
        }
        try {
            key = inputStream.readInt();
        } catch (Exception e) {
            throw new IOException("Invalid key file");
        }
    }

}
