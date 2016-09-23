package org.florescu.java8examples.model;

import android.support.annotation.ColorRes;

import org.florescu.java8examples.R;

public enum TeamColour {
    RED(R.color.red),
    GREEN(R.color.green);

    private final int colorRes;

    TeamColour(@ColorRes int colorRes) {
        this.colorRes = colorRes;
    }

    @ColorRes
    public int getColorRes() {
        return colorRes;
    }
}
