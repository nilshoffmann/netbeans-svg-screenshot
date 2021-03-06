/* 
 * Copyright 2014 Nils Hoffmann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.nilshoffmann.svg.options;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.prefs.Preferences;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.nilshoffmann.svg.actions.SvgScreenshot;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.util.NbPreferences;

final class SVGScreenshotPanel extends javax.swing.JPanel {

    private final SVGScreenshotOptionsPanelController controller;

    SVGScreenshotPanel(final SVGScreenshotOptionsPanelController controller) {
        this.controller = controller;
        initComponents();
        outputDirectory.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                controller.changed();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                controller.changed();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                controller.changed();
            }
        });
        paintMode.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.changed();
            }
        });
        useGzip.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.changed();
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        paintMode = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        outputDirectory = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        useGzip = new javax.swing.JCheckBox();

        paintMode.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PAINT", "PRINT" }));
        paintMode.setToolTipText(org.openide.util.NbBundle.getMessage(SVGScreenshotPanel.class, "SVGScreenshotPanel.paintMode.toolTipText")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(SVGScreenshotPanel.class, "SVGScreenshotPanel.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(SVGScreenshotPanel.class, "SVGScreenshotPanel.jLabel2.text")); // NOI18N

        outputDirectory.setText(org.openide.util.NbBundle.getMessage(SVGScreenshotPanel.class, "SVGScreenshotPanel.outputDirectory.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(SVGScreenshotPanel.class, "SVGScreenshotPanel.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(SVGScreenshotPanel.class, "SVGScreenshotPanel.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(useGzip, org.openide.util.NbBundle.getMessage(SVGScreenshotPanel.class, "SVGScreenshotPanel.useGzip.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(useGzip)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(paintMode, 0, 209, Short.MAX_VALUE)
                            .addComponent(outputDirectory))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(paintMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(outputDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(useGzip)
                .addContainerGap(32, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        outputDirectory.setText(getDefaultOutputDirectory());
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        FileChooserBuilder fcb = new FileChooserBuilder(SVGScreenshotPanel.class);
        fcb.setAcceptAllFileFilterUsed(true);
        fcb.setDirectoriesOnly(true);
        fcb.setTitle("Select output directory");
        fcb.setFileHiding(false);
        File outputDir = fcb.showOpenDialog();
        if (outputDirectory != null) {
            outputDirectory.setText(outputDir.getAbsolutePath());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    void load() {
        Preferences p = NbPreferences.forModule(SvgScreenshot.class);
        outputDirectory.setText(p.get(SvgScreenshot.PREF_OUTPUTDIRECTORY, getDefaultOutputDirectory()));
        paintMode.setSelectedItem(p.get(SvgScreenshot.PREF_PAINTMODE, SvgScreenshot.PaintMode.PAINT.name()));
        useGzip.setSelected(p.getBoolean(SvgScreenshot.PREF_USEGZIP, false));
    }

    void store() {
        Preferences p = NbPreferences.forModule(SvgScreenshot.class);
        p.put(SvgScreenshot.PREF_OUTPUTDIRECTORY, outputDirectory.getText().isEmpty() ? getDefaultOutputDirectory() : new File(outputDirectory.getText()).getAbsolutePath());
        p.put(SvgScreenshot.PREF_PAINTMODE, SvgScreenshot.PaintMode.valueOf(paintMode.getSelectedItem().toString()).name());
        p.putBoolean(SvgScreenshot.PREF_USEGZIP, useGzip.isSelected());
    }

    boolean valid() {
        return !outputDirectory.getText().isEmpty();
    }

    private String getDefaultOutputDirectory() {
        return new File(System.getProperty("user.home"), SvgScreenshot.DEFAULT_OUTPUTDIRECTORY).getAbsolutePath();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField outputDirectory;
    private javax.swing.JComboBox paintMode;
    private javax.swing.JCheckBox useGzip;
    // End of variables declaration//GEN-END:variables
}
