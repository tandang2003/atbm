package model.key;

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

}
