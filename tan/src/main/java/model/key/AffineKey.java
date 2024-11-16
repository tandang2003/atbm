package model.key;

public class AffineKey implements IKey<int[]> {
    private int a, b;

    public AffineKey(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public AffineKey() {
    }

    @Override
    public int[] getKey() {
        return new int[]{a, b};
    }



}
