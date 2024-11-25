package model.key;

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
        for (Map.Entry<String, String> entry : keys.entrySet()) {
            outputStream.writeUTF(entry.getKey());
            outputStream.writeUTF(entry.getValue());
        }
    }

}
