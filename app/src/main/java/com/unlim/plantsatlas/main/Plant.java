package com.unlim.plantsatlas.main;

import java.util.List;

public class Plant {
    private int id;
    private String rusName;
    private String latName;
    private String author;
    private Family family;
    private LifeForm lifeForm;
    private Habitat habitat;
    private List<Value> values;
    private boolean isEndangeredListSaratov;
    private boolean idEndangeredListRussia;
    private FlowerColor flowerColor;
    private String mainPhotoFileName;
    private List<String> listOfAdditionalPhotos;
    private String text;

    // temp
    public Plant(String name) {
        this.rusName = name;
    }

    public Plant(int id, String rusName, String latName, String author,
                 Family family, LifeForm lifeForm, Habitat habitat,
                 List<Value> values,
                 boolean isEndangeredListSaratov, boolean idEndangeredListRussia,
                 FlowerColor flowerColor, String mainPhotoFileName,
                 List<String> listOfAdditionalPhotos, String text) {
        this.id = id;
        this.rusName = rusName;
        this.latName = latName;
        this.author = author;
        this.family = family;
        this.lifeForm = lifeForm;
        this.habitat = habitat;
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

    public Habitat getHabitat() {
        return habitat;
    }

    public List<Value> getValues() {
        return values;
    }

    public boolean isEndangeredListSaratov() {
        return isEndangeredListSaratov;
    }

    public boolean isIdEndangeredListRussia() {
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
}
