package model.key;

public class AsymmetricKey implements IKey<AsymmetricKeyHelper> {
    private AsymmetricKeyHelper key;

    public AsymmetricKey(AsymmetricKeyHelper key) {
        this.key = key;
    }

    public AsymmetricKey() {
        this.key = new AsymmetricKeyHelper();
    }

    @Override
    public AsymmetricKeyHelper getKey() {
        if (key == null) {
            key = new AsymmetricKeyHelper();
        }
        return key;
    }
}
