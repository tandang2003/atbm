package model.key;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class CharacterKey implements IKey<Map<String, String>> {
    private Map<String, String> keys;

    public CharacterKey() {
    }

    public CharacterKey(Map<String, String> key) {
        this.keys = key;
    }

    @Override
    public Map<String, String> getKey() {
        return keys;
    }

    @Override
    public void saveToFile(DataOutputStream outputStream) throws IOException {
        outputStream.writeUTF("CharacterKey");

        for (Map.Entry<String, String> entry : keys.entrySet()) {
            outputStream.writeUTF(entry.getKey());
            outputStream.writeUTF(entry.getValue());
        }
    }

    @Override
    public void loadFromFile(DataInputStream inputStream) throws IOException {
        String alg = inputStream.readUTF();
        if (!alg.equals("CharacterKey")) {
            throw new IOException("Invalid key file");
        }
        try {
            keys.clear();
            try {
                while (true) {
                    keys.put(inputStream.readUTF(), inputStream.readUTF());
                }
            } catch (Exception e) {
                System.out.println("End of file");
            }
        } catch (Exception e) {
            throw new IOException("Invalid key file");
        }
    }
}
