package com.pokemum.domainLayer.domainModel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by qiaorui on 16/05/15.
 */
public class Autor {

    private String nombre;
    private String bibliografia;
    private Date fechaDeNacimiento;
    private Date fechaDeDefuncion;
    private String ciudadDeNacimiento;
    private String ciudadDeDefuncion;
    private ArrayList<Estilo> estilos;
    private String ramaArtistico;

    public Autor(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBibliografia() {
        return bibliografia;
    }

    public void setBibliografia(String bibliografia) {
        this.bibliografia = bibliografia;
    }

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Date getFechaDeDefuncion() {
        return fechaDeDefuncion;
    }

    public void setFechaDeDefuncion(Date fechaDeDefuncion) {
        this.fechaDeDefuncion = fechaDeDefuncion;
    }

    public String getCiudadDeNacimiento() {
        return ciudadDeNacimiento;
    }

    public void setCiudadDeNacimiento(String ciudadDeNacimiento) {
        this.ciudadDeNacimiento = ciudadDeNacimiento;
    }

    public String getCiudadDeDefuncion() {
        return ciudadDeDefuncion;
    }

    public void setCiudadDeDefuncion(String ciudadDeDefuncion) {
        this.ciudadDeDefuncion = ciudadDeDefuncion;
    }

    public ArrayList<Estilo> getEstilos() {
        return estilos;
    }

    public void setEstilos(ArrayList<Estilo> estilos) {
        this.estilos = estilos;
    }

    public String getRamaArtistico() {
        return ramaArtistico;
    }

    public void setRamaArtistico(String ramaArtistico) {
        this.ramaArtistico = ramaArtistico;
    }
}
