
package model;
import java.util.Date;

public class Reservation {
    private int id;
    private String user_name;
    private Date date;
    private int quantity;
    private int event_id;
    
    public Reservation (int id, Date date, int quantity, int event_id){
     
       this.id = id;
       this.user_name = user_name;
       this.date = date;
       this.quantity = quantity;
       this.event_id = event_id;
    
    }


    public int getId() {
        return id;
    }

    
    public void setId(int id) {
        this.id = id;
    }

  
    public String getUser_name() {
        return user_name;
    }

   
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    
    public Date getDate() {
        return date;
    }

   
    public void setDate(Date date) {
        this.date = date;
    }

   
    public int getQuantity() {
        return quantity;
    }

   
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
    public int getEvent_id() {
        return event_id;
    }

  
    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
}
