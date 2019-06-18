package com.unlim.plantsatlas.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unlim.plantsatlas.main.Family;
import com.unlim.plantsatlas.main.FlowerColor;
import com.unlim.plantsatlas.main.Habitat;
import com.unlim.plantsatlas.main.LifeForm;
import com.unlim.plantsatlas.main.Plant;
import com.unlim.plantsatlas.main.Value;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private static List<Family> families;
    private static List<FlowerColor> flowerColors;
    private static List<Habitat> habitats;
    private static List<LifeForm> lifeForms;
    private static List<Value> values;
    private static List<Plant> plants;
    private static List<String> sections;

    public static List<Family> getFamilies() {
        return families;
    }
    public static List<FlowerColor> getFlowerColors() {
        return flowerColors;
    }
    public static List<Habitat> getHabitats() {
        return habitats;
    }
    public static List<LifeForm> getLifeForms() {
        return lifeForms;
    }
    public static List<Value> getValues() {
        return values;
    }
    public static List<Plant> getPlants() {
        return plants;
    }
    public static List<String> getSections() {
        return sections;
    }

    public static void fillMainData(SQLiteDatabase db) {
        fillSections(db);
        fillFamilies(db);
        fillFlowerColors(db);
        fillHabitats(db);
        fillLifeForms(db);
        fillValues(db);
        fillPlants(db);
    }

    private static void fillFamilies(SQLiteDatabase db) {
        families = new ArrayList<>();
        String query = "SELECT _id, " +
                "RusName, " +
                "LatName " +
                "FROM Family " +
                "ORDER BY RusName";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String rusName = cursor.getString(1);
            String latName = cursor.getString(2);
            families.add(new Family(id, rusName, latName));
            cursor.moveToNext();
        }
        cursor.close();
    }
    private static void fillFlowerColors(SQLiteDatabase db) {
        flowerColors = new ArrayList<>();
        String query = "SELECT _id, " +
                "Name, " +
                "IFNULL(Red, 255) AS Red, " +
                "IFNULL(Green, 255) AS Green, " +
                "IFNULL(Blue, 255) AS Blue " +
                "FROM FlowerColor ORDER BY \"Order\"";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int red = cursor.getInt(2);
            int green = cursor.getInt(3);
            int blue = cursor.getInt(4);
            flowerColors.add(new FlowerColor(id, name, red, green, blue));
            cursor.moveToNext();
        }
        cursor.close();
    }
    private static void fillHabitats(SQLiteDatabase db) {
        habitats = new ArrayList<>();
        String query = "SELECT _id, " +
                "Name, " +
                "Image " +
                "FROM Habitat " +
                "ORDER BY \"Order\"";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String imageFile = cursor.getString(2);
            habitats.add(new Habitat(id, name, imageFile));
            cursor.moveToNext();
        }
        cursor.close();
    }
    private static void fillLifeForms(SQLiteDatabase db) {
        lifeForms = new ArrayList<>();
        String query = "SELECT _id, " +
                "Name, " +
                "Image " +
                "FROM LifeForm " +
                "ORDER BY \"Order\"";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String imageFile = cursor.getString(2);
            lifeForms.add(new LifeForm(id, name, imageFile));
            cursor.moveToNext();
        }
        cursor.close();
    }
    private static void fillValues(SQLiteDatabase db) {
        values = new ArrayList<>();
        String query = "SELECT _id, " +
                "Name, " +
                "Image " +
                "FROM Value " +
                "ORDER BY \"Order\"";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String imageFile = cursor.getString(2);
            values.add(new Value(id, name, imageFile));
            cursor.moveToNext();
        }
        cursor.close();
    }
    private static void fillPlants(SQLiteDatabase db) {
        plants = new ArrayList<>();
        String mainQuery = "SELECT " +
                "p._id, " +
                "p.RusName, " +
                "p.LatName, " +
                "p.Author, " +
                "p.Family, " +
                "IFNULL(p.LifeForm, 0) AS LifeForm, " +
                "IFNULL(p.Habitat, 0) AS Habitat, " +
                "p.EndangeredListSaratov, " +
                "p.EndangeredListRussia, " +
                "IFNULL(p.FlowerColor, 0) AS FlowerColor, " +
                "pt.Text, " +
                "ph.fileName AS PhotoFileName " +
                "FROM Plant p " +
                "JOIN PlantText pt ON p._id = pt.idPlant " +
                "LEFT JOIN PlantPhoto pp ON p._id = pp.idPlant AND pp.isMainPhoto = 1 " +
                "LEFT JOIN Photo ph ON pp.idPhoto = ph._id " +
                "ORDER BY p.RusName";
        Cursor mainCursor = db.rawQuery(mainQuery, null);
        mainCursor.moveToFirst();
        while (!mainCursor.isAfterLast()) {
            int id = mainCursor.getInt(mainCursor.getColumnIndex("_id"));
            String rusName = mainCursor.getString(mainCursor.getColumnIndex("RusName"));
            String latName = mainCursor.getString(mainCursor.getColumnIndex("LatName"));
            String author = mainCursor.getString(mainCursor.getColumnIndex("Author"));
            int idFamily = mainCursor.getInt(mainCursor.getColumnIndex("Family"));
            int idLifeForm = mainCursor.getInt(mainCursor.getColumnIndex("LifeForm"));
            int idHabitat = mainCursor.getInt(mainCursor.getColumnIndex("Habitat"));
            boolean isEndangeredListSaratov = mainCursor.getInt(mainCursor.getColumnIndex("EndangeredListSaratov")) == 1;
            boolean isEndangeredListRussia = mainCursor.getInt(mainCursor.getColumnIndex("EndangeredListRussia")) == 1;
            int idFlowerColor = mainCursor.getInt(mainCursor.getColumnIndex("FlowerColor"));
            String text = mainCursor.getString(mainCursor.getColumnIndex("Text"));
            String mainPhotoFileName = mainCursor.isNull(
                    mainCursor.getColumnIndex("PhotoFileName")) ?
                    null : mainCursor.getString(mainCursor.getColumnIndex("PhotoFileName"));

            String valueQuery = "SELECT idValue FROM PlantValue WHERE idPlant = " + id;
            Cursor valueCursor = db.rawQuery(valueQuery, null);
            valueCursor.moveToFirst();
            List<Value> values = new ArrayList<>();
            while (!valueCursor.isAfterLast()) {
                int idValue = valueCursor.getInt(valueCursor.getColumnIndex("idValue"));
                values.add(getValueById(idValue));
                valueCursor.moveToNext();
            }
            valueCursor.close();

            String photoQuery = "SELECT p.fileName FROM PlantPhoto pp JOIN Photo p ON pp.idPhoto = p._id WHERE isMainPhoto = 0 AND idPlant = " + id;
            Cursor photoCursor = db.rawQuery(photoQuery, null);
            photoCursor.moveToFirst();
            List<String> additionalPhotos = new ArrayList<>();
            while (!photoCursor.isAfterLast()) {
                String fileName = photoCursor.getString(photoCursor.getColumnIndex("fileName"));
                additionalPhotos.add(fileName);
                photoCursor.moveToNext();
            }
            photoCursor.close();

            plants.add(new Plant(id, rusName, latName, author, getFamilyById(idFamily), getLifeFormById(idLifeForm),
                    getHabitatById(idHabitat), values, isEndangeredListSaratov, isEndangeredListRussia,
                    getFlowerColorById(idFlowerColor), mainPhotoFileName, additionalPhotos, text));
            mainCursor.moveToNext();
        }
        mainCursor.close();
    }
    private static void fillSections(SQLiteDatabase db) {
        sections = new ArrayList<>();
        String query = "SELECT " +
                "Name " +
                "FROM Section " +
                "ORDER BY \"Order\"";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            sections.add(cursor.getString(cursor.getColumnIndex("Name")));
            cursor.moveToNext();
        }
        cursor.close();
    }

    public static Family getFamilyById(int id) {
        for(Family family: families) {
            if (family.getId() == id) return family;
        }
        return null;
    }
    public static FlowerColor getFlowerColorById(int id) {
        for(FlowerColor flowerColor: flowerColors) {
            if (flowerColor.getId() == id) return flowerColor;
        }
        return null;
    }
    public static Habitat getHabitatById(int id) {
        for(Habitat habitat: habitats) {
            if (habitat.getId() == id) return habitat;
        }
        return null;
    }
    public static LifeForm getLifeFormById(int id) {
        for(LifeForm lifeForm: lifeForms) {
            if (lifeForm.getId() == id) return lifeForm;
        }
        return null;
    }
    public static Value getValueById(int id) {
        for(Value value: values) {
            if (value.getId() == id) return value;
        }
        return null;
    }
}
