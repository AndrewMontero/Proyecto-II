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
import model.Event;
import model.EventDAO;
import model.PlaceDAO;

public class CtrlEvent {

    EventDAO dao = new EventDAO();
    PlaceDAO place = new PlaceDAO();
    int id;

    public void loadDataEvent(JTable table) {

        // Get the table model and set up a TableRowSorter for sorting functionality
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<TableModel> order = new TableRowSorter<TableModel>(model);
        table.setRowSorter(order);
        // Clear existing rows in the table
        model.setRowCount(0);
         // Retrieve a list of events from the data access object (DAO)
        List<Event> event = dao.read();
        for (Event events : event) {
            Object[] row = {events.getId(), events.getName(), events.getDescription(), events.getDate(), events.getAddress(), events.getCity(), events.getPostal_code(),events.getPrice(), events.getRoom(), this.place.getNamePlaces(events.getPlace_id())};
            model.addRow(row);
        }
    }

    public void addEvent(JTextField name, JTextField description, JTextField date, JTextField address, JTextField city, JTextField postalCode, JTextField price, JTextField room, JTextField placeId) {
        // Create a date formatter for parsing the date string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
                    // Parse the date string into a Date object
            Date eventDate = dateFormat.parse(date.getText());
            // Create a new Event object with the provided information and add it to the data source
            this.dao.create(new Event(name.getText(), description.getText(), eventDate, address.getText(), city.getText(), Integer.parseInt(postalCode.getText()), Double.parseDouble(price.getText()), Integer.parseInt(room.getText()), Integer.parseInt(placeId.getText())));
        } catch (ParseException ex) {
            // Handle parsing errors and display an error message if the date format is incorrect
            JOptionPane.showMessageDialog(null, "Error de formato en la fecha, el formato correcto es año-mes-día (yyyy-MM-dd): " + ex.toString());
        } catch (Exception e) {
            // Handle other exceptions and display an error message if there's an issue adding the event
            JOptionPane.showMessageDialog(null, "Error al agregar el evento: " + e.toString());
        }
        // Clear the input fields after successfully adding the event
        this.clearFields(name, description, date, address, city, postalCode, price, room, placeId);
    }

    public void updatedEvent(JTextField name, JTextField description, JTextField date, JTextField address, JTextField city, JTextField postalCode, JTextField price, JTextField room, JTextField placeId) {
         // Create a date formatter for parsing the date string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date eventDate = dateFormat.parse(date.getText());
            this.dao.update(new Event(this.id, name.getText(), description.getText(), eventDate, address.getText(), city.getText(), Integer.parseInt(postalCode.getText()), Double.parseDouble(price.getText()), Integer.parseInt(room.getText()), Integer.parseInt(placeId.getText())));
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error de formato, el indicado es año-mes-día : ");
        }
        // Clear the input fields after successfully updating the event
        this.clearFields(name, description, date, address, city, postalCode, price, room, placeId);
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

     // Delete the event with the specified ID from the data source
    public void deleteEvent() {
        this.dao.delete(this.id);
    }

    public void clearFields(JTextField name, JTextField description, JTextField date, JTextField address, JTextField city, JTextField postalCode, JTextField price, JTextField room, JTextField placeId) {
        name.setText("");
        description.setText("");
        date.setText("");
        address.setText("");
        city.setText("");
        postalCode.setText("");
        price.setText("");
        room.setText("");
        placeId.setText("");
    }
}
