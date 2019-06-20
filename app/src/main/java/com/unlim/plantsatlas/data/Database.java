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

import java.util.ArrayList;
import java.util.List;

public class Database {
    private static List<Listable> families;
    private static List<Listable> flowerColors;
    private static List<Listable> habitats;
    private static List<Listable> lifeForms;
    private static List<Listable> values;
    private static List<Listable> plants;
    private static List<Listable> notFullPlants;
    private static List<Listable> sections;
    private static List<Listable> endangeredLists;

    public static List<Listable> getFamilies() { return families; }
    public static List<Listable> getFlowerColors() { return flowerColors; }
    public static List<Listable> getHabitats() { return habitats; }
    public static List<Listable> getLifeForms() { return lifeForms; }
    public static List<Listable> getValues() { return values; }
    public static List<Listable> getPlants() { return plants; }
    public static List<Listable> getSections() { return sections; }
    public static List<Listable> getEndangeredLists() { return endangeredLists; }
    public static List<Listable> getNotFullPlants() { return notFullPlants; }

    public static void fillMainData(SQLiteDatabase db) throws ClassNotFoundException {
        fillSections(db);
        fillFamilies(db);
        fillFlowerColors(db);
        fillHabitats(db);
        fillLifeForms(db);
        fillValues(db);
        fillPlants(db);
        fillEndangeredLists();
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

            String habitatQuery = "SELECT idHabitat FROM PlantHabitat WHERE idPlant = " + id;
            Cursor habitatCursor = db.rawQuery(habitatQuery, null);
            habitatCursor.moveToFirst();
            List<Habitat> habitats = new ArrayList<>();
            while (!habitatCursor.isAfterLast()) {
                int idHabitat = habitatCursor.getInt(habitatCursor.getColumnIndex("idHabitat"));
                habitats.add(getHabitatById(idHabitat));
                habitatCursor.moveToNext();
            }
            habitatCursor.close();

            plants.add(new Plant(id, rusName, latName, author, getFamilyById(idFamily), getLifeFormById(idLifeForm),
                    habitats, values, isEndangeredListSaratov, isEndangeredListRussia,
                    getFlowerColorById(idFlowerColor), mainPhotoFileName, additionalPhotos, text));
            mainCursor.moveToNext();
        }
        mainCursor.close();
    }
    private static void fillSections(SQLiteDatabase db) throws ClassNotFoundException {
        sections = new ArrayList<>();
        String query = "SELECT _id, " +
                "Name, " +
                "ClassName " +
                "FROM Section " +
                "ORDER BY \"Order\"";
        Cursor cursor = db.rawQuery(query, null);
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
}
