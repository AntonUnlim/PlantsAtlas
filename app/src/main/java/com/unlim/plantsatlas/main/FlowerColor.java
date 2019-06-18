package com.unlim.plantsatlas.main;

import android.graphics.Color;

public class FlowerColor {
    private int id;
    private String name;
    private int color;

    public FlowerColor(int id, String name, int red, int green, int blue) {
        this.id = id;
        this.name = name;
        this.color = Color.rgb(red,green,blue);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getIntColor() {
        return color;
    }

    @Override
    public String toString() {
        return name;
    }
}
