/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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

    public List<Place> fetchWeatherInfo(String latitude, String longitude) throws Exception {
        List<Place> weatherInfoList = new ArrayList<>();

        // Construir la URL para la API de clima utilizando la latitud y longitud
        String weatherEndpoint = String.format("lat=%s&lon=%s&appid=%s&lang=es&units=metric", latitude, longitude, Api_KEY);
        URL weatherUrl = new URL(BASE_URL + weatherEndpoint);

        HttpURLConnection weatherConnection = (HttpURLConnection) weatherUrl.openConnection();
        weatherConnection.setRequestMethod("GET");
        weatherConnection.connect();

        InputStream weatherInputStream = weatherConnection.getInputStream();
        String weatherResponseBody = new Scanner(weatherInputStream, "UTF-8").useDelimiter("\\A").next();

        JSONObject weatherJsonResponse = new JSONObject(weatherResponseBody);

        // Extraer la información del clima del objeto JSON y crear un objeto Place para almacenarla
        JSONArray weatherArray = weatherJsonResponse.getJSONArray("weather");
        JSONObject weatherObject = weatherArray.getJSONObject(0);
        String description = weatherObject.getString("description");
        String icon = weatherObject.getString("icon");

        JSONObject mainObject = weatherJsonResponse.getJSONObject("main");
        Double temperature = mainObject.getDouble("temp");

        JSONObject windObject = weatherJsonResponse.getJSONObject("wind");
        Double speed = windObject.getDouble("speed");

        // Crear un objeto Place para almacenar la información del clima
        Place weatherPlace = new Place();
        weatherPlace.setDescription(description);
        weatherPlace.setIcon(icon);
        weatherPlace.setTemperature(temperature);
        weatherPlace.setSpeed(speed);

        // Agregar el objeto Place a la lista de información del clima
        weatherInfoList.add(weatherPlace);

        return weatherInfoList;
    }

    public String getWeatherIconUrl(String iconId) {
        return String.format("https://openweathermap.org/img/wn/%s@2x.png", iconId);
    }
}
