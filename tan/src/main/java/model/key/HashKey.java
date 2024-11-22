package model.key;

public class HashKey implements IKey<HashKeyHelper> {
    private HashKeyHelper key;

    public HashKey(HashKeyHelper key) {
        this.key = key;
    }

    @Override
    public HashKeyHelper getKey() {
        return key;
    }
}
