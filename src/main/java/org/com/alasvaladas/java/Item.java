/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.com.alasvaladas.java;

/**
 *
 * @author Arnaud
 */
public class Item {
    private final String _nom;    
    private final String _prix_unit;
    private final String _img_url;
    private final String _compo;
    private final String _desc;
    
    public Item(){
        this._nom = null;
        this._prix_unit = null;
        this._img_url = null;
        this._compo = null;
        this._desc = null;
    }
    
    public Item(String nom, String prix_unit, String img_url, String compo, String desc){
        this._nom = nom;
        this._prix_unit = prix_unit;
        this._img_url = img_url;
        this._compo = compo;
        this._desc = desc;
    }

    @Override
    public String toString(){
        return this._nom + ", " + this._prix_unit + ", " + this._img_url + ", " + this._compo + ", " + this._desc;
    }
}
