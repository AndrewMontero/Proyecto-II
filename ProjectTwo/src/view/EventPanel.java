package view;

import java.awt.Desktop;
import java.awt.GridLayout;
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
    private frmAdmin parent;

    public EventPanel(Event event, EventAPI api) {
        initComponents();
        this.event = event;
        lblName.setText(event.getName());
        lblAddress.setText("Direccion: " + event.getAddress());

        try {
            Event details = api.getEventDetails(event.getLocationId());
            event.setNumReviews(details.getNumReviews());
            lblReviews.setText("Basado en: " + event.getNumReviews() + " opiniones");
            event.setRatingImageUrl(details.getRatingImageUrl());

            // Update description if available
            if ("Descripcion no disponible".equals(details.getDescription()) || details.getDescription().isEmpty()) {
                btnDetails.setEnabled(false);
            } else {
                event.setDescription(details.getDescription());
            }
        } catch (Exception e) {
            System.out.println("Error cargando detalles: " + e.getMessage());
            btnDetails.setEnabled(false);
        }

        // Update images after getting details
        try {
            List<String> imageUrls = api.getEventImages(event.getLocationId());
            event.setImageUrls(imageUrls);
            updateDetails();
        } catch (Exception e) {
            System.out.println("Error cargando imágenes: " + e.getMessage());
        }
        try {
            String ratingImageUrl = event.getRatingImageUrl();
            if (!ratingImageUrl.isEmpty()) {
                ImageIcon ratingIcon = SVGImageUtils.getSVGIcon(ratingImageUrl);
                if (ratingIcon != null) {
                    lblRatingImage.setIcon(ratingIcon);
                }
            }
        } catch (Exception e) {
            System.out.println("Error cargando imagen de clasificación: " + e.getMessage());
        }
         try {
            Place details = apiP.getPlaceDetails(event.getLocationId());
            List<Place> weatherInfo = apiP.fetchWeatherInfo(details.getLatitude(), details.getLongitude());
            if (!weatherInfo.isEmpty()) {
                lblDescription.setText("Pronóstico: " + weatherInfo.get(0).getDescription());
                if (!weatherInfo.isEmpty()) {
                    lblTemp.setText(String.valueOf(weatherInfo.get(0).getTemperature()));
                    lblSpeed.setText("Velocidad del viento: " + weatherInfo.get(0).getSpeed());
                    String iconUrl = apiP.getWeatherIconUrl(weatherInfo.get(0).getIcon());
                    ImageIcon icon = new ImageIcon(new URL(iconUrl));
                    lblIcon.setIcon(icon);
                }
            }
        } catch (Exception e) {
            System.out.println("Error cargando lugares: " + e.getMessage());
        }

        this.parent = parent;
    }

    private void updateDetails() {
        try {
            List<String> imageUrls = event.getImageUrls();
            if (!imageUrls.isEmpty()) {
                String imageUrl = imageUrls.get(0);
                lblImage.setIcon(new javax.swing.ImageIcon(new URL(imageUrl)));
            }
        } catch (Exception e) {
            System.out.println("Error cargando imagen: " + e.getMessage());
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

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));

        lblImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/assets/circulo-cruzado.png"))); // NOI18N

        lblName.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        lblName.setText("Nombre");

        lblAddress.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblAddress.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/assets/ubicacion.png"))); // NOI18N
        lblAddress.setText("Direccion");

        btnDetails.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnDetails.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/assets/descripcion.png"))); // NOI18N
        btnDetails.setText("Descripcion");
        btnDetails.setContentAreaFilled(false);
        btnDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailsActionPerformed(evt);
            }
        });

        btnWeb.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnWeb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/assets/globo.png"))); // NOI18N
        btnWeb.setText("Web");
        btnWeb.setContentAreaFilled(false);
        btnWeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWebActionPerformed(evt);
            }
        });

        lblRatingImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/assets/circulo-cruzado.png"))); // NOI18N

        lblReviews.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblReviews.setText("Sin Opiniones");

        jButton1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/assets/confirmar.png"))); // NOI18N
        jButton1.setText("Reservar");
        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/assets/dolar.png"))); // NOI18N
        jLabel1.setText("Precio");

        lblDescription.setText("Clima");

        lblSpeed.setText("Velocidad del viento");

        lblTemp.setText("temp");

        lblIcon.setText("Icono");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnWeb))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnDetails)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblRatingImage, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblReviews)
                            .addComponent(jButton1))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblName)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblDescription)
                                .addGap(17, 17, 17))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblTemp)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblSpeed)
                                .addGap(18, 18, 18)
                                .addComponent(lblIcon)
                                .addGap(16, 16, 16))
                            .addComponent(lblAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblIcon)
                                            .addComponent(lblSpeed))))
                                .addGap(14, 14, 14)
                                .addComponent(lblAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblDescription)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTemp)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblRatingImage)
                                    .addComponent(lblReviews))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnWeb)
                                    .addComponent(jButton1)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnDetails)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
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
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDetails;
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
