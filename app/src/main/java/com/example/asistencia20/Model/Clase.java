package com.example.asistencia20.Model;

import android.content.ContentValues;

public class Clase {
    private String MacSalon;
    private String Nombre;
    private String Id;

    public Clase(String Mac, String nombre, String id){
        this.Id=id;
        this.MacSalon=Mac;
        this.Nombre=nombre;
    }
    public Clase(){}
    public String getMacSalon() {
        return MacSalon;
    }
    public void setMacSalon(String macSalon) {
        MacSalon = macSalon;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
    }
    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(ClaseDatos.claseEntry.ID, Id);
        values.put(ClaseDatos.claseEntry.MACSALON, MacSalon );
        values.put(ClaseDatos.claseEntry.NOMBRE, Nombre);
        return values;
    }
}
