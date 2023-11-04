
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
import model.Place;
import model.PlaceDAO;



public class CtrlPlace {

    PlaceDAO dao = new PlaceDAO();
    int id;

    public void loadDataPlace(JTable table) {

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<TableModel> order = new TableRowSorter<TableModel>(model);
        table.setRowSorter(order);
        model.setRowCount(0);
        List<Place> place = dao.read();
        for (Place places : place) {
            Object[] row = {places.getId(), places.getName(), places.getAddress(), places.getCity(), places.getPostal_code(), places.getLatitude(), places.getLongitude(), places.getTripAdvisor_link()};
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
