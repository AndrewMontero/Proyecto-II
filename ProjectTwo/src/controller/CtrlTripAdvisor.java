/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import model.EventAPI;
import model.Event;
import view.EventPanel;

/**
 *
 * @author Bravo
 */
public class CtrlTripAdvisor {

    private EventAPI apiHandler = new EventAPI();
    private List<Event> events;

    public CtrlTripAdvisor() {
        this.apiHandler = apiHandler;
        this.events = new ArrayList<>();
    }

    public void searchEvents(JTextField name, JTextField ubiField, JScrollPane scroll, JComboBox type) {
        try {
            events = apiHandler.searchEvents(name.getText(), ubiField.getText(), type.getSelectedItem().toString());
            displayEvents(scroll);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error buscando eventos: " + e.getMessage());
        }
    }

    public void displayEvents(JScrollPane scrollPane) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        for (Event event : events) {
            try {
                EventPanel eventPanel = new EventPanel(event, apiHandler);
                mainPanel.add(eventPanel);
            } catch (Exception e) {
                System.out.println("Error mostrando el evento: " + e.getMessage());
            }
        }
        scrollPane.setViewportView(mainPanel);
    }
}
