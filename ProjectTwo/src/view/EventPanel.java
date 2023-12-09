package view;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URI;
import java.net.URL;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.Event;
import model.EventAPI;
import model.Place;
import model.PlaceAPI;
import model.SVGImageUtils;

public class EventPanel extends javax.swing.JPanel {

    private Event event;
    private EventAPI apiE = new EventAPI();
    private PlaceAPI apiP = new PlaceAPI();
    private frmUser parent;
    private int currentIndex;
    private List<String> imageUrls;

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

    private void loadEventDetails() throws Exception {
        Event details = apiE.getEventDetails(event.getLocationId());
        event.setNumReviews(details.getNumReviews());
        lblReviews.setText("Basado en: " + event.getNumReviews() + " opiniones");
        event.setRatingImageUrl(details.getRatingImageUrl());

        // Update description if available
        if ("Descripcion no disponible".equals(details.getDescription()) || details.getDescription().isEmpty()) {
            btnDetails.setEnabled(false);
        } else {
            event.setDescription(details.getDescription());
        }

        imageUrls = apiE.getEventImages(event.getLocationId());
        event.setImageUrls(imageUrls);
        updateImages();
        String ratingImageUrl = event.getRatingImageUrl();
        if (!ratingImageUrl.isEmpty()) {
            ImageIcon ratingIcon = SVGImageUtils.getSVGIcon(ratingImageUrl);
            if (ratingIcon != null) {
                lblRatingImage.setIcon(ratingIcon);
            }
        }
    }

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

    private void showPreviousImage() {
        if (!imageUrls.isEmpty()) {
            currentIndex = (currentIndex - 1 + imageUrls.size()) % imageUrls.size();
            updateImages();
        }
    }

    private void showNextImage() {
        if (!imageUrls.isEmpty()) {
            currentIndex = (currentIndex + 1) % imageUrls.size();
            updateImages();
        }
    }

    private Image getScaledImage(URL url, int width, int height) throws Exception {
        ImageIcon originalIcon = new ImageIcon(url);
        Image image = originalIcon.getImage();
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

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

    public Event getSelectedEvent() {
        return event;
    }

    public Place getSelectedPlace() {
        try {
            int selectedEventId = event.getLocationId();
            return apiP.getPlaceDetails(selectedEventId);
        } catch (Exception e) {
            System.out.println("Error obteniendo detalles del lugar: " + e.getMessage());
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblImage = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        btnDetails = new javax.swing.JButton();
        btnWeb = new javax.swing.JButton();
        lblRatingImage = new javax.swing.JLabel();
        lblReviews = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblDescription = new javax.swing.JLabel();
        lblSpeed = new javax.swing.JLabel();
        lblTemp = new javax.swing.JLabel();
        lblIcon = new javax.swing.JLabel();
        btnPrevImage = new javax.swing.JButton();
        btnNextImage = new javax.swing.JButton();

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

        btnDetails.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnDetails.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/assets/descripcion.png"))); // NOI18N
        btnDetails.setText("Descripcion");
        btnDetails.setContentAreaFilled(false);
        btnDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailsActionPerformed(evt);
            }
        });
        add(btnDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, -1, -1));

        btnWeb.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnWeb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/assets/globo.png"))); // NOI18N
        btnWeb.setText("Web");
        btnWeb.setContentAreaFilled(false);
        btnWeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWebActionPerformed(evt);
            }
        });
        add(btnWeb, new org.netbeans.lib.awtextra.AbsoluteConstraints(357, 140, -1, -1));

        lblRatingImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/assets/circulo-cruzado.png"))); // NOI18N
        add(lblRatingImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 104, 110, -1));

        lblReviews.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblReviews.setText("Sin Opiniones");
        add(lblReviews, new org.netbeans.lib.awtextra.AbsoluteConstraints(441, 112, -1, -1));

        jButton1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/assets/confirmar.png"))); // NOI18N
        jButton1.setText("Reservar");
        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(441, 140, -1, -1));

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
    }// </editor-fold>//GEN-END:initComponents

    private void btnDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailsActionPerformed
        try {
            Event details = apiE.getEventDetails(event.getLocationId());
            String formattedDescription = details.getDescription().replace("\\n", "\n");
            eventDescription detailsDialog = new eventDescription(parent, true, "Detalles del Evento", formattedDescription);
            detailsDialog.setVisible(true);
        } catch (Exception e) {
            System.out.println("Error fetching event details: " + e.getMessage());
        }
    }//GEN-LAST:event_btnDetailsActionPerformed

    private void btnWebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWebActionPerformed
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
    }//GEN-LAST:event_btnWebActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int selectedEventId = event.getLocationId();
        String eventName = event.getName();

        // Show a confirmation dialog
        int option = JOptionPane.showConfirmDialog(this, "¿Serás dirigido a la pagina de reservas del evento '" + eventName + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
        // Check the user response
        if (option == JOptionPane.YES_OPTION) {
            parent.setEventPanel(this);
            parent.getJpReservations();
            parent.actualizarFecha(selectedEventId);
        } else {
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDetails;
    private javax.swing.JButton btnNextImage;
    private javax.swing.JButton btnPrevImage;
    private javax.swing.JButton btnWeb;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
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
