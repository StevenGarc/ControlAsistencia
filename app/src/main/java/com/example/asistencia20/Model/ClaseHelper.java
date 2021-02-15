package com.example.asistencia20.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Estudiante.db";


    public ClaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE " + ClaseDatos.claseEntry.TABLE_NAME + " ("
                        + ClaseDatos.claseEntry._ID + " TEXT PRIMARY KEY AUTOINCREMENT,"
                        + ClaseDatos.claseEntry.ID + " TEXT NOT NULL,"
                        + ClaseDatos.claseEntry.NOMBRE + " TEXT NOT NULL)"
        );
        Clase c1= new Clase();
        c1.setId("1");
        c1.setMacSalon("F7:05:11:CA:E3:EF");
        c1.setNombre("Hackathon");
        guardarClase(c1);

        Clase c2= new Clase();
        c1.setId("2");
        c1.setMacSalon("C8:7D:3B:32:38:D6");
        c1.setNombre("Otra Clase");
        guardarClase(c2);

    }

    public long guardarClase(Clase clase) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(
                ClaseDatos.claseEntry.TABLE_NAME,
                null,
                clase.toContentValues()
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
