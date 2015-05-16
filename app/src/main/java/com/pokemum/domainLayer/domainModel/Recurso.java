package com.pokemum.domainLayer.domainModel;

/**
 * Created by qiaorui on 16/05/15.
 */
public class Recurso {

    private String id;
    private String title;
    private String url;

    public Recurso(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
