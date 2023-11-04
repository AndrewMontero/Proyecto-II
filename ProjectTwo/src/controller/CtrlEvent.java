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
import model.Event;
import model.EventDAO;



public class CtrlEvent {

    EventDAO dao = new EventDAO();
    int id;

    public void loadDataTeacher(JTable table) {

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<TableModel> order = new TableRowSorter<TableModel>(model);
        table.setRowSorter(order);
        model.setRowCount(0);
        List<Event> event = dao.read();
        for (Event events : event) {
            Object[] row = {events.getId(), events.getName(), events.getDescription(), events.getDate(), events.getAddress(), events.getCity(), events.getPostal_code(), events.getPrice(), events.getRoom(), events.getPlace_id()};
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
