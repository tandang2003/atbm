package model.algorithms.classicEncryption;


import model.algorithms.AAlgorithm;
import model.common.Cipher;
import model.common.ICipherEnum;
import model.key.CharacterKey;

import java.util.*;

public class SubstitutionAlgorithm extends AAlgorithm {
//    IKey<Map<Integer, String>> keys;

    public SubstitutionAlgorithm(String keys, List<String> chars) {
        super();
        //TODO:  validation it need checking key to fill or cancel it and unique
        this.arrChar = chars;
        this.key = validationKey(keys);
    }

    public SubstitutionAlgorithm(List<String> chars) {
        super();
        this.arrChar = chars;
    }

    @Override
    public void genKey() {
        List<String> arrChar = new ArrayList<>(this.arrChar);
        Collections.shuffle(arrChar);
        Map<String, String> keys = new HashMap<>();
        for (int i = 0; i < arrChar.size(); i++)
            keys.put(this.arrChar.get(i), arrChar.get(i));
        this.key = new CharacterKey(keys);
    }

    private CharacterKey validationKey(String loadKey) {
        String k = uniqueString(loadKey);
        List<String> arChar = new ArrayList<>(this.arrChar);
        Map<String, String> keys = new HashMap<>();
        if (k.length() < arChar.size()) {
            for (int i = 0; i < k.length(); i++) {
                keys.put(arChar.getFirst(), String.valueOf(k.charAt(i)).toUpperCase());
                arChar.removeFirst();
            }
        }
        for (String s : arChar) {
            keys.put(s, s);
        }
        return new CharacterKey(keys);
    }

    @Override
    public String encrypt(String input) {
        char[] textInput = input.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : textInput)
            if (!this.arrChar.contains(String.valueOf(c).toUpperCase()))
                sb.append(c);
            else
                sb.append(encryptToken(c, Character.isUpperCase(c)));

        return sb.toString();
    }

    public String encryptToken(char token, boolean isUpperCase) {
        Map<String, String> key = (Map<String, String>) ((this.key).getKey());
        if (isUpperCase)
            return key.get(String.valueOf(token)).toUpperCase();

        return key.get(String.valueOf(token).toUpperCase()).toLowerCase();
    }

    @Override
    public String decrypt(String encryptInput) {
        char[] textInput = encryptInput.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : textInput)
            if (!this.arrChar.contains(String.valueOf(c).toUpperCase()))
                sb.append(c);
            else
                sb.append(decryptToken(c, Character.isUpperCase(c)));

        return sb.toString();
    }

    @Override
    public ICipherEnum getCipher() {
        return Cipher.SUBSTITUTION;
    }

    @Override
    public void updateKey(Object[] key) {
        this.key = validationKey((String) key[0]);
    }


    public String decryptToken(char token, boolean isUpperCase) {
        Map<String, String> key = (Map<String, String>) this.key.getKey();
        for (Map.Entry<String, String> entry : key.entrySet()) {
            if (isUpperCase)
                if (entry.getValue().equals(String.valueOf(token)))
                    return entry.getKey();
            if (entry.getValue().equals(String.valueOf(token).toUpperCase()))
                return entry.getKey().toLowerCase();

        }
        return String.valueOf(token);
    }


    private String uniqueString(String key) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : key.toCharArray()) {
            if (stringBuilder.indexOf(String.valueOf(c)) != -1) continue;
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    @Override
    protected boolean validation() {
        return false;
    }

//    public static void main(String[] args) {
//        TranspositionAlgorithm1 algorithm = new TranspositionAlgorithm1(Alphabet.VIETNAMESE_CHAR_SET);
//        algorithm.genKey();
//
//        String input = "Nguyễn Văn Á";
//        String encrypt = algorithm.encrypt(input);
//        System.out.println(encrypt);
//        String decrypt = algorithm.decrypt(encrypt);
//        System.out.println(decrypt);
//
//    }
}
