
package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;


public class UserDAO {

    public void create(User user) {
        DBConnection db = new DBConnection();
        String consultaSQL = "INSERT INTO users (id_number, name, last_name, birth_date, email, phone_number , password, rol_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = db.getConnection().prepareStatement(consultaSQL);
            ps.setInt(1, user.getID_number());
            ps.setString(2, user.getName());
            ps.setString(3, user.getLast_name());
            ps.setDate(4, new java.sql.Date(user.getBirth_date().getTime()));
            ps.setString(5, user.getEmail());
            ps.setInt(6, user.getPhone_number());
            ps.setString(7, user.getPassword());
            ps.setInt(8, user.getRol_id());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se insertó correctamente el usuario");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No Se insertó correctamente el usuario, error: " + e.toString());
        } finally {
            db.disconnect();
        }
    }

    public List<User> read() {

        DBConnection db = new DBConnection();
        List<User> user = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try {
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int id_number = Integer.parseInt(resultSet.getString("id_number"));
                String name = resultSet.getString("name");
                String last_name = resultSet.getString("last_name");
                Date birth_date = resultSet.getDate("birth_date");
                String email = resultSet.getString("email");
                int phone_number = Integer.parseInt(resultSet.getString("phone_number"));
                String password = resultSet.getString("password");
                int rol_id = Integer.parseInt(resultSet.getString("rol_id"));
                user.add(new User(id, id_number, name, last_name, birth_date, email, phone_number, password, rol_id));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            db.disconnect();
        }
        return user;
    }

    public void update(User user) {

        DBConnection db = new DBConnection();

        String consultaSQL = "UPDATE users SET id_number=?, name=?, last_name=?, birth_date=?, email=?, phone_number=?, password=?, rol_id=? WHERE id=?";

        try {
            PreparedStatement ps = db.getConnection().prepareStatement(consultaSQL);
            ps.setInt(1, user.getID_number());
            ps.setString(2, user.getName());
            ps.setString(3, user.getLast_name());
            ps.setDate(4, new java.sql.Date(user.getBirth_date().getTime()));
            ps.setString(5, user.getEmail());
            ps.setInt(6, user.getPhone_number());
            ps.setString(7, user.getPassword());
            ps.setInt(8, user.getRol_id());
            ps.setInt(9, user.getId());
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

        String consultaSQL = "DELETE FROM users WHERE id=?";

        try {
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(consultaSQL);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Se eliminó correctamente el rol");

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "No se pudo eliminar, error: " + e.toString());
        } finally {
            db.disconnect();
        }
    }

    public int getIDUser(String name) {
        int ID = 0;
        DBConnection db = new DBConnection();
        String sql = "SELECT id FROM users WHERE name = ?";
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

    public String getNameUser(int id) {
        String name = "";
        DBConnection db = new DBConnection();
        String sql = "SELECT name FROM users WHERE id = ?";
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
