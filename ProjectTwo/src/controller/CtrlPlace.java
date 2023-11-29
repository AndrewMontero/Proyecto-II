package controller;

import java.text.ParseException;
import java.util.List;
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
         // Get the table model and set up a TableRowSorter for sorting functionality

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
    // Create a new Place object with the provided information and add it to the data source
    public void addPlace(JTextField name, JTextField address, JTextField city, JTextField postalCode, JTextField latitude, JTextField longitude, JTextField tripAdvisorLink) {
        try {
            this.dao.create(new Place(name.getText(), address.getText(), city.getText(), Integer.parseInt(postalCode.getText()), latitude.getText(), longitude.getText(), tripAdvisorLink.getText()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar el lugar: " + e.toString());
        }
        this.clearFields(name, address, city, postalCode, latitude, longitude, tripAdvisorLink);
    }
    
     public void updatedPlace(JTextField name, JTextField address, JTextField city, JTextField postalCode, JTextField latitude, JTextField longitude, JTextField tripAdvisorLink) {
         // Update the existing place in the data source with the provided information
        try {     
            this.dao.update(new Place(this.id, name.getText(), address.getText(), city.getText(), Integer.parseInt(postalCode.getText()), latitude.getText(), longitude.getText(), tripAdvisorLink.getText()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el lugar: " + e.toString());
        }
        this.clearFields(name, address, city, postalCode, latitude, longitude, tripAdvisorLink);
    }
    // Delete the place with the specified ID from the data source
    public void deletePlace() {
        this.dao.delete(this.id);
    }
    // Set the text content of each JTextField to an empty string
    public void clearFields(JTextField name, JTextField address, JTextField city, JTextField postalCode, JTextField latitude, JTextField longitude, JTextField tripAdvisorLink) {
        name.setText("");
        address.setText("");
        city.setText("");
        postalCode.setText("");
        latitude.setText("");
        longitude.setText("");
        tripAdvisorLink.setText("");
    }
}
