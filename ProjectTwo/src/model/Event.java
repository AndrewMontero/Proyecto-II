package model;

import java.util.Date;

public class Event {

    private int id;
    private String name;
    private String description;
    private Date date;
    private String address;
    private String city;
    private int postal_code;
    private double price;
    private int room;
    private int place_id;

    public Event() {
    }

    public Event(String name, String description, Date date, String address, String city, int postal_code, double price, int room, int place_id) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.address = address;
        this.city = city;
        this.postal_code = postal_code;
        this.price = price;
        this.room = room;
        this.place_id = place_id;
    }

    public Event(int id, String name, String description, Date date, String address, String city, int postal_code, double price, int room, int place_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.address = address;
        this.city = city;
        this.postal_code = postal_code;
        this.price = price;
        this.room = room;
        this.place_id = place_id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(int postal_code) {
        this.postal_code = postal_code;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getPlace_id() {
        return place_id;
    }

    public void setPlace_id(int place_id) {
        this.place_id = place_id;
    }
}
