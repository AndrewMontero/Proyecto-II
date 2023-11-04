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
import model.Reservation;
import model.ReservationDAO;



public class CtrlReservation {

    ReservationDAO dao = new ReservationDAO();
    int id;

    public void loadDataReservation(JTable table) {

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<TableModel> order = new TableRowSorter<TableModel>(model);
        table.setRowSorter(order);
        model.setRowCount(0);
        List<Reservation> reservation = dao.read();
        for (Reservation reservations : reservation) {
            Object[] row = {reservations.getId(), reservations.getUser_name(), reservations.getDate(), reservations.getQuantity(), reservations.getEvent_id()};
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
