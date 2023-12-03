package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.RolDAO;
import model.User;
import model.UserDAO;

public class CtrlUser {

    UserDAO dao = new UserDAO();
    RolDAO rol = new RolDAO();
    int id;
    private static int rolId;

    // Get the table model and set up a TableRowSorter for sorting functionality
    public void loadDataUser(JTable table) {

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<TableModel> order = new TableRowSorter<TableModel>(model);
        table.setRowSorter(order);
        model.setRowCount(0);
        List<User> user = dao.read();
        for (User users : user) {
            Object[] row = {users.getId(), users.getID_number(), users.getName(), users.getLast_name(), users.getBirth_date(), users.getEmail(), users.getPhone_number(), users.getPassword(), this.rol.getNameRol(users.getRol_id())};
            model.addRow(row);
        }
    }

    // Create a date formatter for parsing the birth date string
    public void addUser(JTextField IDNumber, JTextField name, JTextField lastName, JTextField birthDate, JTextField email, JTextField phoneNumber, JTextField password) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date userBirthDate = dateFormat.parse(birthDate.getText());
            this.dao.create(new User(Integer.parseInt(IDNumber.getText()), name.getText(), lastName.getText(), userBirthDate, email.getText(), Integer.parseInt(phoneNumber.getText()), password.getText(), rolId));
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error de formato en la fecha, el formato correcto es año-mes-día (yyyy-MM-dd): " + ex.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar el usuario: " + e.toString());
        }
        this.clearFields(IDNumber, name, lastName, birthDate, email, phoneNumber, password, email);
    }
    // Create a date formatter for parsing the birth date string

    public void addUserRegister(JTextField IDNumber, JTextField name, JTextField lastName, JTextField birthDate, JTextField email, JTextField phoneNumber, JTextField password, JFrame frame) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date userBirthDate = dateFormat.parse(birthDate.getText());
            this.dao.create(new User(Integer.parseInt(IDNumber.getText()), name.getText(), lastName.getText(), userBirthDate, email.getText(), Integer.parseInt(phoneNumber.getText()), password.getText(), rolId));
            if (rolId == 2) {
                frame.dispose();
            }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error de formato en la fecha, el formato correcto es año-mes-día (yyyy-MM-dd): " + ex.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar el usuario: " + e.toString());
        }
        this.clearFields(IDNumber, name, lastName, birthDate, email, phoneNumber, password, email);
    }

    // Create a date formatter for parsing the updated birth date string
    public void updatedUser(JTextField IDNumber, JTextField name, JTextField lastName, JTextField birthDate, JTextField email, JTextField phoneNumber, JTextField password) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date userBirthDate = dateFormat.parse(birthDate.getText());
            this.dao.update(new User(this.id, Integer.parseInt(IDNumber.getText()), name.getText(), lastName.getText(), userBirthDate, email.getText(), Integer.parseInt(phoneNumber.getText()), password.getText(), rolId));
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error de formato, el indicado es año-mes-día : ");
        }
    }

    // Delete the user with the specified ID from the data source using the DAO
    public void deleteuser() {
        this.dao.delete(this.id);
    }

    // Set the text content of each JTextField to an empty string
    public void clearFields(JTextField IDNumber, JTextField name, JTextField lastName, JTextField birthDate, JTextField email, JTextField phoneNumber, JTextField password, JTextField rolId) {
        IDNumber.setText("");
        name.setText("");
        lastName.setText("");
        birthDate.setText("");
        email.setText("");
        phoneNumber.setText("");
        password.setText("");
        rolId.setText("");
    }

    // Set the role ID to the specified value
    public static void setRolId(int id) {
        rolId = id;
    }
}
