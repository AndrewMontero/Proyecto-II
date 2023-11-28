/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Bravo
 */
public class EventAPI {

    private static final String SEARCH_URL = "https://api.content.tripadvisor.com/api/v1/location/search";
    private static final String LOCATION_URL = "https://api.content.tripadvisor.com/api/v1/";
    private static final String API_KEY = "4BB0E403299542FEB3BF8DEB5EF58051";

    public List<Event> searchEvents(String name, String location, String category) throws Exception {
        String encodedLocation = URLEncoder.encode(location, "UTF-8");
        String encodedname = URLEncoder.encode(name, "UTF-8");
        String endpoint = String.format("?key=%s&searchQuery=%s&address=%s&category=%s&language=en", API_KEY, encodedname, encodedLocation, category);
        return fetchEvents(endpoint);
    }

    private List<Event> fetchEvents(String endpoint) throws Exception {
        List<Event> events = new ArrayList<>();

        URL url = new URL(SEARCH_URL + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        InputStream in = connection.getInputStream();
        String responseBody = new Scanner(in, "UTF-8").useDelimiter("\\A").next();

        JSONObject jsonResponse = new JSONObject(responseBody);
        JSONArray data = jsonResponse.getJSONArray("data");

        for (int i = 0; i < data.length(); i++) {
            JSONObject eventObj = data.getJSONObject(i);
            Event event = new Event();
            event.setLocationId(eventObj.getInt("location_id"));
            event.setName(eventObj.getString("name"));
            event.setAddress(eventObj.getJSONObject("address_obj").getString("address_string"));
            if (eventObj.has("city") && !eventObj.isNull("city")) {
                event.setCity(eventObj.getString("city"));
            } else {
                event.setCity("Ciudad no disponible");
            }
            if (eventObj.has("postalcode") && !eventObj.isNull("postalcode")) {
                event.setPostal_code(eventObj.getInt("postalcode"));
            } else {
                event.setPostal_code(0);
            }
            events.add(event);
        }

        return events;
    }

    public List<String> getEventImages(int id) throws Exception {
        List<String> imageUrls = new ArrayList<>();

        String endpoint = "location/" + id + "/photos?key=4BB0E403299542FEB3BF8DEB5EF58051&language=en";
        URL url = new URL(LOCATION_URL + endpoint);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        InputStream in = connection.getInputStream();
        String responseBody = new Scanner(in, "UTF-8").useDelimiter("\\A").next();

        JSONObject jsonResponse = new JSONObject(responseBody);
        JSONArray photoData = jsonResponse.getJSONArray("data");

        for (int i = 0; i < photoData.length(); i++) {
            JSONObject photo = photoData.getJSONObject(i);
            JSONObject images = photo.getJSONObject("images");
            JSONObject smallImg = images.getJSONObject("medium");
            String imageUrl = smallImg.getString("url");
            imageUrls.add(imageUrl);
        }

        return imageUrls;
    }

    public Event getEventDetails(int id) throws Exception {
        Event event = new Event();
        String endpoint = String.format("location/%s/details?key=%s&language=en&currency=USD", id, API_KEY);
        URL url = new URL(LOCATION_URL + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        InputStream in = connection.getInputStream();
        String responseBody = new Scanner(in, "UTF-8").useDelimiter("\\A").next();
        JSONObject jsonResponse = new JSONObject(responseBody);
        if (jsonResponse.has("description") && !jsonResponse.isNull("description")) {
            event.setDescription(jsonResponse.getString("description"));
        } else {
            event.setDescription("Descripcion no disponible");
        }
        event.setNumReviews(jsonResponse.getString("num_reviews"));
        event.setRatingImageUrl(jsonResponse.getString("rating_image_url"));
        return event;
    }
}
