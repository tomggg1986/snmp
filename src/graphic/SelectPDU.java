/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphic;

import hibernate.Bulk;
import java.util.ArrayList;
import java.util.List;
import org.snmp4j.smi.OID;


public class SelectPDU extends javax.swing.JFrame {

    MainFrame mother;
    public SelectPDU(MainFrame mother) {
        this.mother = mother;
        initComponents();
        if(mother.tablica[0] == 1)
            GetPDU.setSelected(true);
        else
            GetPDU.setSelected(false);
        if(mother.tablica[1] == 1)
            GetPDUNEXT.setSelected(true);
        else
            GetPDUNEXT.setSelected(false);
        if(mother.tablica[2] == 1)
            GetPDUBULK.setSelected(true);
        else
            GetPDUBULK.setSelected(false);
        gettBulkParams();
        GetOID.setText(printOid(mother.getOID()));
        NextOID.setText(printOid(mother.getnextOID()));
        BulkOID.setText(printOid(mother.getbulkOID()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GetPDU = new javax.swing.JCheckBox();
        GetPDUNEXT = new javax.swing.JCheckBox();
        GetPDUBULK = new javax.swing.JCheckBox();
        GetOID = new javax.swing.JTextField();
        NextOID = new javax.swing.JTextField();
        BulkOID = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        nonrepe = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        maxrepe = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        GetPDU.setText("GetPDU");

        GetPDUNEXT.setText("GetPDUNEXT");

        GetPDUBULK.setText("GetPDUBULK");

        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("non-repeaters:");

        jLabel2.setText("max-repetitions:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(GetPDU)
                        .addGap(26, 26, 26)
                        .addComponent(GetOID, javax.swing.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(GetPDUBULK)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BulkOID))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(GetPDUNEXT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(NextOID))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nonrepe, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(maxrepe, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GetPDU)
                    .addComponent(GetOID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GetPDUNEXT)
                    .addComponent(NextOID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GetPDUBULK)
                    .addComponent(BulkOID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(nonrepe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(maxrepe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        saveTable();
        //--------------------set's list in memory-------------------------------
        mother.setOID(stringtoOIDs(GetOID.getText().toString()));
        mother.setOIDnext(stringtoOIDs(NextOID.getText().toString()));
        mother.setOIDbulk(stringtoOIDs(BulkOID.getText().toString()));
        mother.updateGet(mother.getOID());
        
        if(GetPDUBULK.isSelected() == true)
        {
            if((nonrepe.getText().length() != 0) && (maxrepe.getText().length() != 0))           
            {  
                mother.saveBulkparams(new Bulk(Integer.parseInt(nonrepe.getText()),Integer.parseInt(maxrepe.getText())));
                mother.setBulkparams(new Bulk(Integer.parseInt(nonrepe.getText()),Integer.parseInt(maxrepe.getText())));
            }
            else
            {   mother.setBulkparams(new Bulk(0,1));}
        }                  
    }//GEN-LAST:event_jButton1ActionPerformed
    private void saveTable()
    {
        if(GetPDU.isSelected())
            mother.tablica[0] = 1;
        else
            mother.tablica[0] = 0;
         if(GetPDUNEXT.isSelected())
            mother.tablica[1] = 1;
        else
            mother.tablica[1] = 0;
          if(GetPDUBULK.isSelected())
            mother.tablica[2] = 1;
        else
            mother.tablica[2] = 0;
    }
    private void gettBulkParams()
    {                
        nonrepe.setText(Integer.toString(mother.getBulkparams().getNonrepeaters()));
        maxrepe.setText(Integer.toString(mother.getBulkparams().getMaxrepetitions()));             
    }
    private String printOid(List<OID> oids)
    {
        String text = "";
        for(OID oid: oids)
        {
            text += oid.toDottedString();
            text += ";";
        }
        return text;
    }
    private List<OID> stringtoOIDs(String text)
    {
        List<OID> oids = new ArrayList<OID>();
        String[] tabString = text.split(";");
        for(String s: tabString)
        {
            System.out.println(s.trim());
            oids.add(new OID(s));
        }
        return oids;
    }
            
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BulkOID;
    private javax.swing.JTextField GetOID;
    private javax.swing.JCheckBox GetPDU;
    private javax.swing.JCheckBox GetPDUBULK;
    private javax.swing.JCheckBox GetPDUNEXT;
    private javax.swing.JTextField NextOID;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField maxrepe;
    private javax.swing.JTextField nonrepe;
    // End of variables declaration//GEN-END:variables
}
