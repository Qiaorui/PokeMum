package com.pokemum.domainLayer.domainModel;

/**
 * Created by qiaorui on 16/05/15.
 */
public class Obra {

    private String id;
    private String titulo;
    private Estilo estiloArtistico;
    private String descripcion;
    private int dataDeCreacion;
    private int dataDeAdquisicion;
    private String periodoHistorico;
    private TipoDeObra tipo;
    private int posicionX;
    private int posicionY;

    public Obra(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Estilo getEstiloArtistico() {
        return estiloArtistico;
    }

    public void setEstiloArtistico(Estilo estiloArtistico) {
        this.estiloArtistico = estiloArtistico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDataDeCreacion() {
        return dataDeCreacion;
    }

    public void setDataDeCreacion(int dataDeCreacion) {
        this.dataDeCreacion = dataDeCreacion;
    }

    public int getDataDeAdquisicion() {
        return dataDeAdquisicion;
    }

    public void setDataDeAdquisicion(int dataDeAdquisicion) {
        this.dataDeAdquisicion = dataDeAdquisicion;
    }

    public String getPeriodoHistorico() {
        return periodoHistorico;
    }

    public void setPeriodoHistorico(String periodoHistorico) {
        this.periodoHistorico = periodoHistorico;
    }

    public TipoDeObra getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeObra tipo) {
        this.tipo = tipo;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }
}
