/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphic;

import hibernate.HibernateUtil;
import hibernate.Profils;
import java.util.Iterator;
import java.util.Map;
import org.hibernate.Session;
import org.snmp4j.smi.OctetString;
import snmp_server.Profil;
import snmp_server.ProfilFactory;

/**
 *
 * @author Tomek
 */
public class ProfilFrame extends javax.swing.JFrame {

    MainFrame mother;
    public ProfilFrame(MainFrame mother) {
        this.mother = mother;
        initComponents();
        SnmpVersion.addItem("snmpv1");
        SnmpVersion.addItem("snmpv2c");
        SnmpVersion.addItem("snmpv3");
        setAuthProtocol();
        setPrivProtocol();
    }
    private void setAuthProtocol()
    {
        Iterator i = mother.getAuthProtocols().entrySet().iterator();
        while(i.hasNext())
        {
            Map.Entry entry = (Map.Entry)i.next();
            authBox.addItem(entry.getKey().toString());
        }        
    }
    private void setPrivProtocol()
    {
        Iterator i = mother.getPrivProtocols().entrySet().iterator();
        privBox.addItem("");
        while(i.hasNext())
        {
            Map.Entry entry = (Map.Entry)i.next();
            privBox.addItem(entry.getKey().toString());
        }        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        SnmpVersion = new javax.swing.JComboBox();
        SecuName = new javax.swing.JTextField();
        AuthPass = new javax.swing.JTextField();
        PrivPass = new javax.swing.JTextField();
        SaveButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        ProfilName = new javax.swing.JTextField();
        authBox = new javax.swing.JComboBox();
        privBox = new javax.swing.JComboBox();

        jLabel1.setText("SNMP version:");

        jLabel2.setText("Security Name:");

        jLabel3.setText("Authentication Protocol:");

        jLabel4.setText("Authentication Password:");

        jLabel5.setText("Privacy Protocol:");

        jLabel6.setText("Privacy Passowrd:");

        SaveButton.setText("Save");
        SaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveButtonActionPerformed(evt);
            }
        });

        jLabel7.setText("Profil Name:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(SaveButton)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel6))
                                    .addGap(38, 38, 38))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(SecuName, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                                .addComponent(AuthPass)
                                .addComponent(PrivPass)
                                .addComponent(ProfilName)
                                .addComponent(authBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(privBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(65, 65, 65)
                        .addComponent(SnmpVersion, 0, 135, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SnmpVersion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ProfilName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SecuName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(authBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(AuthPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(privBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(PrivPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(SaveButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveButtonActionPerformed

        mother.getProfils().add(mother.getProfils().size(),new Profil(
                ProfilFactory.addName(new OctetString(ProfilName.getText())),
                ProfilFactory.addVersion(SnmpVersion.getSelectedItem().toString()),
                ProfilFactory.addSecurityName(new OctetString(SecuName.getText())),              
                ProfilFactory.addAuthProtocol(new OctetString(authBox.getSelectedItem().toString()),mother.getAuthProtocols()),
                ProfilFactory.addAuthPassword(new OctetString(AuthPass.getText())),
                ProfilFactory.addPrivProtocol(new OctetString(privBox.getSelectedItem().toString()),mother.getPrivProtocols()),
                ProfilFactory.addPrivPassword(new OctetString(PrivPass.getText()))));
        mother.setProfilsTab(); 
        saveProfil();
    }//GEN-LAST:event_SaveButtonActionPerformed
    private void saveProfil()
    {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Profils profils = new Profils();
        profils.setName(ProfilName.getText());
        profils.setVersion(SnmpVersion.getSelectedItem().toString());
        profils.setSecurityname(SecuName.getText());
        profils.setAuthprot(authBox.getSelectedItem().toString());
        profils.setAuthpass(AuthPass.getText());
        profils.setPrivprot(privBox.getSelectedItem().toString());
        profils.setPrivpass(PrivPass.getText());
        session.save(profils);      
        session.getTransaction().commit();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AuthPass;
    private javax.swing.JTextField PrivPass;
    private javax.swing.JTextField ProfilName;
    private javax.swing.JButton SaveButton;
    private javax.swing.JTextField SecuName;
    private javax.swing.JComboBox SnmpVersion;
    private javax.swing.JComboBox authBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JComboBox privBox;
    // End of variables declaration//GEN-END:variables
}
