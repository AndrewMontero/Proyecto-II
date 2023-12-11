package view;

import java.awt.Desktop;
import java.awt.Image;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Event;
import model.EventAPI;
import model.Place;
import model.PlaceAPI;
import model.SVGImageUtils;

//Represents a panel for displaying detailed information about an event, including images and weather information.
public class EventPanel extends javax.swing.JPanel {

    private Event event;
    private EventAPI apiE = new EventAPI();
    private PlaceAPI apiP = new PlaceAPI();
    private frmUser parent;
    private int currentIndex;
    private List<String> imageUrls;

    /**
     * Creates a new EventPanel instance.
     *
     * @param event The Event object to display details for.
     * @param api The EventAPI instance to use for fetching event details.
     * @param parent The parent frame.
     */
    public EventPanel(Event event, EventAPI api, frmUser parent) {
        initComponents();
        this.imageUrls = imageUrls;
        this.parent = parent;
        this.currentIndex = 0;
        this.event = event;
        lblName.setText(event.getName());
        lblAddress.setText("Direccion: " + event.getAddress());
        try {
            loadEventDetails();
            loadWeatherInfo();
        } catch (Exception e) {
            System.out.println("Error al cargar el panel de eventos: " + e.getMessage());
        }
    }

    //Loads additional details about the event, such as reviews, rating, and images.
    private void loadEventDetails() throws Exception {
        Event details = apiE.getEventDetails(event.getLocationId());
        event.setNumReviews(details.getNumReviews());
        lblReviews.setText("Basado en: " + event.getNumReviews() + " opiniones");
        event.setRatingImageUrl(details.getRatingImageUrl());

        // Update description if available
        if ("Descripcion no disponible".equals(details.getDescription()) || details.getDescription().isEmpty()) {
            btnDescription.setEnabled(false);
        } else {
            event.setDescription(details.getDescription());
        }
        imageUrls = apiE.getEventImages(event.getLocationId());
        event.setImageUrls(imageUrls);
        updateImages();
        setRandomPrice();
        setRandomRoom();
        String ratingImageUrl = event.getRatingImageUrl();
        if (!ratingImageUrl.isEmpty()) {
            ImageIcon ratingIcon = SVGImageUtils.getSVGIcon(ratingImageUrl);
            if (ratingIcon != null) {
                lblRatingImage.setIcon(ratingIcon);
            }
        }
    }

    //Updates the displayed images.
    private void updateImages() {
        if (!imageUrls.isEmpty()) {
            String imageUrl = imageUrls.get(currentIndex);
            try {
                Image scaledImage = getScaledImage(new URL(imageUrl), 300, 300);
                ImageIcon icon = new ImageIcon(scaledImage);
                lblImage.setIcon(icon);

            } catch (Exception e) {
                System.out.println("Error cargando imagen: " + e.getMessage());
            }
        }
    }

    //Displays the previous image in the image list.
    private void showPreviousImage() {
        if (!imageUrls.isEmpty()) {
            currentIndex = (currentIndex - 1 + imageUrls.size()) % imageUrls.size();
            updateImages();
        }
    }

    // Shows the next image in the image gallery.
    private void showNextImage() {
        if (!imageUrls.isEmpty()) {
            currentIndex = (currentIndex + 1) % imageUrls.size();
            updateImages();
        }
    }

