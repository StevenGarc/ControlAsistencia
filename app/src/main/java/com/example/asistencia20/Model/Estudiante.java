package com.example.asistencia20.Model;

import android.content.ContentValues;

public class Estudiante {
    private String Nombre;
    private String Correo;
    private String MAC;
    private String pass;
    private String id;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
    }
    public void setCorreo(String correo) {
        Correo = correo;
    }
    public void setMAC(String MAC) {
        this.MAC = MAC;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getCorreo() {
        return Correo;
    }
    public String getMAC() {
        return MAC;
    }
    public String getPass() {
        return pass;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(EstudianteDatos.estudianteEntry.ID, id);
        values.put(EstudianteDatos.estudianteEntry.CORREO, Correo);
        values.put(EstudianteDatos.estudianteEntry.MAC, MAC);
        values.put(EstudianteDatos.estudianteEntry.PASS, pass);
        values.put(EstudianteDatos.estudianteEntry.NOMBRE, Nombre);
        return values;
    }


}
