package com.unlim.plantsatlas.main;

import com.unlim.plantsatlas.data.Listable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
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
        return "\t" + text.replace("\\n","\n\t");
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

    public static Comparator<Listable> sortByRusName() {
        Comparator comp = new Comparator<Plant>() {
            @Override
            public int compare(Plant plant1, Plant plant2) {
                return plant1.getRusName().compareTo(plant2.getRusName());
            }
        };
        return comp;
    }

    public static Comparator<Listable> sortByLatName() {
        Comparator comp = new Comparator<Plant>() {
            @Override
            public int compare(Plant plant1, Plant plant2) {
                return plant1.getLatName().compareTo(plant2.getLatName());
            }
        };
        return comp;
    }

    public boolean hasFlowerColor(int[] flowerColorIDs) {
        if(getFlowerColor() == null) return false;
        for(int ID: flowerColorIDs) {
            if (getFlowerColor().getId() == ID) {
                return true;
            }
        }
        return false;
    }

    public boolean hasLifeForm(int[] lifeFormIDs) {
        if(getLifeForm() == null) return false;
        for(int ID: lifeFormIDs) {
            if (getLifeForm().getId() == ID) {
                return true;
            }
        }
        return false;
    }

    public boolean hasHabitat(int[] habitatIDs) {
        if(getHabitats() == null) return false;
        for(Habitat habitat: habitats) {
            for(int ID: habitatIDs) {
                if (habitat.getId() == ID) return true;
            }
        }
        return false;
    }

    public boolean hasValue(int[] valueIDs) {
        if(getValues() == null) return false;
        for(Value value: values) {
            for(int ID: valueIDs) {
                if (value.getId() == ID) return true;
            }
        }
        return false;
    }

    public boolean hasFamily(int[] familyIDs) {
        if(getFamily() == null) return false;
        for(int ID: familyIDs) {
            if (getFamily().getId() == ID) {
                return true;
            }
        }
        return false;
    }

    public boolean hasEndangeredListSar(int[] yesNoIDs) {
        if(yesNoIDs.length == 2) return true;
        if(isEndangeredListSaratov() && yesNoIDs[0] == 1) return true;
        return !isEndangeredListSaratov() && yesNoIDs[0] == 0;
    }

    public boolean hasEndangeredListRF(int[] yesNoIDs) {
        if(yesNoIDs.length == 2) return true;
        if(isEndangeredListRussia() && yesNoIDs[0] == 1) return true;
        return !isEndangeredListRussia() && yesNoIDs[0] == 0;
    }

    public boolean hasLifeForm(LifeForm lifeForm) {
        return getLifeForm() == lifeForm;
    }

    public boolean hasHabitat(Habitat habitat) {
        if(getHabitats() == null) return false;
        return getHabitats().contains(habitat);
    }

    public boolean hasValue(Value value) {
        //if(getValues() == null) return false;
        return getValues().contains(value);
    }

    public boolean hasFamily(Family family) {
        return getFamily() == family;
    }

    public boolean hasFlowerColor(FlowerColor flowerColor) {
        return getFlowerColor() == flowerColor;
    }
}