    /**
     * Gets a scaled image from the specified URL.
     *
     * @param url The URL of the image.
     * @param width The desired width of the scaled image.
     * @param height The desired height of the scaled image.
     * @return The scaled Image object.
     */
    private Image getScaledImage(URL url, int width, int height) throws Exception {
        ImageIcon originalIcon = new ImageIcon(url);
        Image image = originalIcon.getImage();
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    //Loads weather information for the event's location and updates the weather display.
    private void loadWeatherInfo() {
        try {
            Place details = apiP.getPlaceDetails(event.getLocationId());
            List<Place> weatherInfo = apiP.fetchWeatherInfo(details.getLatitude(), details.getLongitude());
            if (!weatherInfo.isEmpty()) {
                lblDescription.setText(weatherInfo.get(0).getDescription());
                if (!weatherInfo.isEmpty()) {
                    lblTemp.setText(String.valueOf("Temperatura: " + weatherInfo.get(0).getTemperature() + "º"));
                    lblSpeed.setText("Velocidad del viento: " + weatherInfo.get(0).getSpeed());
                    updateWeatherIcon(weatherInfo.get(0).getIcon());
                }
            }
        } catch (Exception e) {
            System.out.println("Error cargando lugares: " + e.getMessage());
        }
    }

    /**
     * Updates the weather icon based on the specified icon code.
     *
     * @param icon The icon code representing the weather condition.
     */
    private void updateWeatherIcon(String icon) {
        try {
            String iconUrl = apiP.getWeatherIconUrl(icon);
            ImageIcon originalIcon = new ImageIcon(new URL(iconUrl));
            int width = 60;
            int height = 60;
            Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            lblIcon.setIcon(scaledIcon);
        } catch (Exception e) {
            System.out.println("Error al actualizar el icono meteorológico: " + e.getMessage());
        }
    }

    //Sets a random price for the event and updates the corresponding label.
    private void setRandomPrice() {
        double randomPrice = 10 + (new Random().nextDouble() * (200 - 10));
        jLabel1.setText(String.format("$%.2f", randomPrice));
        event.setPrice(randomPrice);
    }

    // Sets a random room number for the event and updates the corresponding label.
    private void setRandomRoom() {
        int randomRoom = 1 + new Random().nextInt(20);
        event.setRoom(randomRoom);
    }

    // Retrieves the selected event.
    public Event getSelectedEvent() {
        return event;
    }

    //Retrieves detailed information about the selected place.
    public Place getSelectedPlace() {
        try {
            int selectedEventId = event.getLocationId();
            return apiP.getPlaceDetails(selectedEventId);
        } catch (Exception e) {
            System.out.println("Error obteniendo detalles del lugar: " + e.getMessage());
            return null;
        }
    }

    private void panelColor(JPanel panel) {
        panel.setBackground(new java.awt.Color(153, 236, 234));
    }

    private void resetPanelColor(JPanel panel) {
        panel.setBackground(new java.awt.Color(10, 186, 181));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblImage = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        lblRatingImage = new javax.swing.JLabel();
        lblReviews = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblDescription = new javax.swing.JLabel();
        lblSpeed = new javax.swing.JLabel();
        lblTemp = new javax.swing.JLabel();
        lblIcon = new javax.swing.JLabel();
        btnPrevImage = new javax.swing.JButton();
        btnNextImage = new javax.swing.JButton();
        btnDescription = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnWeb = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnBooking = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/assets/circulo-cruzado.png"))); // NOI18N
        add(lblImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 7, 165, 150));

        lblName.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        lblName.setText("Nombre");
        add(lblName, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, -1, 38));

