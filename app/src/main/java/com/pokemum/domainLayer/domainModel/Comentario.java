package com.pokemum.domainLayer.domainModel;

import java.util.Date;

/**
 * Created by qiaorui on 16/05/15.
 */
public class Comentario {

    private String id;
    private String text;
    private Date fecha;

    public Comentario(String id, String text, Date fecha) {
        this.id = id;
        this.text = text;
        this.fecha = fecha;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
