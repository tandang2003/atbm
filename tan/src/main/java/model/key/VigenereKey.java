package model.key;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class VigenereKey implements IKey<String[]> {
    private String[] key;

    public VigenereKey(String[] key) {
        this.key = key;
    }

    public VigenereKey() {
        this.key = new String[0];
    }
    @Override
    public String[] getKey() {
        return key;
    }

    @Override
    public void saveToFile(DataOutputStream outputStream) throws IOException {
        outputStream.writeUTF("VigenereKey");
        outputStream.writeUTF(String.join("", key));
    }

    @Override
    public void loadFromFile(DataInputStream inputStream) throws IOException {
        String alg = inputStream.readUTF();
        if (!alg.equals("VigenereKey")) {
            throw new IOException("Invalid key file");
        }
        try {
            key = inputStream.readUTF().split("");
        } catch (Exception e) {
            throw new IOException("Invalid key file");
        }
    }

}
