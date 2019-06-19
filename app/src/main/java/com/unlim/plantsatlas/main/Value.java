package com.unlim.plantsatlas.main;

import com.unlim.plantsatlas.data.Listable;

public class Value extends Category implements Listable {
    public Value(int id, String name, String imageFile) {
        super(id, name, imageFile);
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
        if (!(obj instanceof Value)) {
            return false;
        }
        Value v = (Value) obj;
        return v.getId() == ((Value) obj).getId();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + getId();
        return result;
    }
}
