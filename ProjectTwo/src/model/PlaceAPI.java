/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

/**
 *
 * @author Bravo
 */
public class PlaceAPI {

    private static final String LOCATION_URL = "https://api.content.tripadvisor.com/api/v1/";
    private static final String API_KEY = "4BB0E403299542FEB3BF8DEB5EF58051";

    public Place getPlaceDetails(int id) throws Exception {
        Place place = new Place();
        String endpoint = String.format("location/%s/details?key=%s&language=en&currency=USD", id, API_KEY);
        URL url = new URL(LOCATION_URL + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        InputStream in = connection.getInputStream();
        String responseBody = new Scanner(in, "UTF-8").useDelimiter("\\A").next();
        JSONObject jsonResponse = new JSONObject(responseBody);
        place.setName(jsonResponse.getString("name"));
        place.setAddress(jsonResponse.getJSONObject("address_obj").getString("address_string"));
        if (jsonResponse.has("city") && !jsonResponse.isNull("city")) {
            place.setCity(jsonResponse.getString("city"));
        } else {
            place.setCity("Ciudad no disponible");
        }
        if (jsonResponse.has("postalcode") && !jsonResponse.isNull("postalcode")) {
            place.setPostal_code(jsonResponse.getInt("postalcode"));
        } else {
            place.setPostal_code(0);
        }
        place.setLatitude(jsonResponse.getString("latitude"));
        place.setLongitude(jsonResponse.getString("longitude"));
        place.setTripAdvisor_link(jsonResponse.getString("web_url"));
        return place;
    }
}
