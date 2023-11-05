package model;

import java.util.Date;

public class User {

    private int id;
    private int ID_number;
    private String name;
    private String last_name;
    private Date birth_date;
    private String email;
    private int phone_number;
    private String password;
    private int rol_id;

    public User() {
    }

    public User(int ID_number, String name, String last_name, Date birth_date, String email, int phone_number, String password, int rol_id) {
        this.ID_number = ID_number;
        this.name = name;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.email = email;
        this.phone_number = phone_number;
        this.password = password;
        this.rol_id = rol_id;
    }

    public User(int id, int ID_number, String name, String last_name, Date birth_date, String email, int phone_number, String password, int rol_id) {
        this.id = id;
        this.ID_number = ID_number;
        this.name = name;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.email = email;
        this.phone_number = phone_number;
        this.password = password;
        this.rol_id = rol_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getID_number() {
        return ID_number;
    }

    public void setID_number(int ID_number) {
        this.ID_number = ID_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRol_id() {
        return rol_id;
    }

    public void setRol_id(int rol_id) {
        this.rol_id = rol_id;
    }

}
