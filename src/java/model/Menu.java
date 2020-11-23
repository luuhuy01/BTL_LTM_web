/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author luuhu
 */

public class Menu {
    private int id;
    private String name;
    private String url;
    private int idRoot;

    public Menu() {
    }

    public Menu(String name, String url, int idRoot) {
        this.name = name;
        this.url = url;
        this.idRoot = idRoot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIdRoot() {
        return idRoot;
    }

    public void setIdRoot(int idRoot) {
        this.idRoot = idRoot;
    }
    
}
