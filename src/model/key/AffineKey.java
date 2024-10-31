package model.key;

import model.utils.MyMath;

import java.util.Random;

public class AffineKey implements IKey<int[]> {
    private int[] key;

    public AffineKey(int[] key) {
        this.key = key;
    }

    public AffineKey() {
        genKey();
    }

    @Override
    public int[] getKey() {
        return key;
    }

    @Override
    public void genKey() {
        int a = genA();
        int b = new Random().nextInt();
        key = new int[]{a, b};
    }

    private int genA() {
        int a = new Random().nextInt();
        while (MyMath.gcd(a, 26) != 1) {
            a = new Random().nextInt();
        }
        return a;
    }

}
