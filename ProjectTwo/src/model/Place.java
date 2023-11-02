
package model;


public class Place {
    private int id;
    private String name;
    private String address;
    private String city;
    private int postal_code;
    private String latitude;
    private String longitude;
    private String tripAdvisor_link;
    
    public Place(int id, String name, String address, String city, int postal_code, String latitude, String longitude, String tripAdvisor_link){
    this.id = id;
    this.name = name;
    this.address = address;
    this.city = city;
    this.postal_code = postal_code;
    this.latitude = latitude;
    this.longitude = longitude;
    this.tripAdvisor_link = tripAdvisor_link;
  
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

   
    public String getLatitude() {
        return latitude;
    }

   
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    
    public String getLongitude() {
        return longitude;
    }

    
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

   
    public String getTripAdvisor_link() {
        return tripAdvisor_link;
    }

    public void setTripAdvisor_link(String tripAdvisor_link) {
        this.tripAdvisor_link = tripAdvisor_link;
    }
}
