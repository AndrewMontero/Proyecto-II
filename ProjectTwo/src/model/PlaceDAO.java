/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author 50672
 */
public class PlaceDAO {
     public void create(Place place) {
        DBConnection db = new DBConnection();
        String consultaSQL = "INSERT INTO places (name, address, city, postal_code, latitude, longitude , tripAdvisor_link) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = db.getConnection().prepareStatement(consultaSQL);
            ps.setString(1, place.getName());
            ps.setString(2, place.getAddress());
            ps.setString(3, place.getCity());
            ps.setInt(4, place.getPostal_code());
            ps.setString(5, place.getLatitude());
            ps.setString(6, place.getLongitude());
            ps.setString(7, place.getTripAdvisor_link());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se insertó correctamente el lugar");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No Se insertó correctamente el lugar, error: " + e.toString());
        } finally {
            db.disconnect();
        }
    }

    public List<Place> read() {

        DBConnection db = new DBConnection();
        List<Place> place = new ArrayList<>();
        String sql = "SELECT * FROM places";

        try {
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String city = resultSet.getString("city");
                int postal_code = Integer.parseInt(resultSet.getString("postal_code"));
                String latitude = resultSet.getString("latitude");
                String longitude = resultSet.getString("longitude");
                String tripAdvisor_link = resultSet.getString("tripAdvisor_link");
                place.add(new Place(id, name, address, city, postal_code, latitude, longitude, tripAdvisor_link));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            db.disconnect();
        }
        return place;
    }

    public void update(Place place) {

        DBConnection db = new DBConnection();

        String consultaSQL = "UPDATE places SET name=?, address=?, city=?, postal_code=?, latitude=?, longitude=?, tripAdvisor_link=? WHERE id=?";

        try {
            PreparedStatement ps = db.getConnection().prepareStatement(consultaSQL);
            ps.setString(1, place.getName());
            ps.setString(2, place.getAddress());
            ps.setString(3, place.getCity());
            ps.setInt(4, place.getPostal_code());
            ps.setString(5, place.getLatitude());
            ps.setString(6, place.getLongitude());
            ps.setString(7, place.getTripAdvisor_link());
            ps.setInt(8, place.getId());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Modificación Exitosa");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se modificó, error:" + e.toString());
        } finally {
            db.disconnect();
        }
    }

    public void delete(int id) {

        DBConnection db = new DBConnection();

        String consultaSQL = "DELETE FROM places WHERE id=?";

        try {
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(consultaSQL);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Se eliminó correctamente el lugar");

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "No se pudo eliminar, error: " + e.toString());
        } finally {
            db.disconnect();
        }
    }

    public int getIDPlaces(String name) {
        int ID = 0;
        DBConnection db = new DBConnection();
        String sql = "SELECT id FROM places WHERE name = ?";
        try {
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                ID = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            db.disconnect();
        }
        return ID;
    }

    public String getNamePlaces(int id) {
        String name = "";
        DBConnection db = new DBConnection();
        String sql = "SELECT city FROM places WHERE id = ?";
        try {
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                name = resultSet.getString("city");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            db.disconnect();
        }
        return name;
    }
}
