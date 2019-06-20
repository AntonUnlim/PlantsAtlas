package com.unlim.plantsatlas.data;

public class SqlQuery {
    public static String family =
            "SELECT _id, " +
            "RusName, " +
            "LatName " +
            "FROM Family " +
            "ORDER BY RusName";
    public static String flowerColor =
            "SELECT _id, " +
            "Name, " +
            "IFNULL(Red, 255) AS Red, " +
            "IFNULL(Green, 255) AS Green, " +
            "IFNULL(Blue, 255) AS Blue " +
            "FROM FlowerColor ORDER BY \"Order\"";
    public static String habitat =
            "SELECT _id, " +
            "Name, " +
            "Image " +
            "FROM Habitat " +
            "ORDER BY \"Order\"";
    public static String lifeForm =
            "SELECT _id, " +
            "Name, " +
            "Image " +
            "FROM LifeForm " +
            "ORDER BY \"Order\"";
    public static String value =
            "SELECT _id, " +
            "Name, " +
            "Image " +
            "FROM Value " +
            "ORDER BY \"Order\"";
    public static String plantMain =
            "SELECT " +
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
    public static String plantValue =
            "SELECT " +
            "idValue " +
            "FROM " +
            "PlantValue " +
            "WHERE " +
            "idPlant = ";
    public static String plantPhoto =
            "SELECT " +
            "p.fileName " +
            "FROM " +
            "PlantPhoto pp " +
            "JOIN Photo p ON pp.idPhoto = p._id " +
            "WHERE " +
            "isMainPhoto = 0 AND idPlant = ";
    public static String plantHabitat =
            "SELECT " +
            "idHabitat " +
            "FROM " +
            "PlantHabitat " +
            "WHERE " +
            "idPlant = ";
    public static String section =
            "SELECT _id, " +
            "Name, " +
            "ClassName " +
            "FROM Section " +
            "ORDER BY \"Order\"";

}
