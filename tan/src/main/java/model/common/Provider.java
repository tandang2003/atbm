package model.common;

import java.util.List;

public enum Provider {
    SUN("SUN"),

    ;

    String name;
    List<String> algRandoms;

    Provider(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
