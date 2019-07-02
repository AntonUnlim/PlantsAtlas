package com.unlim.plantsatlas.main;

import com.unlim.plantsatlas.data.Listable;

public class LifeForm extends Category implements Listable {
    public LifeForm(int id, String name, String imageFile) {
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

        if (!(obj instanceof LifeForm)) {
            return false;
        }

        LifeForm lf = (LifeForm) obj;
        return getId() == lf.getId();
    }
}
