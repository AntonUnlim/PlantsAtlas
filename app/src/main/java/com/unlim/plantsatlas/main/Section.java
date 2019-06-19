package com.unlim.plantsatlas.main;

import com.unlim.plantsatlas.data.Listable;

public class Section implements Listable {
    private int id;
    private String name;
    private Class classToStart;

    public Section(int id, String name, Class classToStart) {
        this.id = id;
        this.name = name;
        this.classToStart = classToStart;
    }

    public Class getClassToStart() {
        return classToStart;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Listable clone() {
        return this;
    }

    @Override
    public String toString() {
        return name;
    }
}
