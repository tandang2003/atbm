package model.key;

import java.util.*;

import static model.common.Alphabet.*;

public class CharacterKey implements IKey<Map<Integer, String>> {
    private Map<Integer, String> keys;

    public CharacterKey() {
        genKey();
    }

    public CharacterKey(String key) {
        char[] arrKey = key.toCharArray();
        keys = new HashMap<>();
        for (int i = UPPER_A; i <= UPPER_Z; i++) {
            keys.put(i, String.valueOf(arrKey[i]));
        }
    }

    @Override
    public Map<Integer, String> getKey() {
        return keys;
    }

    @Override
    public void genKey() {
        List<String> textKeys = new ArrayList<>();
        for (int i = UPPER_A; i <= UPPER_Z; i++) {
            textKeys.add(String.valueOf((char) i));
        }
        Collections.shuffle(textKeys);
        keys = new HashMap<>();
        for (int i = 0; i < ENGLISH_ALPHABET; i++) {
            keys.put(i, textKeys.get(i));
        }
    }
}
