/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Bravo
 */
public class PlaceAPI {

    private static final String LOCATION_URL = "https://api.content.tripadvisor.com/api/v1/";
    private static final String API_KEY = "4BB0E403299542FEB3BF8DEB5EF58051";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?";
    private static final String Api_KEY = "86579c590b19855998f140dcf47071b5";
    HttpURLConnection connection = null;

    public Place getPlaceDetails(int id) throws Exception {
        String endpoint = String.format("location/%s/details?key=%s&language=en&currency=USD", id, API_KEY);
        String jsonResponse = getJsonResponse(LOCATION_URL + endpoint);
        return parsePlaceDetails(jsonResponse);
    }

    public List<Place> fetchWeatherInfo(String latitude, String longitude) throws Exception {
        String weatherEndpoint = String.format("lat=%s&lon=%s&appid=%s&lang=es&units=metric", latitude, longitude, Api_KEY);
        String weatherResponseBody = getJsonResponse(BASE_URL + weatherEndpoint);
        return parseWeatherInfo(weatherResponseBody);
    }

    public String getWeatherIconUrl(String iconId) {
        return String.format("https://openweathermap.org/img/wn/%s@2x.png", iconId);
    }

    private String getJsonResponse(String urlString) throws IOException {
        URL url = new URL(urlString);
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            return readInputStream(connection.getInputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error obteniendo la conexion " + e, "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private String readInputStream(InputStream inputStream) throws IOException {
        try (Scanner scanner = new Scanner(inputStream, "UTF-8")) {
            return scanner.useDelimiter("\\A").next();
        }
    }

    private Place parsePlaceDetails(String jsonResponse) {
        JSONObject jsonPlace = new JSONObject(jsonResponse);
        Place place = new Place();

        place.setName(jsonPlace.getString("name"));
        place.setAddress(jsonPlace.getJSONObject("address_obj").getString("address_string"));
        place.setCity(jsonPlace.getJSONObject("address_obj").optString("city", "Ciudad no disponible"));
        place.setPostal_code(jsonPlace.getJSONObject("address_obj").optInt("postalcode", 0));
        place.setLatitude(jsonPlace.getString("latitude"));
        place.setLongitude(jsonPlace.getString("longitude"));
        place.setTripAdvisor_link(jsonPlace.getString("web_url"));

        return place;
    }

    private List<Place> parseWeatherInfo(String weatherResponseBody) {
        List<Place> weatherList = new ArrayList<>();

        JSONObject weatherJsonResponse = new JSONObject(weatherResponseBody);
        JSONArray weatherArray = weatherJsonResponse.getJSONArray("weather");
        JSONObject weatherObject = weatherArray.getJSONObject(0);

        Place weatherPlace = new Place();
        weatherPlace.setDescription(weatherObject.getString("description"));
        weatherPlace.setIcon(weatherObject.getString("icon"));

        JSONObject mainObject = weatherJsonResponse.getJSONObject("main");
        weatherPlace.setTemperature(mainObject.getDouble("temp"));

        JSONObject windObject = weatherJsonResponse.getJSONObject("wind");
        weatherPlace.setSpeed(windObject.getDouble("speed"));

        weatherList.add(weatherPlace);

        return weatherList;
    }
}
