/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.IOException;
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
    HttpURLConnection connection = null;

    public List<Event> searchEvents(String name, String location, String category) throws Exception {
        String encodedLocation = URLEncoder.encode(location, "UTF-8");
        String encodedname = URLEncoder.encode(name, "UTF-8");
        String endpoint = String.format("?key=%s&searchQuery=%s&address=%s&category=%s&language=en", API_KEY, encodedname, encodedLocation, category);
        return fetchEvents(endpoint);
    }

    private List<Event> fetchEvents(String endpoint) throws Exception {
        List<Event> events = new ArrayList<>();
        String jsonResponse = getJsonResponse(SEARCH_URL + endpoint);
        if (jsonResponse != null) {
            JSONObject jsonObj = new JSONObject(jsonResponse);
            JSONArray data = jsonObj.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                JSONObject eventObj = data.getJSONObject(i);
                events.add(parseEvent(eventObj));
            }
        }
        return events;
    }

    public List<String> getEventImages(int id) throws Exception {
        List<String> imageUrls = new ArrayList<>();
        String endpoint = "location/" + id + "/photos?key=4BB0E403299542FEB3BF8DEB5EF58051&language=en";
        String jsonResponse = getJsonResponse(LOCATION_URL + endpoint);
        if (jsonResponse != null) {
            JSONObject jsonObj = new JSONObject(jsonResponse);
            JSONArray photoData = jsonObj.getJSONArray("data");

            for (int i = 0; i < photoData.length(); i++) {
                JSONObject photo = photoData.getJSONObject(i);
                JSONObject images = photo.getJSONObject("images");
                JSONObject smallImg = images.getJSONObject("medium");
                String imageUrl = smallImg.getString("url");
                imageUrls.add(imageUrl);
            }
        }
        return imageUrls;
    }

    public Event getEventDetails(int id) throws Exception {
        Event event = new Event();
        String endpoint = String.format("location/%s/details?key=%s&language=en&currency=USD", id, API_KEY);
        String jsonResponse = getJsonResponse(LOCATION_URL + endpoint);
        if (jsonResponse != null) {
            JSONObject jsonObj = new JSONObject(jsonResponse);
            event.setDescription(jsonObj.optString("description", "Descripcion no disponible"));
            event.setNumReviews(jsonObj.optString("num_reviews"));
            event.setRatingImageUrl(jsonObj.optString("rating_image_url"));
        }
        return event;
    }

    private String getJsonResponse(String urlString) throws IOException {
        URL url = new URL(urlString);
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            return readInput(connection.getInputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error obteniendo la conexion " + e, "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private String readInput(InputStream input) throws IOException {
        try (Scanner scanner = new Scanner(input, "UTF-8")) {
            return scanner.useDelimiter("\\A").next();
        }
    }

    private Event parseEvent(JSONObject eventObj) {
        Event event = new Event();
        event.setLocationId(eventObj.getInt("location_id"));
        event.setName(eventObj.getString("name"));
        event.setAddress(eventObj.getJSONObject("address_obj").getString("address_string"));
        event.setCity(eventObj.optString("city", "Ciudad no disponible"));
        event.setPostal_code(eventObj.optInt("postalcode", 0));
        return event;
    }
}
