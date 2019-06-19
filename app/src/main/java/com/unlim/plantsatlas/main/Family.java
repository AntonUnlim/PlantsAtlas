package com.unlim.plantsatlas.main;

import com.unlim.plantsatlas.data.Listable;

import java.io.Serializable;

public class Family implements Serializable, Listable {
    private int id;
    private String rusName;
    private String latName;

    public Family(int id, String rusName, String latName) {
        this.id = id;
        this.rusName = rusName;
        this.latName = latName;
    }

    public int getId() {
        return id;
    }

    public String getRusName() {
        return rusName;
    }

    public String getLatName() {
        return latName;
    }

    @Override
    public String toString() {
        return this.rusName + " - " + latName;
    }

    @Override
    public String getName() {
        return this.toString();
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

        if (!(obj instanceof Family)) {
            return false;
        }

        Family f = (Family) obj;

        return f.getId() == ((Family) obj).getId();
    }
}
