package com.pokemum.domainLayer.domainModel;

/**
 * Created by qiaorui on 16/05/15.
 */
public class Sala {

    private String nombre;
    private int maxY;
    private int maxX;

    public Sala(String nombre) {
        this.nombre = nombre;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }
}
