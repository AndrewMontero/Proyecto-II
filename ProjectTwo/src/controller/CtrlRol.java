package controller;

import java.util.List;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.Rol;
import model.RolDAO;

public class CtrlRol {

    RolDAO dao = new RolDAO();
    int id;

    public void loadDataRol(JTable table) {

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<TableModel> order = new TableRowSorter<TableModel>(model);
        table.setRowSorter(order);
        model.setRowCount(0);
        List<Rol> rol = dao.read();
        for (Rol rols : rol) {
            Object[] row = {rols.getId(), rols.getName()};
            model.addRow(row);
        }
    }

    public void deleteEvent() {
        this.dao.delete(this.id);
    }

    public void clearFields(JTextField IDNumber, JTextField name) {
        IDNumber.setText("");
        name.setText("");

    }

}
