/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;
import model.EventAPI;
import model.Event;

/**
 *
 * @author Bravo
 */
public class CtrlTripAdvisor {

    private EventAPI apiHandler = new EventAPI();

    public void searchEvents(JTextField name, JTextField ubiField, JScrollPane scroll, JComboBox type) {
        try {
            List<Event> events = apiHandler.searchEvents(name.getText(), ubiField.getText(), type.getSelectedItem().toString());
            displayEvents(events, scroll);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error buscando eventos: " + e.getMessage());
        }
    }

    private void displayEvents(List<Event> events, JScrollPane scroll) throws Exception {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        for (Event event : events) {
            JPanel eventp = new JPanel();
            eventp.setPreferredSize(new Dimension(600, 300));
            eventp.setBorder(BorderFactory.createTitledBorder(event.getName()));
            eventp.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            gbc.gridx = 0;
            gbc.gridy = 0;
            eventp.add(new JLabel("Id: " + event.getId()), gbc);

            gbc.gridy++;
            eventp.add(new JLabel("Nombre: " + event.getName()), gbc);

            gbc.gridy++;
            eventp.add(new JLabel("Direccion: " + event.getAddress()), gbc);

            gbc.gridy++;
            String description = "";
            try {
                Event details = apiHandler.getEventDetails(event.getId());
                description = details.getDescription();
            } catch (Exception e) {
                description = "Descripcion no disponible";
            }

            // Check if the description is not empty before adding the text area
            if (description != null && !description.isEmpty()) {
                JTextArea descArea = new JTextArea("Descripcion: " + description);
                descArea.setLineWrap(true);
                descArea.setWrapStyleWord(true);
                descArea.setEditable(false);
                JScrollPane scrollDesc = new JScrollPane(descArea);
                scrollDesc.setPreferredSize(new Dimension(400, 100));
                gbc.gridy++;
                eventp.add(scrollDesc, gbc);
            }
            List<String> imageUrls = apiHandler.getEventImages(event.getId());
            JPanel imagep = new JPanel(new FlowLayout(FlowLayout.LEFT));
            for (String imageUrl : imageUrls) {
                try {
                    URL url = new URL(imageUrl);
                    ImageIcon icon = new ImageIcon(url);
                    Image original = icon.getImage();
                    Image resizedImage = original.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    ImageIcon resizedIcon = new ImageIcon(resizedImage);

                    JLabel imageLabel = new JLabel(resizedIcon);
                    imagep.add(imageLabel);
                } catch (Exception e) {
                    imagep.add(new JLabel("Imagen no disponible"));
                }
            }
            gbc.gridy++;
            eventp.add(imagep, gbc);

            mainPanel.add(eventp);
        }

        scroll.setViewportView(mainPanel);
    }
}
