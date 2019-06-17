package com.unlim.plantsatlas.main;

public class Family {
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
}
