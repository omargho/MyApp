package com.omar.og.myapplication.toParse;

/**
 * Created by OG on 22/11/2015.
 */
public class Accomodation {
   public String type ;
    public String name ;
    public String lonng,lat,contact,id,city,adress;
    public String website;

    public Accomodation(String type, String name, String lonng, String lat, String contact, String id, String city, String adress, String website) {
        this.type = type;
        this.name = name;
        this.lonng = lonng;
        this.lat = lat;
        this.contact = contact;
        this.id = id;
        this.city = city;
        this.adress = adress;
        this.website = website;
    }
}
