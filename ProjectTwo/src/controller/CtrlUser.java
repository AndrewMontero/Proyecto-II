package controller;

import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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


    public void deleteEvent(){
        this.dao.delete(this.id);
    }
   
    public void clearFields(JTextField IDNumber, JTextField name) {
        IDNumber.setText("");
        name.setText("");
      
    }

}
