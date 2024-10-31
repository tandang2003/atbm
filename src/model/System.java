package model;

import model.algorithms.IAlgorithms;

import java.util.Objects;

public class System {
    private boolean isEncrypt, isFile, isLoadKey;
    private String mode;
    private IAlgorithms algorithm;
    private String input;
    private static System instance;

    public static System getInstance() {
        if (instance == null) {
            instance = new System();
        }
        return instance;
    }

    private System() {
    }

    public void setInstanceProperties(boolean isEncrypt, boolean isLoadKey, boolean isFile, String mode, IAlgorithms algorithm, String key, String input) {
        this.isEncrypt = isEncrypt;
        this.isFile = isFile;
        this.mode = mode;
        this.algorithm = algorithm;
        this.isLoadKey = isLoadKey;
        this.input = input;
    }
//
//    public System(boolean isEncrypt, boolean isFile, String mode, String algorithm, String key, String input) {
//        this.isEncrypt = isEncrypt;
//        this.isFile = isFile;
//        this.mode = mode;
//        this.algorithm = algorithm;
//        this.key = key;
//        this.input = input;
//    }


    @Override
    public String toString() {
        return "System{" +
                "isEncrypt=" + isEncrypt +
                ", isFile=" + isFile +
                ", mode='" + mode + '\'' +
                ", algorithm='" + algorithm + '\'' +
                ", input='" + input + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        System system = (System) o;
        return isEncrypt == system.isEncrypt && isFile == system.isFile && Objects.equals(mode, system.mode) && Objects.equals(algorithm, system.algorithm) && Objects.equals(input, system.input);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isEncrypt, isFile, mode, algorithm, input);
    }

    public boolean isEncrypt() {
        return isEncrypt;
    }

    public void setEncrypt(boolean encrypt) {
        isEncrypt = encrypt;
    }

    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean file) {
        isFile = file;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public IAlgorithms getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(IAlgorithms algorithm) {
        this.algorithm = algorithm;
    }


    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void genKey() {
        algorithm.genKey();
    }

    public void loadKey(String key) {
        isLoadKey = true;
//        algorithm.loadKey(key);
    }

    public String encrypt() {
        return algorithm.encrypt(input);
    }

    public String decrypt() {
        return algorithm.decrypt(input);
    }
}
