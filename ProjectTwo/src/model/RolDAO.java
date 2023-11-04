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


public class RolDAO {
     public RolDAO() {
    }

    public void create(Rol rol) {

        DBConnection db = new DBConnection();
        String consultaSQL = "INSERT INTO rol ( name) VALUES (?)";
        try {
            PreparedStatement ps = db.getConnection().prepareStatement(consultaSQL);
            ps.setString(1, rol.getName());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se insertó correctamente el rol");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se insertó correctamente el rol, error: " + e.toString());
        } finally {
            db.disconnect();
        }
    }

    public List<Rol> read() {

        DBConnection db = new DBConnection();
        List<Rol> rol = new ArrayList<>();
        String sql = "SELECT * FROM rol";

        try {
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                rol.add(new Rol(id,name));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            db.disconnect();
        }
        return rol;
    }

    public void update(Rol rol) {

        DBConnection db = new DBConnection();
        String consultaSQL = "UPDATE rol SET name=? WHERE id=?";

        try {
            PreparedStatement ps = db.getConnection().prepareStatement(consultaSQL);
            ps.setString(1, rol.getName());
            ps.setInt(2, rol.getId());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Modificación Exitosa");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se modificó, error:" + e.toString());
        }finally {
            db.disconnect();
        }
        
    }

    public void delete(int id) {

        DBConnection db = new DBConnection();

        String consultaSQL = "DELETE FROM rol WHERE id=?";

        try {
            PreparedStatement ps = db.getConnection().prepareStatement(consultaSQL);
            ps.setInt(1, id);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se eliminó correctamente el rol");

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "No se pudo eliminar, error: " + e.toString());
        }finally {
            db.disconnect();
        } 
    }
    
     public int getIDRol(String name) {
        int ID = 0;
        DBConnection db = new DBConnection();
        String sql = "SELECT id FROM rol WHERE name = ?";
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

    public String getNameRol(int id) {
        String name = "";
        DBConnection db = new DBConnection();
        String sql = "SELECT name FROM rol WHERE id = ?";
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
