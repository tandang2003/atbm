package model.common;

import java.util.List;

public enum Provider {
    SUN("SUN"),
    SUN_JCE("SunJCE"),
    SUN_JSSE("SunJSSE"),
    SUN_RSA_SIGN("SunRsaSign"),
    BC("BC"),
    ;

    String name;

    Provider(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
