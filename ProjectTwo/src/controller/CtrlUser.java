package controller;

import com.toedter.calendar.JDateChooser;
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

    Validation vali = new Validation();
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
    public void addUser(JTextField IDNumber, JTextField name, JTextField lastName, JDateChooser birthDate, JTextField email, JTextField phoneNumber, JTextField password) {
        if (!validateName(name) || !validateLastName(lastName) || !validateIDNumber(IDNumber) || !validateEmail(email) || !validatePhone(phoneNumber) || !validatePassword(password)) {
            return;
        }
        try {
            Date userBirthDate = birthDate.getDate();
            this.dao.create(new User(Integer.parseInt(IDNumber.getText()), name.getText(), lastName.getText(), userBirthDate, email.getText(), Integer.parseInt(phoneNumber.getText()), password.getText(), rolId));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar el usuario: " + e.toString());
        }
        this.clearFields(IDNumber, name, lastName, birthDate, email, phoneNumber, password);
    }
    // Create a date formatter for parsing the birth date string

    public void addUserRegister(JTextField IDNumber, JTextField name, JTextField lastName, JDateChooser birthDate, JTextField email, JTextField phoneNumber, JTextField password, JFrame frame) {
        if (!validateName(name) || !validateLastName(lastName) || !validateIDNumber(IDNumber) || !validateEmail(email) || !validatePhone(phoneNumber) || !validatePassword(password)) {
            return;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date userBirthDate = birthDate.getDate();
            this.dao.create(new User(Integer.parseInt(IDNumber.getText()), name.getText(), lastName.getText(), userBirthDate, email.getText(), Integer.parseInt(phoneNumber.getText()), password.getText(), rolId));
            if (rolId == 2) {
                frame.dispose();
            }
            this.clearFields(IDNumber, name, lastName, birthDate, email, phoneNumber, password);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar el usuario: " + e.toString());
        }
    }

    // Create a date formatter for parsing the updated birth date string
    public void updatedUser(JTextField IDNumber, JTextField name, JTextField lastName, JDateChooser birthDate, JTextField email, JTextField phoneNumber, JTextField password) {
        if (!validateName(name) || !validateLastName(lastName) || !validateIDNumber(IDNumber) || !validateEmail(email) || !validatePhone(phoneNumber) || !validatePassword(password)) {
            return;
        }
        try {
            Date userBirthDate = birthDate.getDate();
            this.dao.update(new User(this.id, Integer.parseInt(IDNumber.getText()), name.getText(), lastName.getText(), userBirthDate, email.getText(), Integer.parseInt(phoneNumber.getText()), password.getText(), rolId));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el usuario: " + e.toString());
        }
    }

    public void selectedRow(JTable table, JTextField IDNumber, JTextField name, JTextField lastName, JDateChooser birthDate, JTextField email, JTextField phoneNumber, JTextField password) {
        try {
            int row = table.getSelectedRow();
            if (row >= 0) {
                this.id = Integer.parseInt(table.getValueAt(row, 0).toString());
                IDNumber.setText(table.getValueAt(row, 1).toString());
                name.setText(table.getValueAt(row, 2).toString());
                lastName.setText(table.getValueAt(row, 3).toString());
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(table.getValueAt(row, 4).toString());
                birthDate.setDate(date);
                email.setText(table.getValueAt(row, 5).toString());
                phoneNumber.setText(table.getValueAt(row, 6).toString());
                password.setText(table.getValueAt(row, 7).toString());
            } else {
                JOptionPane.showMessageDialog(null, "Fila no seleccionada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de selección, error: " + e.toString());
        }
    }

    // Delete the user with the specified ID from the data source using the DAO
    public void deleteuser() {
        this.dao.delete(this.id);
    }

    // Set the text content of each JTextField to an empty string
    public void clearFields(JTextField IDNumber, JTextField name, JTextField lastName, JDateChooser birthDate, JTextField email, JTextField phoneNumber, JTextField password) {
        IDNumber.setText("");
        name.setText("");
        lastName.setText("");
        birthDate.setDate(null);
        email.setText("");
        phoneNumber.setText("");
        password.setText("");
    }

    public void setId(int id) {
        this.id = id;
    }
    
    // Set the role ID to the specified value
    public  static void setRolId(int id) {
        rolId=0;
        rolId = id;
    }

    public boolean validateIDNumber(JTextField IDNumber) {
        if (!vali.validateIdNumber(IDNumber.getText())) {
            JOptionPane.showMessageDialog(null, "La cedula que ingresaste no cumple con el formato");
            return false;
        }
        return true;
    }

    public boolean validateName(JTextField name) {
        if (!vali.validateLettersSpaces(name.getText())) {
            JOptionPane.showMessageDialog(null, "El nombre que ingresaste no cumple con el formato");
            return false;
        }
        return true;
    }

    public boolean validateLastName(JTextField LastnName) {
        if (!vali.validateLettersSpaces(LastnName.getText())) {
            JOptionPane.showMessageDialog(null, "El apellido que ingresaste no cumple con el formato");
            return false;
        }
        return true;
    }

    public boolean validateEmail(JTextField correo) {
        if (!vali.validateEmail(correo.getText())) {
            JOptionPane.showMessageDialog(null, "El email que ingresaste no cumple con el formato");
            return false;
        }
        return true;
    }

    public boolean validatePhone(JTextField phoneNumber) {
        if (!vali.validatePhoneNumber(phoneNumber.getText())) {
            JOptionPane.showMessageDialog(null, "El numero de telefono que ingresaste no cumple con el formato");
            return false;
        }
        return true;
    }

    public boolean validatePassword(JTextField valor) {
        if (!vali.validateAlphanumeric(valor.getText())) {
            JOptionPane.showMessageDialog(null, "La contraseña que ingresaste no cumple con el formato");
            return false;
        }
        return true;
    }
}
