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
public class EventDAO {

    public void create(Event event) {
        DBConnection db = new DBConnection();
        String consultaSQL = "INSERT INTO events (name, description, date, address, city, postal_code, price, room, place_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = db.getConnection().prepareStatement(consultaSQL);
            ps.setString(1, event.getName());
            ps.setString(2, event.getDescription());
            if (event.getDate() != null) {
                ps.setDate(3, new java.sql.Date(event.getDate().getTime()));
            } else {
                ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            }
            ps.setString(4, event.getAddress());
            ps.setString(5, event.getCity());
            ps.setInt(6, event.getPostal_code());
            ps.setDouble(7, event.getPrice());
            ps.setInt(8, event.getRoom());
            ps.setInt(9, event.getPlace_id());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se insertó correctamente el evento");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No Se insertó correctamente el evento, error: " + e.toString());
        } finally {
            db.disconnect();
        }
    }

    public List<Event> read() {

        DBConnection db = new DBConnection();
        List<Event> event = new ArrayList<>();
        String sql = "SELECT * FROM events";

        try {
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Date date = resultSet.getDate("date");
                String address = resultSet.getString("address");
                String city = resultSet.getString("city");
                int postal_code = Integer.parseInt(resultSet.getString("postal_code"));
                double price = Double.parseDouble(resultSet.getString("price"));
                int room = Integer.parseInt(resultSet.getString("room"));
                int place_id = Integer.parseInt(resultSet.getString("district_id"));
                event.add(new Event(id, name, description, date, address, city, postal_code, price, room, place_id));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            db.disconnect();
        }
        return event;
    }

    public void update(Event event) {

        DBConnection db = new DBConnection();

        String consultaSQL = "UPDATE events SET name=?, description=?, date=?, address=?, city=?, postal_code=?, price=?, room=?, place_id=? WHERE id=?";

        try {
            PreparedStatement ps = db.getConnection().prepareStatement(consultaSQL);
            ps.setString(1, event.getName());
            ps.setString(2, event.getDescription());
            ps.setDate(3, new java.sql.Date(event.getDate().getTime()));
            ps.setString(4, event.getAddress());
            ps.setString(5, event.getCity());
            ps.setInt(6, event.getPostal_code());
            ps.setDouble(7, event.getPrice());
            ps.setInt(8, event.getRoom());
            ps.setInt(9, event.getPlace_id());
            ps.setInt(10, event.getId());
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

        String consultaSQL = "DELETE FROM events WHERE id=?";

        try {
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(consultaSQL);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Se eliminó correctamente el evento");

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "No se pudo eliminar, error: " + e.toString());
        } finally {
            db.disconnect();
        }
    }

    public int getIDEvents(String name) {
        int ID = 0;
        DBConnection db = new DBConnection();
        String sql = "SELECT id FROM events WHERE name = ?";
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

    public String getNameEvents(int id) {
        String name = "";
        DBConnection db = new DBConnection();
        String sql = "SELECT name FROM events WHERE id = ?";
        try {
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                name = resultSet.getString("name");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            db.disconnect();
        }
        return name;
    }
}
