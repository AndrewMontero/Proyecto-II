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

    public void addReservation(JTextField userName, JTextField date, JTextField quantity, JTextField eventId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date reservationDate = dateFormat.parse(date.getText());
            this.dao.create(new Reservation(userName.getText(), reservationDate, Integer.parseInt(quantity.getText()), Integer.parseInt(eventId.getText())));
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error de formato en la fecha, el formato correcto es año-mes-día (yyyy-MM-dd): " + ex.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar la reserva: " + e.toString());
        }
    }

    public void deleteRol() {
        this.dao.delete(this.id);
    }

    public void clearFields(JTextField IDNumber, JTextField name) {
        IDNumber.setText("");
        name.setText("");

    }

}
