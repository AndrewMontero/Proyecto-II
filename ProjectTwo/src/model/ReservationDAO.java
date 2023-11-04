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
public class ReservationDAO {

    public void create(Reservation reservation) {

        DBConnection db = new DBConnection();
        String consultaSQL = "INSERT INTO reservations (user_name, date, quantity, event_id) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = db.getConnection().prepareStatement(consultaSQL);
            ps.setString(1, reservation.getUser_name());
            ps.setDate(2, new java.sql.Date(reservation.getDate().getTime()));
            ps.setInt(3, reservation.getQuantity());
            ps.setInt(4, reservation.getEvent_id());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se insertó correctamente la reservación");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se insertó correctamente la reservación, error: " + e.toString());
        } finally {
            db.disconnect();
        }
    }

    public List<Reservation> read() {

        DBConnection db = new DBConnection();
        List<Reservation> reservation = new ArrayList<>();
        String sql = "SELECT * FROM reservations";

        try {
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String user_name = resultSet.getString("user_name");
                Date date = resultSet.getDate("date");
                int quantity = resultSet.getInt("quantity");
                int event_id = resultSet.getInt("event_id");
                reservation.add(new Reservation(id, user_name, date, quantity, event_id));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            db.disconnect();
        }
        return reservation;
    }

    public void update(Reservation reservation) {

        DBConnection db = new DBConnection();
        String consultaSQL = "UPDATE reservations SET user_name=?, date=?, quantity=?, event_id=? WHERE id=?";

        try {
            PreparedStatement ps = db.getConnection().prepareStatement(consultaSQL);
            ps.setString(1, reservation.getUser_name());
            ps.setDate(2, new java.sql.Date(reservation.getDate().getTime()));
            ps.setInt(3, reservation.getQuantity());
            ps.setInt(4, reservation.getEvent_id());
            ps.setInt(5, reservation.getId());
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

        String consultaSQL = "DELETE FROM reservations WHERE id=?";

        try {
            PreparedStatement ps = db.getConnection().prepareStatement(consultaSQL);
            ps.setInt(1, id);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se eliminó correctamente la reservación");

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "No se pudo eliminar, error: " + e.toString());
        } finally {
            db.disconnect();
        }
    }
    
     public int getIDReservation(String name) {
        int ID = 0;
        DBConnection db = new DBConnection();
        String sql = "SELECT id FROM reservations WHERE name = ?";
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

    public String getNameReservation(int id) {
        String name = "";
        DBConnection db = new DBConnection();
        String sql = "SELECT name FROM reservations WHERE id = ?";
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