package com.unlim.plantsatlas.main;

import com.unlim.plantsatlas.data.Listable;

import java.util.ArrayList;
import java.util.List;

public class YesNo implements Listable {

    private int id;
    private String name;

    private YesNo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<Listable> getYesNoList() {
        List<Listable> yesNoList = new ArrayList<>();
        yesNoList.add(new YesNo(0, "Нет"));
        yesNoList.add(new YesNo(1, "Да"));
        return yesNoList;
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
}
