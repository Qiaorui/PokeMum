package com.pokemum.domainLayer.domainModel;

/**
 * Created by qiaorui on 16/05/15.
 */
public class Video extends Recurso {

    private int duracion;

    public Video(String id, String title) {
        super(id, title);
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
}
