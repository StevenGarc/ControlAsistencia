package com.example.asistencia20.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EstudianteHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Estudiante.db";

    public EstudianteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + EstudianteDatos.estudianteEntry.TABLE_NAME + " ("
                + EstudianteDatos.estudianteEntry._ID + " TEXT PRIMARY KEY AUTOINCREMENT,"
                + EstudianteDatos.estudianteEntry.ID + " TEXT NOT NULL,"
                + EstudianteDatos.estudianteEntry.NOMBRE + " TEXT NOT NULL,"
                + EstudianteDatos.estudianteEntry.CORREO + " TEXT NOT NULL,"
                + EstudianteDatos.estudianteEntry.MAC + " TEXT NOT NULL,"
                + EstudianteDatos.estudianteEntry.PASS + " TEXT NOT NULL)"
        );
    }

    public long guardarEstudiante(Estudiante estudiante) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(
                EstudianteDatos.estudianteEntry.TABLE_NAME,
                null,
                estudiante.toContentValues()
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
