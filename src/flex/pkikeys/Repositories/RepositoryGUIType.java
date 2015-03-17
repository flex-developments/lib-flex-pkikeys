/*
 * lib-flex-pkikeys
 *
 * Copyright (C) 2010
 * Ing. Felix D. Lopez M. - flex.developments en gmail
 * 
 * Desarrollo apoyado por la Superintendencia de Servicios de Certificación 
 * Electrónica (SUSCERTE) durante 2010-2014 por:
 * Ing. Felix D. Lopez M. - flex.developments en gmail | flopez en suscerte gob ve
 * Ing. Yessica De Ascencao - yessicadeascencao en gmail | ydeascencao en suscerte gob ve
 *
 * Este programa es software libre; Usted puede usarlo bajo los terminos de la
 * licencia de software GPL version 2.0 de la Free Software Foundation.
 *
 * Este programa se distribuye con la esperanza de que sea util, pero SIN
 * NINGUNA GARANTIA; tampoco las implicitas garantias de MERCANTILIDAD o
 * ADECUACION A UN PROPOSITO PARTICULAR.
 * Consulte la licencia GPL para mas detalles. Usted debe recibir una copia
 * de la GPL junto con este programa; si no, escriba a la Free Software
 * Foundation Inc. 51 Franklin Street,5 Piso, Boston, MA 02110-1301, USA.
 */

package flex.pkikeys.Repositories;

import flex.pkikeys.PKIKeysLogger;
import flex.pkikeys.exceptions.PKIKeysException;
import flex.pkikeys.exceptions.PKIKeysQuitWinException;
import flex.pkikeys.i18n.I18n;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 ** RepositorioGUITipo
 * 
 * @author Ing. Felix D. Lopez M. - flex.developments en gmail
 * @author Ing. Yessica De Ascencao - yessicadeascencao en gmail
 */
public class RepositoryGUIType extends javax.swing.JFrame {
    private ImageIcon image;
    private final TreeMap<String, String> matrizKSType = new TreeMap<>();
    
    /**
     * Creates new form RepositorioGUITipo
     */
    public RepositoryGUIType() {
        try {
            InputStream imgIS = flex.pkikeys.Repositories.AbstractRepository.class.getResourceAsStream("llaves.png");
            image = new ImageIcon(ImageIO.read(imgIS));
            initComponents();
            KSType.removeAllItems();
            Enumeration<String> keys = RepositoryBundle.getKeys();
            while(keys.hasMoreElements()) {
                String key = keys.nextElement();
                matrizKSType.put(RepositoryBundle.get(key), key);
            }
            for(String element : matrizKSType.descendingKeySet()) KSType.addItem(element);
            
        } catch (IOException ex) {
            PKIKeysLogger.writeErrorLog(ex);
        }
    }
    
    public String getSelectedKSType() {
        return matrizKSType.get(KSType.getSelectedItem().toString());
    }
    
    private int mostraVentanaModal() {
        return JOptionPane.showOptionDialog(
              null,
              this.getContentPane(),
              this.getTitle(),
              JOptionPane.OK_CANCEL_OPTION,
              JOptionPane.DEFAULT_OPTION,
              null,
              null,
              null);
    }

    public void getKSType() throws PKIKeysException {
        switch (mostraVentanaModal()) {
            case JOptionPane.OK_OPTION: { //Aceptar
                break;
            }

            default: { //Salir
                throw new PKIKeysException(new PKIKeysQuitWinException());
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        KSType = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        Limagen = new javax.swing.JLabel(image);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(I18n.get(I18n.I_REPOSITORY_GUI_TITLE));

        KSType.setName(""); // NOI18N

        jLabel1.setLabelFor(KSType);
        jLabel1.setText(I18n.get("I_REPOSITORY_TYPE_L_CENTRAL"));
        jLabel1.setToolTipText("");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        Limagen.setText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Limagen, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(KSType, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(85, 85, 85)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(KSType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
            .addComponent(Limagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox KSType;
    private javax.swing.JLabel Limagen;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
