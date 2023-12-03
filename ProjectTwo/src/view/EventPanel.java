package view;

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
    private frmAdmin parent;
    private int currentIndex;
    private List<String> imageUrls;

    public EventPanel(Event event, EventAPI api) {
        initComponents();
        this.imageUrls = imageUrls;
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

        lblDescription.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblDescription.setText("Clima");

        lblSpeed.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblSpeed.setText("Velocidad del viento");

        lblTemp.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblTemp.setText("temp");

        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/assets/cancelado.png"))); // NOI18N

        btnPrevImage.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnPrevImage.setText("<-");
        btnPrevImage.setContentAreaFilled(false);
        btnPrevImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevImageActionPerformed(evt);
            }
        });

        btnNextImage.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnNextImage.setText("->");
        btnNextImage.setContentAreaFilled(false);
        btnNextImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextImageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnPrevImage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNextImage)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblName)
                                .addGap(49, 49, 49)
                                .addComponent(lblIcon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblDescription)
                                .addGap(18, 18, 18)
                                .addComponent(lblTemp)
                                .addGap(18, 18, 18)
                                .addComponent(lblSpeed))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnWeb)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnDetails)
                                        .addGap(27, 27, 27)
                                        .addComponent(lblRatingImage, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1)
                                    .addComponent(lblReviews))))
                        .addGap(87, 87, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNextImage)
                            .addComponent(btnPrevImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIcon)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblDescription)
                                .addComponent(lblTemp)
                                .addComponent(lblSpeed)
                                .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblRatingImage)
                                    .addComponent(lblReviews))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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

    private void btnPrevImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevImageActionPerformed
        showPreviousImage();
    }//GEN-LAST:event_btnPrevImageActionPerformed

    private void btnNextImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextImageActionPerformed
        showNextImage();
    }//GEN-LAST:event_btnNextImageActionPerformed


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
