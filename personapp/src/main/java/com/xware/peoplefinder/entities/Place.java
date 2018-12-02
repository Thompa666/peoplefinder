package com.xware.peoplefinder.entities;

/**
 * Created by paul on 1/5/17.
 */


public class Place extends Contact{
    public Long id;
    public String name="";
    public String description="";
    public String address="";
    public String phone="";
    public String email="";
    public boolean checked;
    public Integer intId ;
    public Place(){};
    public Place(Long id, String content, String details) {
        this.id = id;
        this.name = content;
        this.description = details;
    }
    public Place(Long id, String name, String description, String address,String email,String phone) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address= address;
        this.email =email;
        this.phone=phone;

    }
    public Place(Long id, String name, String description, String address,String email,String phone,int intId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address= address;
        this.email =email;
        this.phone=phone;
        this.intId=intId;

    }
    @Override
    public String toString() {
        return id + " " + name + " " + description + " "+ checked;
    }
}
