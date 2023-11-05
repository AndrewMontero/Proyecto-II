package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.User;
import model.UserDAO;

public class CtrlUser {

    UserDAO dao = new UserDAO();
    int id;

    public void loadDataUser(JTable table) {

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<TableModel> order = new TableRowSorter<TableModel>(model);
        table.setRowSorter(order);
        model.setRowCount(0);
        List<User> user = dao.read();
        for (User users : user) {
            Object[] row = {users.getId(), users.getID_number(), users.getName(), users.getLast_name(), users.getBirth_date(), users.getEmail(), users.getPhone_number(), users.getPassword(), users.getRol_id()};
            model.addRow(row);
        }
    }

    public void addUser(JTextField IDNumber, JTextField name, JTextField lastName, JTextField birthDate, JTextField email, JTextField phoneNumber, JTextField password, JTextField rolId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date userBirthDate = dateFormat.parse(birthDate.getText());
            this.dao.create(new User(Integer.parseInt(IDNumber.getText()), name.getText(), lastName.getText(), userBirthDate, email.getText(), Integer.parseInt(phoneNumber.getText()), password.getText(), Integer.parseInt(rolId.getText())));
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error de formato en la fecha, el formato correcto es año-mes-día (yyyy-MM-dd): " + ex.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar el usuario: " + e.toString());
        }
    }

    public void deleteuser() {
        this.dao.delete(this.id);
    }

    public void clearFields(JTextField IDNumber, JTextField name) {
        IDNumber.setText("");
        name.setText("");

    }

}
