package com.unlim.plantsatlas.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unlim.plantsatlas.main.Category;
import com.unlim.plantsatlas.main.Family;
import com.unlim.plantsatlas.main.FlowerColor;
import com.unlim.plantsatlas.main.Habitat;
import com.unlim.plantsatlas.main.LifeForm;
import com.unlim.plantsatlas.main.Value;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private static List<Family> families;
    private static List<FlowerColor> flowerColors;
    private static List<Habitat> habitats;
    private static List<LifeForm> lifeForms;
    private static List<Value> values;

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

    public static void fillMainData(SQLiteDatabase db) {
        fillFamilies(db);
        fillFlowerColors(db);
        fillHabitats(db);
        fillLifeForms(db);
        fillValues(db);
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

    /*private static void fillCategories(SQLiteDatabase db, List<Category> list, String category) {
        String query = "SELECT _id, " +
                "Name, " +
                "Image " +
                "FROM " + category + " " +
                "ORDER BY \"Order\"";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String imageFile = cursor.getString(2);

            cursor.moveToNext();
        }
        cursor.close();
    }*/
}
