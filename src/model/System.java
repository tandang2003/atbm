package model;

import java.util.Objects;

public class System {
    private boolean isEncrypt, isFile;
    private String mode;
    private String algorithm;
    private String key;
    private String input;

    public System() {
    }

    public System(boolean isEncrypt, boolean isFile, String mode, String algorithm, String key, String input) {
        this.isEncrypt = isEncrypt;
        this.isFile = isFile;
        this.mode = mode;
        this.algorithm = algorithm;
        this.key = key;
        this.input = input;
    }

    @Override
    public String toString() {
        return "System{" +
                "isEncrypt=" + isEncrypt +
                ", isFile=" + isFile +
                ", mode='" + mode + '\'' +
                ", algorithm='" + algorithm + '\'' +
                ", key='" + key + '\'' +
                ", input='" + input + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        System system = (System) o;
        return isEncrypt == system.isEncrypt && isFile == system.isFile && Objects.equals(mode, system.mode) && Objects.equals(algorithm, system.algorithm) && Objects.equals(key, system.key) && Objects.equals(input, system.input);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isEncrypt, isFile, mode, algorithm, key, input);
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

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
