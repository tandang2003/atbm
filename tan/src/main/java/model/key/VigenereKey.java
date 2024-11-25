package model.key;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class VigenereKey implements IKey<String[]> {
    private String[] key;

    public VigenereKey(String[] key) {
        this.key = key;
    }

    @Override
    public String[] getKey() {
        return key;
    }

    @Override
    public void saveToFile(DataOutputStream outputStream) throws IOException {
        outputStream.writeUTF(String.join("", key));
    }

}
