package com.github.mgcvale.projetojava.config;

import java.io.Serializable;

public class AppProperties implements Serializable {
    public String currentTheme;
    public String themeFallback;

    public AppProperties() {
        currentTheme = "macdark";
        themeFallback = "nimbus";
    }

    @Override
    public String toString() {
        return "AppProperties{" +
                "currentTheme='" + currentTheme + '\'' +
                ", themeFallback='" + themeFallback + '\'' +
                '}';
    }
}
