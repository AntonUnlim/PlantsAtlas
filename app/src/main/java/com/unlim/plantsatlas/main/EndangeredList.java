package com.unlim.plantsatlas.main;

import com.unlim.plantsatlas.data.Listable;

public class EndangeredList extends Category implements Listable {
    public EndangeredList(int id, String name, String imageFile) {
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

        if (!(obj instanceof EndangeredList)) {
            return false;
        }

        EndangeredList el = (EndangeredList) obj;

        return getId() == el.getId();
    }
}