        lblAddress.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblAddress.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/assets/ubicacion.png"))); // NOI18N
        lblAddress.setText("Direccion");
        add(lblAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 490, 24));

        lblRatingImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/assets/circulo-cruzado.png"))); // NOI18N
        add(lblRatingImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 104, 110, -1));

        lblReviews.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblReviews.setText("Sin Opiniones");
        add(lblReviews, new org.netbeans.lib.awtextra.AbsoluteConstraints(441, 112, -1, -1));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/assets/dolar.png"))); // NOI18N
        jLabel1.setText("Precio");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 133, -1, -1));

        lblDescription.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblDescription.setText("Clima");
        add(lblDescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 30, -1, -1));

        lblSpeed.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblSpeed.setText("Velocidad del viento");
        add(lblSpeed, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 110, -1, -1));

        lblTemp.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblTemp.setText("temp");
        add(lblTemp, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 70, -1, -1));

        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/assets/cancelado.png"))); // NOI18N
        add(lblIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, -1, -1));

        btnPrevImage.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnPrevImage.setText("<-");
        btnPrevImage.setContentAreaFilled(false);
        btnPrevImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPrevImageMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPrevImageMouseExited(evt);
            }
        });
        btnPrevImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevImageActionPerformed(evt);
            }
        });
        add(btnPrevImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 50, -1));

        btnNextImage.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnNextImage.setText("->");
        btnNextImage.setContentAreaFilled(false);
        btnNextImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextImageActionPerformed(evt);
            }
        });
        add(btnNextImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, -1, -1));

        btnDescription.setBackground(new java.awt.Color(58, 203, 199));
        btnDescription.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDescriptionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDescriptionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDescriptionMouseExited(evt);
            }
        });
        btnDescription.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/assets/descripcion.png"))); // NOI18N
        btnDescription.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 35, 30));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Descripcion");
        btnDescription.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 7, 90, 20));

        add(btnDescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, 140, 30));

        btnWeb.setBackground(new java.awt.Color(58, 203, 199));
        btnWeb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnWebMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnWebMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnWebMouseExited(evt);
            }
        });
        btnWeb.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/assets/web.png"))); // NOI18N
        btnWeb.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 30, 30));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Web");
        btnWeb.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 40, 30));

        add(btnWeb, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 150, 90, 30));

        btnBooking.setBackground(new java.awt.Color(58, 203, 199));
        btnBooking.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBookingMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBookingMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBookingMouseExited(evt);
            }
        });
        btnBooking.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/assets/confirmar.png"))); // NOI18N
        btnBooking.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 30, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Reservar");
        btnBooking.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 60, 30));

        add(btnBooking, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 150, 100, 30));
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrevImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevImageActionPerformed
        showPreviousImage();
    }//GEN-LAST:event_btnPrevImageActionPerformed

    private void btnNextImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextImageActionPerformed
        showNextImage();
    }//GEN-LAST:event_btnNextImageActionPerformed

    private void btnPrevImageMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrevImageMouseEntered


    }//GEN-LAST:event_btnPrevImageMouseEntered

    private void btnPrevImageMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrevImageMouseExited

    }//GEN-LAST:event_btnPrevImageMouseExited

    private void btnDescriptionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDescriptionMouseClicked
        //Event handler for the "Details" button, displaying additional details about the event.
        try {
            Event details = apiE.getEventDetails(event.getLocationId());
            String formattedDescription = details.getDescription().replace("\\n", "\n");
            eventDescription detailsDialog = new eventDescription(parent, true, "Detalles del Evento", formattedDescription);
            detailsDialog.setVisible(true);
        } catch (Exception e) {
            System.out.println("Error fetching event details: " + e.getMessage());
        }
    }//GEN-LAST:event_btnDescriptionMouseClicked

    private void btnDescriptionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDescriptionMouseEntered
        panelColor(btnDescription);
    }//GEN-LAST:event_btnDescriptionMouseEntered

    private void btnDescriptionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDescriptionMouseExited
        resetPanelColor(btnDescription);
    }//GEN-LAST:event_btnDescriptionMouseExited

    private void btnWebMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnWebMouseClicked
        //Event handler for the "Web" button, opening the TripAdvisor link associated with the event.
        try {
            String tripAdvisorLink = apiP.getPlaceDetails(event.getLocationId()).getTripAdvisor_link();
            if (tripAdvisorLink != null && !tripAdvisorLink.isEmpty()) {
                Desktop.getDesktop().browse(new URI(tripAdvisorLink));
            } else {
                JOptionPane.showMessageDialog(this, "No hay enlace de TripAdvisor disponible.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println("Error opening TripAdvisor link: " + e.getMessage());
        }
    }//GEN-LAST:event_btnWebMouseClicked

    private void btnWebMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnWebMouseEntered
        panelColor(btnWeb);
    }//GEN-LAST:event_btnWebMouseEntered

    private void btnWebMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnWebMouseExited
        resetPanelColor(btnWeb);
    }//GEN-LAST:event_btnWebMouseExited

    private void btnBookingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBookingMouseClicked
        //Event handler for the "Reserve" button, prompting the user to confirm redirection to the reservation page.
        String eventName = event.getName();

        // Show a confirmation dialog
        int option = JOptionPane.showConfirmDialog(this, "¿Serás dirigido a la pagina de reservas del evento '" + eventName + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
        // Check the user response
        if (option == JOptionPane.YES_OPTION) {
            parent.setEventPanel(this);
            parent.getJpReservations();
            parent.actualizarFecha(eventName);
        } else {
        }
    }//GEN-LAST:event_btnBookingMouseClicked

    private void btnBookingMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBookingMouseEntered
        panelColor(btnBooking);
    }//GEN-LAST:event_btnBookingMouseEntered

    private void btnBookingMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBookingMouseExited
        resetPanelColor(btnBooking);
    }//GEN-LAST:event_btnBookingMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnBooking;
    private javax.swing.JPanel btnDescription;
    private javax.swing.JButton btnNextImage;
    private javax.swing.JButton btnPrevImage;
    private javax.swing.JPanel btnWeb;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblRatingImage;
    private javax.swing.JLabel lblReviews;
    private javax.swing.JLabel lblSpeed;
    private javax.swing.JLabel lblTemp;
    // End of variables declaration//GEN-END:variables
}
