package com.pokemum.domainLayer.domainModel;

/**
 * Created by qiaorui on 16/05/15.
 */
public class Session {

    private static volatile Session instance;
    private String usuarioActual;
    private int permisoDeUsuario;

    /**
     * Session
     * Crea una instancia de la clase de Session
     */
    private Session() {
    }

    /**
     * getInstance
     * Obtiene la instancia de la clase de Session
     * @return La instancia de la clase de Session
     */
    public static Session getInstance() {
        if (instance == null) {
            synchronized (Session.class) {
                if (instance == null) {
                    instance = new Session();
                }
            }
        }
        return instance;
    }

    public String getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(String usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public int getPermisoDeUsuario() {
        return permisoDeUsuario;
    }

    public void setPermisoDeUsuario(int permisoDeUsuario) {
        this.permisoDeUsuario = permisoDeUsuario;
    }
}