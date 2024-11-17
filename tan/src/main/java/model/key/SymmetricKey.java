package model.key;

public class SymmetricKey implements IKey<SymmetricKeyHelper> {
    private SymmetricKeyHelper key;

    public SymmetricKey(SymmetricKeyHelper key) {
        this.key = key;
    }

    public SymmetricKey() {
        this.key = new SymmetricKeyHelper();
    }

    @Override
    public SymmetricKeyHelper getKey() {
        if (key == null) {
            key = new SymmetricKeyHelper();
        }
        return key;
    }

}
