package com.unlim.plantsatlas.main;

import android.graphics.Color;

import com.unlim.plantsatlas.data.Listable;

import java.io.Serializable;

public class FlowerColor implements Serializable, Listable {
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

    @Override
    public Listable clone() {
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof FlowerColor)) {
            return false;
        }

        FlowerColor fc = (FlowerColor) obj;

        return fc.getId() == ((FlowerColor) obj).getId();
    }
}
