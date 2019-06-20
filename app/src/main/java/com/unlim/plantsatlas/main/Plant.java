package com.unlim.plantsatlas.main;

import com.unlim.plantsatlas.data.Listable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class Plant implements Cloneable, Serializable, Listable {
    private int id;
    private String rusName;
    private String latName;
    private String author;
    private Family family;
    private LifeForm lifeForm;
    private List<Habitat> habitats;
    private List<Value> values;
    private boolean isEndangeredListSaratov;
    private boolean idEndangeredListRussia;
    private FlowerColor flowerColor;
    private String mainPhotoFileName;
    private List<String> listOfAdditionalPhotos;
    private String text;

    public Plant(int id, String rusName, String latName, String author,
                 Family family, LifeForm lifeForm, List<Habitat> habitats, List<Value> values,
                 boolean isEndangeredListSaratov, boolean idEndangeredListRussia,
                 FlowerColor flowerColor, String mainPhotoFileName,
                 List<String> listOfAdditionalPhotos, String text) {
        this.id = id;
        this.rusName = rusName;
        this.latName = latName;
        this.author = author;
        this.family = family;
        this.lifeForm = lifeForm;
        this.habitats = habitats;
        this.values = values;
        this.isEndangeredListSaratov = isEndangeredListSaratov;
        this.idEndangeredListRussia = idEndangeredListRussia;
        this.flowerColor = flowerColor;
        this.mainPhotoFileName = mainPhotoFileName;
        this.listOfAdditionalPhotos = listOfAdditionalPhotos;
        this.text = text;
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

    public String getAuthor() {
        return author;
    }

    public Family getFamily() {
        return family;
    }

    public LifeForm getLifeForm() {
        return lifeForm;
    }

    public List<Habitat> getHabitats() {
        return habitats;
    }

    public List<Value> getValues() {
        return values;
    }

    public boolean isEndangeredListSaratov() {
        return isEndangeredListSaratov;
    }

    public boolean isEndangeredListRussia() {
        return idEndangeredListRussia;
    }

    public FlowerColor getFlowerColor() {
        return flowerColor;
    }

    public String getMainPhotoFileName() {
        return mainPhotoFileName;
    }

    public List<String> getListOfAdditionalPhotos() {
        return listOfAdditionalPhotos;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return rusName;
    }

    @Override
    public String getName() {
        return getRusName();
    }

    @Override
    public Plant clone() {
        Plant clone = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            clone = (Plant) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clone;
    }
}