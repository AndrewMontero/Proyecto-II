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
import model.SVGImageUtils;

public class EventPanel extends javax.swing.JPanel {

    private Event event;
    private EventAPI api = new EventAPI();
    private frmAdmin parent;

    public EventPanel(Event event, EventAPI api) {
        initComponents();
        this.event = event;
        lblName.setText(event.getName());
        lblAddress.setText("Direccion: " + event.getAddress());

        try {
            Event details = api.getEventDetails(event.getId());
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
            List<String> imageUrls = api.getEventImages(event.getId());
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

        setBackground(new java.awt.Color(255, 255, 255));

        lblImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/assets/circulo-cruzado.png"))); // NOI18N

        lblName.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        lblName.setText("Nombre");

        lblAddress.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblAddress.setText("Direccion");

        btnDetails.setText("Descripcion");
        btnDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailsActionPerformed(evt);
            }
        });

        btnWeb.setText("Visit Web");
        btnWeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWebActionPerformed(evt);
            }
        });

        lblRatingImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/assets/circulo-cruzado.png"))); // NOI18N

        lblReviews.setText("Opniniones");

        jButton1.setText("Reservar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setText("Precio");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblName)
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
                            .addComponent(jButton1))))
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
                        .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblRatingImage)
                                    .addComponent(lblReviews))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnWeb)
                                    .addComponent(jButton1))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnDetails)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)
                                .addGap(47, 47, 47))))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailsActionPerformed
        try {
            Event details = api.getEventDetails(event.getId());
            String formattedDescription = details.getDescription().replace("\\n", "\n");
            eventDescription detailsDialog = new eventDescription(parent, true, "Detalles del Evento", formattedDescription);
            detailsDialog.setVisible(true);
        } catch (Exception e) {
            System.out.println("Error fetching event details: " + e.getMessage());
        }
    }//GEN-LAST:event_btnDetailsActionPerformed

    private void btnWebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWebActionPerformed
        try {
            String tripAdvisorLink = api.getPlaceDetails(event.getId()).getTripAdvisor_link();
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
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblRatingImage;
    private javax.swing.JLabel lblReviews;
    // End of variables declaration//GEN-END:variables
}
