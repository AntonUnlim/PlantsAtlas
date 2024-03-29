package com.unlim.plantsatlas.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.unlim.plantsatlas.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH;
    private static String DB_NAME = "db.db";
    private static final int DB_VERSION = 11;

    private Context context;
    private boolean isNeedToUpdate = false;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        if (Build.VERSION.SDK_INT >= 24) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        copyDatabase();
        this.getReadableDatabase();
    }

    public void updateDatabase() throws IOException {
        if (isNeedToUpdate) {
            File file = new File(DB_PATH + DB_NAME);
            if (file.exists()) {
                file.delete();
            }
            copyDatabase();
            isNeedToUpdate = false;
        }
    }

    private boolean checkDatabase() {
        File file = new File(DB_PATH + DB_NAME);
        return file.exists();
    }

    private void copyDatabase() {
        if(!checkDatabase()) {
            this.getReadableDatabase();
            try {
                copyDBFile();
            } catch (IOException e) {
                throw new Error("Error copy database");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream inputStream = context.getResources().openRawResource(R.raw.db);
        OutputStream outputStream = new FileOutputStream(DB_PATH + DB_NAME);
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) { }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) isNeedToUpdate = true;
    }
}
