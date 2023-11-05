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

public class CtrlEvent {

    EventDAO dao = new EventDAO();
    int id;

    public void loadDataEvent(JTable table) {

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

    public void addEvent(JTextField name, JTextField description, JTextField date, JTextField address, JTextField city, JTextField postalCode, JTextField price, JTextField room, JTextField placeId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date eventDate = dateFormat.parse(date.getText());
            this.dao.create(new Event(name.getText(), description.getText(), eventDate, address.getText(), city.getText(), Integer.parseInt(postalCode.getText()), Double.parseDouble(price.getText()), Integer.parseInt(room.getText()), Integer.parseInt(placeId.getText())));
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error de formato en la fecha, el formato correcto es año-mes-día (yyyy-MM-dd): " + ex.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar el evento: " + e.toString());
        }
    }

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
