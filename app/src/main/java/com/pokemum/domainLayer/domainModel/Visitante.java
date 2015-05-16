package com.pokemum.domainLayer.domainModel;

/**
 * Created by qiaorui on 16/05/15.
 */
public class Visitante extends Usuario {

    private Sexo sexo;
    private String enlaceRedSocial;
    private String fechaDeNacimiento;

    public Visitante(String username, String password, String name) {
        super(username, password, name);
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getEnlaceRedSocial() {
        return enlaceRedSocial;
    }

    public void setEnlaceRedSocial(String enlaceRedSocial) {
        this.enlaceRedSocial = enlaceRedSocial;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }
}
