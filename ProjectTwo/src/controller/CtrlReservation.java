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
import model.EventDAO;
import model.Reservation;
import model.ReservationDAO;

public class CtrlReservation {

    Validation vali = new Validation();
    ReservationDAO dao = new ReservationDAO();
    EventDAO event = new EventDAO();
    int id;

    public void loadDataReservation(JTable table) {
        // Get the table model and set up a TableRowSorter for sorting functionality

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<TableModel> order = new TableRowSorter<TableModel>(model);
        table.setRowSorter(order);
        model.setRowCount(0);
        List<Reservation> reservation = dao.read();
        for (Reservation reservations : reservation) {
            Object[] row = {reservations.getId(), reservations.getUser_name(), reservations.getDate(), reservations.getQuantity(), this.event.getNameEvents(reservations.getEvent_id())};
            model.addRow(row);
        }
    }

    public void addReservation(JTextField userName, JTextField date, int quantity, int eventId) {
        if (!validateName(userName)) {
            return;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date reservationDate = dateFormat.parse(date.getText());
            this.dao.create(new Reservation(userName.getText(), reservationDate, quantity, eventId));
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error de formato en la fecha, el formato correcto es año-mes-día (yyyy-MM-dd): " + ex.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar la reserva: " + e.toString());
        }
        this.clearFields(userName, date);
    }
    // Create a date formatter for parsing the date string

    public void updatedReservation(JTextField userName, JTextField date, int quantity, int eventId) {
        if (!validateName(userName)) {
            return;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date reservationDate = dateFormat.parse(date.getText());
            this.dao.update(new Reservation(this.id, userName.getText(), reservationDate, quantity, eventId));
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error de formato, el indicado es año-mes-día : ");
        }
        this.clearFields(userName, date);
    }

    public void selectedRow(JTable table) {
        try {
            int row = table.getSelectedRow();
            if (row >= 0) {
                this.id = Integer.parseInt(table.getValueAt(row, 0).toString());
            } else {
                JOptionPane.showMessageDialog(null, "Fila no seleccionada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de selección, error: " + e.toString());
        }
    }

    // Delete the role with the specified ID from the data source
    public void deleteReserv() {
        this.dao.delete(this.id);
    }
    // Set the text content of each JTextField to an empty string

    public void clearFields(JTextField userName, JTextField date) {
        userName.setText("");
        date.setText("");
    }

    public boolean validateName(JTextField userName) {
        if (!vali.validateLettersSpaces(userName.getText())) {
            JOptionPane.showMessageDialog(null, "El nombre que ingresaste no cumple con el formato");
            return false;
        }
        return true;
    }

    public boolean validateQuantity(JTextField valor) {
        if (!vali.validateNumber(valor.getText())) {
            JOptionPane.showMessageDialog(null, "La canitidad que ingresaste no cumple con el formato");
            return false;
        }
        return true;
    }

}
