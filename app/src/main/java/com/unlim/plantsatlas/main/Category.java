package com.unlim.plantsatlas.main;

import java.io.Serializable;

public abstract class Category implements Serializable {
    private int id;
    private String name;
    private String imageFile;

    public Category(int id, String name, String imageFile) {
        this.id = id;
        this.name = name;
        this.imageFile = imageFile;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageFile() {
        return imageFile;
    }

    @Override
    public String toString() {
        return name;
    }

}
