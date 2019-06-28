package com.unlim.plantsatlas.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unlim.plantsatlas.main.EndangeredList;
import com.unlim.plantsatlas.main.Family;
import com.unlim.plantsatlas.main.FlowerColor;
import com.unlim.plantsatlas.main.Habitat;
import com.unlim.plantsatlas.main.LifeForm;
import com.unlim.plantsatlas.main.Plant;
import com.unlim.plantsatlas.main.Section;
import com.unlim.plantsatlas.main.Value;
import com.unlim.plantsatlas.main.YesNo;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Cursor cursor;

    private static List<Listable> families;
    private static List<Listable> flowerColors;
    private static List<Listable> habitats;
    private static List<Listable> lifeForms;
    private static List<Listable> values;
    private static List<Listable> plants;
    private static List<Listable> notFullPlants;
    private static List<Listable> sections;
    private static List<Listable> endangeredLists;
    private static List<Listable> yesNoList;

    public static List<Listable> getFamilies() { return families; }
    public static List<Listable> getFlowerColors() { return flowerColors; }
    public static List<Listable> getHabitats() { return habitats; }
    public static List<Listable> getLifeForms() { return lifeForms; }
    public static List<Listable> getValues() { return values; }
    public static List<Listable> getPlants() { return plants; }
    public static List<Listable> getSections() { return sections; }
    public static List<Listable> getEndangeredLists() { return endangeredLists; }
    public static List<Listable> getNotFullPlants() { return notFullPlants; }
    public static List<Listable> getYesNoList() { return yesNoList; }

    public static void fillMainData(SQLiteDatabase db) throws ClassNotFoundException {
        fillSections(db);
        fillFamilies(db);
        fillFlowerColors(db);
        fillHabitats(db);
        fillLifeForms(db);
        fillValues(db);
        fillPlants(db);
        fillEndangeredLists();
        fillYesNoList();
    }

    private static void fillFamilies(SQLiteDatabase db) {
        families = new ArrayList<>();
        cursor = db.rawQuery(SqlQuery.family, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            families.add(new Family(
                    getIntFromDB("_id"),
                    getStrFromDB("RusName"),
                    getStrFromDB("LatName")));
            cursor.moveToNext();
        }
        cursor.close();
    }
    private static void fillFlowerColors(SQLiteDatabase db) {
        flowerColors = new ArrayList<>();
        cursor = db.rawQuery(SqlQuery.flowerColor, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            flowerColors.add(new FlowerColor(
                    getIntFromDB("_id"),
                    getStrFromDB("Name"),
                    getIntFromDB("Red"),
                    getIntFromDB("Green"),
                    getIntFromDB("Blue")));
            cursor.moveToNext();
        }
        cursor.close();
    }
    private static void fillHabitats(SQLiteDatabase db) {
        habitats = new ArrayList<>();
        cursor = db.rawQuery(SqlQuery.habitat, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            habitats.add(new Habitat(
                    getIntFromDB("_id"),
                    getStrFromDB("Name"),
                    getStrFromDB("Image")));
            cursor.moveToNext();
        }
        cursor.close();
    }
    private static void fillLifeForms(SQLiteDatabase db) {
        lifeForms = new ArrayList<>();
        cursor = db.rawQuery(SqlQuery.lifeForm, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            lifeForms.add(new LifeForm(
                    getIntFromDB("_id"),
                    getStrFromDB("Name"),
                    getStrFromDB("Image")));
            cursor.moveToNext();
        }
        cursor.close();
    }
    private static void fillValues(SQLiteDatabase db) {
        values = new ArrayList<>();
        cursor = db.rawQuery(SqlQuery.value, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            values.add(new Value(
                    getIntFromDB("_id"),
                    getStrFromDB("Name"),
                    getStrFromDB("Image")));
            cursor.moveToNext();
        }
        cursor.close();
    }
    private static void fillPlants(SQLiteDatabase db) {
        plants = new ArrayList<>();
        cursor = db.rawQuery(SqlQuery.plantMain, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = getIntFromDB("_id");
            String mainPhotoFileName = cursor.isNull(
                    cursor.getColumnIndex("PhotoFileName")) ?
                    null : cursor.getString(cursor.getColumnIndex("PhotoFileName"));

            plants.add(new Plant(
                    id,
                    getStrFromDB("RusName"),
                    getStrFromDB("LatName"),
                    getStrFromDB("Author"),
                    getFamilyById(getIntFromDB("Family")),
                    getLifeFormById(getIntFromDB("LifeForm")),
                    getPlantHabitats(db, id),
                    getPlantValuesFromDB(db, id),
                    getIntFromDB("EndangeredListSaratov") == 1,
                    getIntFromDB("EndangeredListRussia") == 1,
                    getFlowerColorById(getIntFromDB("FlowerColor")),
                    mainPhotoFileName,
                    getPlantAdditionalPhotos(db, id),
                    getStrFromDB("Text")
                    )
            );
            cursor.moveToNext();
        }
        cursor.close();
    }
    private static void fillSections(SQLiteDatabase db) throws ClassNotFoundException {
        sections = new ArrayList<>();
        Cursor cursor = db.rawQuery(SqlQuery.section, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex("Name"));
            String className = cursor.getString(cursor.getColumnIndex("ClassName"));
            sections.add(new Section(id, name, Class.forName(className)));
            cursor.moveToNext();
        }
        cursor.close();
    }
    private static void fillEndangeredLists() {
        endangeredLists = new ArrayList<>();
        endangeredLists.add(new EndangeredList(1, "Красная книга Саратовской области", ""));
        endangeredLists.add(new EndangeredList(2, "Красная книга РФ", ""));
    }
    private static void fillYesNoList() {
        yesNoList = YesNo.getYesNoList();
    }

    private static List<Value> getPlantValuesFromDB(SQLiteDatabase db, int id) {
        Cursor valueCursor = db.rawQuery(SqlQuery.plantValue + id, null);
        valueCursor.moveToFirst();
        List<Value> values = new ArrayList<>();
        while (!valueCursor.isAfterLast()) {
            int idValue = valueCursor.getInt(valueCursor.getColumnIndex("idValue"));
            values.add(getValueById(idValue));
            valueCursor.moveToNext();
        }
        valueCursor.close();
        return values;
    }
    private static List<String> getPlantAdditionalPhotos(SQLiteDatabase db, int id) {
        Cursor photoCursor = db.rawQuery(SqlQuery.plantPhoto + id, null);
        photoCursor.moveToFirst();
        List<String> additionalPhotos = new ArrayList<>();
        while (!photoCursor.isAfterLast()) {
            String fileName = photoCursor.getString(photoCursor.getColumnIndex("fileName"));
            additionalPhotos.add(fileName);
            photoCursor.moveToNext();
        }
        photoCursor.close();
        return additionalPhotos;
    }
    private static List<Habitat> getPlantHabitats(SQLiteDatabase db, int id) {
        Cursor habitatCursor = db.rawQuery(SqlQuery.plantHabitat + id, null);
        habitatCursor.moveToFirst();
        List<Habitat> habitats = new ArrayList<>();
        while (!habitatCursor.isAfterLast()) {
            int idHabitat = habitatCursor.getInt(habitatCursor.getColumnIndex("idHabitat"));
            habitats.add(getHabitatById(idHabitat));
            habitatCursor.moveToNext();
        }
        habitatCursor.close();
        return habitats;
    }

    private static int getIntFromDB(String fieldName) {
        return cursor.getInt(cursor.getColumnIndex(fieldName));
    }
    private static String getStrFromDB(String fieldName) {
        return cursor.getString(cursor.getColumnIndex(fieldName));
    }

    public static Family getFamilyById(int id) {
        for(Listable family: families) {
            if (((Family)family).getId() == id) return (Family)family;
        }
        return null;
    }
    public static FlowerColor getFlowerColorById(int id) {
        for(Listable flowerColor: flowerColors) {
            if (((FlowerColor)flowerColor).getId() == id) return (FlowerColor)flowerColor;
        }
        return null;
    }
    public static Habitat getHabitatById(int id) {
        for(Listable habitat: habitats) {
            if (((Habitat)habitat).getId() == id) return (Habitat)habitat;
        }
        return null;
    }
    public static LifeForm getLifeFormById(int id) {
        for(Listable lifeForm: lifeForms) {
            if (((LifeForm)lifeForm).getId() == id) return (LifeForm) lifeForm;
        }
        return null;
    }
    public static Value getValueById(int id) {
        for(Listable value: values) {
            if (((Value)value).getId() == id) return (Value) value;
        }
        return null;
    }
    public static EndangeredList getEndangeredListById(int id) {
        for(Listable endangeredList: endangeredLists) {
            if (((EndangeredList)endangeredList).getId() == id) return (EndangeredList) endangeredList;
        }
        return null;
    }
    public static Plant getPlantById(int id) {
        for(Listable plant: plants) {
            if (((Plant)plant).getId() == id) return (Plant) plant;
        }
        return null;
    }

    private static List<Listable> getPlantsByFamily(Family family) {
        List<Listable> resultList = new ArrayList<>();
        for(Listable plant : plants) {
            if(((Plant)plant).getFamily() == family) {
                resultList.add(plant);
            }
        }
        return resultList;
    }
    private static List<Listable> getPlantsByLifeForm(LifeForm lifeForm) {
        List<Listable> resultList = new ArrayList<>();
        for(Listable plant : plants) {
            if(((Plant)plant).getLifeForm() == lifeForm) {
                resultList.add(plant);
            }
        }
        return resultList;
    }
    private static List<Listable> getPlantsByFlowerColor(FlowerColor flowerColor) {
        List<Listable> resultList = new ArrayList<>();
        for(Listable plant : plants) {
            if(((Plant)plant).getFlowerColor() == flowerColor) {
                resultList.add(plant);
            }
        }
        return resultList;
    }
    private static List<Listable> getPlantsByValue(Value value) {
        List<Listable> resultList = new ArrayList<>();
        for(Listable plant : plants) {
            if(((Plant)plant).getValues().contains(value)) {
                resultList.add(plant);
            }
        }
        return resultList;
    }
    private static List<Listable> getPlantsByHabitat(Habitat habitat) {
        List<Listable> resultList = new ArrayList<>();
        for(Listable plant : plants) {
            if(((Plant)plant).getHabitats().contains(habitat)) {
                resultList.add(plant);
            }
        }
        return resultList;
    }
    private static List<Listable> getPlantsByEndangeredList(EndangeredList endangeredList) {
        List<Listable> resultList = new ArrayList<>();
        for(Listable plant : plants) {
            if(endangeredList.getId() == 1 && ((Plant)plant).isEndangeredListSaratov()) {
                resultList.add(plant);
            }
            if(endangeredList.getId() == 2 && ((Plant)plant).isEndangeredListRussia()) {
                resultList.add(plant);
            }
        }
        return resultList;
    }

    public static void setNotFullPlants(Object tag) {
        if (tag instanceof Family) {
            notFullPlants = getPlantsByFamily((Family)tag);
        } else if (tag instanceof LifeForm) {
            notFullPlants = getPlantsByLifeForm((LifeForm)tag);
        } else if (tag instanceof FlowerColor) {
            notFullPlants = getPlantsByFlowerColor((FlowerColor)tag);
        } else if (tag instanceof Value) {
            notFullPlants = getPlantsByValue((Value)tag);
        } else if (tag instanceof Habitat) {
            notFullPlants = getPlantsByHabitat((Habitat)tag);
        } else if (tag instanceof EndangeredList) {
            notFullPlants = getPlantsByEndangeredList((EndangeredList) tag);
        }
    }
    public static List<Listable> searchPlants(String searchString, Family family, LifeForm lifeForm,
                                              Value value, Habitat habitat, FlowerColor flowerColor,
                                              YesNo endangeredListSar, YesNo endangeredListRF) {
        List<Listable> resultList = new ArrayList<>();
        int[] familyIDs = fillFamilyIDsForSearch(family);
        int[] lifeFormIDs = fillLifeFormIDsForSearch(lifeForm);
        int[] valueIDs = fillValueIDsForSearch(value);
        int[] habitatIDs = fillHabitatIDsForSearch(habitat);
        int[] flowerColorIDs = fillFlowerColorIDsForSearch(flowerColor);
        int[] endangeredListSaratov = fillYesNoIDsForSearch(endangeredListSar);
        int[] endangeredListRussia = fillYesNoIDsForSearch(endangeredListRF);

        for(Listable plant: plants) {
            if((((Plant)plant).getRusName().toUpperCase().contains(searchString.toUpperCase())
                    || ((Plant)plant).getLatName().toUpperCase().contains(searchString.toUpperCase())
                    || ((Plant)plant).getText().toUpperCase().contains(searchString.toUpperCase()))
                && ((Plant)plant).hasFamily(familyIDs) &&
                    ((Plant)plant).hasLifeForm(lifeFormIDs) &&
                    (((Plant)plant).hasValue(valueIDs) || ((Plant)plant).getValues().size() == 0) &&
                    (((Plant)plant).hasHabitat(habitatIDs) || ((Plant)plant).getHabitats().size() == 0) &&
                    (((Plant)plant).hasFlowerColor(flowerColorIDs) || ((flowerColorIDs.length > 1) && ((Plant)plant).getFlowerColor() == null)) &&
                    ((Plant)plant).hasEndangeredListSar(endangeredListSaratov) &&
                    ((Plant)plant).hasEndangeredListRF(endangeredListRussia)) {
                resultList.add(plant);
            }
        }
        return resultList;
    }

    public static List<Listable> definePlants(FlowerColor flowerColor, LifeForm lifeForm, Habitat habitat) {
        List<Listable> resultList = new ArrayList<>();
        int[] flowerColorIDs = fillFlowerColorIDsForSearch(flowerColor);
        int[] lifeFormIDs = fillLifeFormIDsForSearch(lifeForm);
        int[] habitatIDs = fillHabitatIDsForSearch(habitat);

        for(Listable plant: plants) {
            if (((Plant)plant).hasFlowerColor(flowerColorIDs) &&
                    ((Plant)plant).hasLifeForm(lifeFormIDs) &&
                    ((Plant)plant).hasHabitat(habitatIDs)) {
                resultList.add(plant);
            }
        }
        return resultList;
    }

    private static int[] fillFlowerColorIDsForSearch(FlowerColor flowerColor) {
        int[] flowerColorIDs;
        if (flowerColor == null) {
            flowerColorIDs = new int[flowerColors.size()];
            for(int i = 0; i < flowerColors.size(); i++) {
                flowerColorIDs[i] = ((FlowerColor)flowerColors.get(i)).getId();
            }
        } else {
            flowerColorIDs = new int[1];
            flowerColorIDs[0] = flowerColor.getId();
        }
        return flowerColorIDs;
    }
    private static int[] fillFamilyIDsForSearch(Family family) {
        int[] familyIDs;
        if (family == null) {
            familyIDs = new int[families.size()];
            for(int i = 0; i < families.size(); i++) {
                familyIDs[i] = ((Family)families.get(i)).getId();
            }
        } else {
            familyIDs = new int[1];
            familyIDs[0] = family.getId();
        }
        return familyIDs;
    }
    private static int[] fillLifeFormIDsForSearch(LifeForm lifeForm) {
        int[] lifeFormIDs;
        if (lifeForm == null) {
            lifeFormIDs = new int[lifeForms.size()];
            for(int i = 0; i < lifeForms.size(); i++) {
                lifeFormIDs[i] = ((LifeForm)lifeForms.get(i)).getId();
            }
        } else {
            lifeFormIDs = new int[1];
            lifeFormIDs[0] = lifeForm.getId();
        }
        return lifeFormIDs;
    }
    private static int[] fillHabitatIDsForSearch(Habitat habitat) {
        int[] habitatIDs;
        if (habitat == null) {
            habitatIDs = new int[habitats.size()];
            for(int i = 0; i < habitats.size(); i++) {
                habitatIDs[i] = ((Habitat)habitats.get(i)).getId();
            }
        } else {
            habitatIDs = new int[1];
            habitatIDs[0] = habitat.getId();
        }
        return habitatIDs;
    }
    private static int[] fillValueIDsForSearch(Value value) {
        int[] valueIDs;
        if (value == null) {
            valueIDs = new int[values.size()];
            for(int i = 0; i < values.size(); i++) {
                valueIDs[i] = ((Value)values.get(i)).getId();
            }
        } else {
            valueIDs = new int[1];
            valueIDs[0] = value.getId();
        }
        return valueIDs;
    }
    private static int[] fillYesNoIDsForSearch(YesNo yesNo) {
        int[] yesNoIDs;
        if (yesNo == null) {
            yesNoIDs = new int[yesNoList.size()];
            for(int i = 0; i < yesNoList.size(); i++) {
                yesNoIDs[i] = ((YesNo)yesNoList.get(i)).getId();
            }
        } else {
            yesNoIDs = new int[1];
            yesNoIDs[0] = yesNo.getId();
        }
        return yesNoIDs;
    }
}
