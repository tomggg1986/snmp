package graphic;

/**
 *
 * @author Tomek
 */
import hibernate.Bulk;
import hibernate.Bulkoid;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.snmp4j.security.AuthGeneric;
import org.snmp4j.security.AuthMD5;
import org.snmp4j.security.AuthSHA;
import org.snmp4j.security.PrivDES;
import org.snmp4j.security.PrivacyProtocol;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import snmp_server.LocalAddress;
import snmp_server.Profil;
import snmp_server.SNMP;
import snmp_server.TrapReceiver;
import hibernate.HibernateUtil;
import hibernate.Device;    
import hibernate.Getoid;
import hibernate.Nextoid;
import hibernate.Profils;
import javax.swing.JFrame;
import org.hibernate.Session;
import snmp_server.ProfilFactory;


public class MainFrame extends javax.swing.JFrame {

    protected List<OID> getoids; 
    protected List<OID> nextoids; 
    protected List<OID> bulkoids;
    protected int[] tablica = new int[12];
    protected Bulk bulkparams;
    
    TrapReceiver trap;
    List<Profil> profillist;
    HashMap<OctetString, AuthGeneric> authProtocols;
    HashMap<OctetString, PrivacyProtocol> privProtocols;
    List devices;
    public MainFrame(TrapReceiver trap) {
       
        initComponents(); 
        getoids = new ArrayList<OID>();
        nextoids = new ArrayList<OID>();
        bulkoids = new ArrayList<OID>();
        this.trap = trap;
        this.trap.setField(trapPane);
        trapPane.setEditable(false);
        authProtocols =new HashMap();
        privProtocols = new HashMap();
        authProtocols.put(new OctetString("AuthMD5"), new AuthMD5());
        authProtocols.put(new OctetString("AuthSHA"), new AuthSHA());
        privProtocols.put(new OctetString("PrivDES"),new PrivDES());
        profillist = new ArrayList<Profil>();
        this.getOIDS();
        this.setprofillist();
        this.setProfilsTab();
        this.setDeviceTab();  
        this.loadBulkparams();
        tablica[0] = 1;
        tablica[1] = 1;
        tablica[2] = 0;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        discovery_Button = new javax.swing.JButton();
        addresText = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        discoveryText = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        trapPane = new javax.swing.JTextPane();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        select_Button = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        repeat = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        sendRequest_Item = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        addProfil_Item = new javax.swing.JMenuItem();
        addDevice_Item = new javax.swing.JMenuItem();
        setPDU_Item = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SNMP Menager by Tomasz Gołębiowski");

        discovery_Button.setLabel("Discovery");
        discovery_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discovery_ButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Address:");
        jLabel1.setName("addrestext"); // NOI18N

        discoveryText.setColumns(20);
        discoveryText.setRows(5);
        jScrollPane1.setViewportView(discoveryText);

        jLabel2.setText("Trap receiver:");

        jScrollPane3.setViewportView(trapPane);

        jLabel3.setText("Select Profile:");

        jLabel4.setText("Device:");

        select_Button.setText("Select");
        select_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                select_ButtonActionPerformed(evt);
            }
        });

        jLabel5.setText("Repeat:");

        repeat.setText("1");

        jMenu1.setText("File");

        sendRequest_Item.setText("SetValue");
        sendRequest_Item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendRequest_ItemActionPerformed(evt);
            }
        });
        jMenu1.add(sendRequest_Item);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        addProfil_Item.setText("Add profil");
        addProfil_Item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProfil_ItemActionPerformed(evt);
            }
        });
        jMenu2.add(addProfil_Item);

        addDevice_Item.setText("Add Device");
        addDevice_Item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDevice_ItemActionPerformed(evt);
            }
        });
        jMenu2.add(addDevice_Item);

        setPDU_Item.setText("SetPDUs");
        setPDU_Item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setPDU_ItemActionPerformed(evt);
            }
        });
        jMenu2.add(setPDU_Item);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(repeat, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(discovery_Button)
                                .addGap(34, 34, 34)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(addresText, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(select_Button)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(discovery_Button)
                    .addComponent(addresText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(select_Button))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(repeat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
//---------------------------start sending request------------------------------
    private void discovery_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discovery_ButtonActionPerformed
        UdpAddress udpaddress = new UdpAddress(addresText.getText().toString()+"/161");
        System.out.println("lista: "+jComboBox1.getSelectedIndex());
        SNMP  snmp = new SNMP(profillist.get(jComboBox1.getSelectedIndex()));      
        snmp.setTargetAddress(udpaddress);
        Font fontNormal = new Font("Verdana", Font.PLAIN, 12);
        discoveryText.setFont(fontNormal);
        System.out.println(udpaddress.toString());
        snmp.listen();
        int repeatint = Integer.parseInt(repeat.getText().toString());
        int  licznik =0;
        while(licznik<repeatint)
        {
             discoveryText.setText("");
            //------------------------------PDU_GET---------------------------------
            if(tablica[0] == 1)
            {
                discoveryText.append("\ngetRequest:\n\n");
            for(int i =0;i < snmp.getUDP(getoids).size(); i++)
            {
                 discoveryText.append(snmp.getUDP(getoids).get(i).toString()+"\n");
            } 
            }
            //------------------------------PDU-GETNEXT-----------------------------
            if(tablica[1] == 1)
            {
            discoveryText.append("\ngetNextRequest:\n\n");     
            Vector v = new Vector(snmp.getNextRequest(nextoids));
            VariableBinding vb = new VariableBinding();   
            for(int i =0;i < v.size() ; i++)
            {
                 vb = (VariableBinding) v.get(i);
                 discoveryText.append(vb.toValueString()+"\n");  
                 System.out.println(vb.toString());
            }
            }
            //-------------------PDU_GETBULK----------------------------------
            if(tablica[2] == 1)
            {
            discoveryText.append("\ngetBulkRequest:\n\n");
            Vector v2 = new Vector(snmp.getBulkRequest(bulkoids, bulkparams));
            VariableBinding vb2 = new VariableBinding();   
            for(int i =0;i < v2.size(); i++)
            {
                 vb2 = (VariableBinding) v2.get(i);
                 discoveryText.append(vb2.toValueString()+"\n");
            }
            }
            licznik++;
            try
            {
                 Thread.sleep(0); //czas w milisekundach 1/30 sekundy
            }
catch(InterruptedException e)
{
}
        }
    }//GEN-LAST:event_discovery_ButtonActionPerformed
//-----------------------open window to add new profil--------------------------
    private void addProfil_ItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProfil_ItemActionPerformed
        ProfilFrame p_frame = new ProfilFrame(this);
       // p_frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Toolkit zestaw = Toolkit.getDefaultToolkit();
        Dimension wymiary = zestaw.getScreenSize();
        int wysokosc = wymiary.height;
        int szerokosc = wymiary.width;
        p_frame.setLocation((szerokosc/2)-100, (wysokosc/2)-150);
        p_frame.show();       
    }//GEN-LAST:event_addProfil_ItemActionPerformed
//---------------------------select device from databases-----------------------
    private void select_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_select_ButtonActionPerformed
       String s = jComboBox2.getSelectedItem().toString();
       Device d;
       boolean bool = true;
       int i = 0;
       while(bool)
       {
           d = (Device) devices.get(i);
           if(s.equals(d.getName()))
           {
               addresText.setText(d.getIpaddres());
               bool = false;
           }
           else
           {
               i++;
           }
       }
        
    }//GEN-LAST:event_select_ButtonActionPerformed
//---------------------------Open Window to add new device----------------------
    private void addDevice_ItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addDevice_ItemActionPerformed
        DeviceFrame deviceframe = new DeviceFrame(this);
        deviceframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Toolkit zestaw = Toolkit.getDefaultToolkit();
        Dimension wymiary = zestaw.getScreenSize();
        int wysokosc = wymiary.height;
        int szerokosc = wymiary.width;
        deviceframe.setLocation((szerokosc/2)-100, (wysokosc/2)-150);
        deviceframe.show();
    }//GEN-LAST:event_addDevice_ItemActionPerformed
//-----------------------------Open window to config PDU------------------------ 
    private void setPDU_ItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setPDU_ItemActionPerformed
        SelectPDU spdu = new SelectPDU(this);
        spdu.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Toolkit zestaw = Toolkit.getDefaultToolkit();
        Dimension wymiary = zestaw.getScreenSize();
        int wysokosc = wymiary.height;
        int szerokosc = wymiary.width;
        spdu.setLocation((szerokosc/2)-100, (wysokosc/2)-150);
        spdu.show();
    }//GEN-LAST:event_setPDU_ItemActionPerformed
//----------------------------Open window to send setrequest PDU----------------
    private void sendRequest_ItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendRequest_ItemActionPerformed
        SetValue set = new SetValue(this);
        set.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Toolkit zestaw = Toolkit.getDefaultToolkit();
        Dimension wymiary = zestaw.getScreenSize();
        int wysokosc = wymiary.height;
        int szerokosc = wymiary.width;
        set.setLocation((szerokosc/2)-100, (wysokosc/2)-150);
        set.show();
    }//GEN-LAST:event_sendRequest_ItemActionPerformed
  
//------------------------getters---------------------------------------
    public HashMap<OctetString, AuthGeneric> getAuthProtocols() {
        return authProtocols;
    }

    public HashMap<OctetString, PrivacyProtocol> getPrivProtocols() {
        return privProtocols;
    } 

    public List<Profil> getProfils() {
        return profillist;
    }
    public List<OID> getOID()
    {
        return getoids;
    }
    public List<OID> getnextOID()
    {
        return nextoids;
    }
    public List<OID> getbulkOID()
    {
        return bulkoids;
    }
    
 //----------------------setters---------------------------------------

    public void setAuthProtocols(HashMap<OctetString, AuthGeneric> authProtocols) {
        this.authProtocols = authProtocols;
    }

    public void setPrivProtocols(HashMap<OctetString, PrivacyProtocol> privProtocols) {
        this.privProtocols = privProtocols;
    }

    public void setProfils(List<Profil> profils) {
        this.profillist = profils;
    }
    
      public void setOID(List<OID> oids)
   {
        this.getoids = oids;
   }
   public void setOIDnext(List<OID> oidnext)
   {
        this.nextoids = oidnext;
   }
    public void setOIDbulk(List<OID> oidbulk)
   {
        this.bulkoids = oidbulk;
   } 
    public Bulk getBulkparams() {
        return bulkparams;
    }
    public SNMP getSNMP()
    {
        SNMP snmp = new SNMP(profillist.get(jComboBox1.getSelectedIndex())); 
        snmp.setTargetAddress(new UdpAddress(addresText.getText().toString()+"/161"));
        return snmp;
    }
    public void setBulkparams(Bulk bulkparams) {
        this.bulkparams = bulkparams;
    }
    //---------------------------read from databases----------------------------
   protected void  loadBulkparams()
   {
        boolean local = false;      
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();        
        if(session.getTransaction().isActive() == false )
        { session.beginTransaction(); local = true;}
         if(session.createQuery("from Bulk").list().size() !=0)
             setBulkparams((Bulk)session.createQuery("from Bulk").list().get(0));
         else
             setBulkparams(new Bulk(0,1));
   }
   public void setDeviceTab()
   {
       jComboBox2.removeAllItems();
        boolean local = false;      
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();        
        if(session.getTransaction().isActive() == false )
        { session.beginTransaction(); local = true;}
        
        devices = session.createQuery("from Device").list();
        for(int i =0;i<devices.size();i++)
        {
        Device dev =(Device) devices.get(i);
        jComboBox2.addItem(dev.getName());
        }
   }
   protected void setProfilsTab() 
   {   
       jComboBox1.removeAllItems();
       Iterator i = profillist.iterator();
       while(i.hasNext())
       {
          Profil p = (Profil)i.next(); 
          jComboBox1.addItem(p.getName()); 
       }
    
   }
   protected void setprofillist()
   {
        List<Profils> plist = new ArrayList<Profils>();
        boolean local = false;      
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();        
        if(session.getTransaction().isActive() == false )
        { session.beginTransaction(); local = true;}
        plist = session.createQuery("from Profils").list();
        Iterator i = plist.iterator();
        while(i.hasNext())
        {
            Profils d = (Profils)i.next();
            Profil prof = new Profil(
                ProfilFactory.addName(new OctetString(d.getName())),
                ProfilFactory.addVersion(d.getVersion()),
                ProfilFactory.addSecurityName(new OctetString(d.getSecurityname())),              
                ProfilFactory.addAuthProtocol(new OctetString(d.getAuthprot()),this.getAuthProtocols()),
                ProfilFactory.addAuthPassword(new OctetString(d.getAuthpass())),
                ProfilFactory.addPrivProtocol(new OctetString(d.getPrivprot()),this.getPrivProtocols()),
                ProfilFactory.addPrivPassword(new OctetString(d.getPrivpass()))
            );
        profillist.add(prof);    
        };
   }
   public void getOIDS()
   {
       List getoid;
       List nextoid;
       List bulkoid;
       boolean local = false;      
       Session session = HibernateUtil.getSessionFactory().getCurrentSession();        
       if(session.getTransaction().isActive() == false )
       { session.beginTransaction(); local = true;}
        
        getoid = session.createQuery("from Getoid").list();
        nextoid = session.createQuery("from Nextoid").list();
        bulkoid = session.createQuery("from Bulkoid").list();
        System.out.println("getoid size:"+getoid.size());
        for(int i = 0;i<getoid.size();i++)
        {         
            Getoid goid = (Getoid)getoid.get(i);
            getoids.add(new OID(goid.getOid()));
        }
        for(int i = 0;i<nextoid.size();i++)
        {
            Nextoid noid = (Nextoid)nextoid.get(i);
            nextoids.add(new OID(noid.getOid()));
        }
        for(int i = 0;i<bulkoid.size();i++)
        {
            Bulkoid boid = (Bulkoid)bulkoid.get(i); 
            bulkoids.add(new OID(boid.getOid()));
        }
   }
   //--------------------------------Save to databases--------------------------
   protected void saveGetOID(List<OID> oids)
   {
       // List<Getoid> getoids = new ArrayList<Getoid>();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        for(OID oid : oids)
        {
            session.save(new Getoid(oid.toDottedString()));
        }
        session.getTransaction().commit();
   }
   protected void saveNextOID(List<OID> oids)
   {
       // List<Getoid> getoids = new ArrayList<Getoid>();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        for(OID oid : oids)
        {
            session.save(new Nextoid(oid.toDottedString()));
        }
        session.getTransaction().commit();
   }
   protected void saveBulkOID(List<OID> oids)
   {
       // List<Getoid> getoids = new ArrayList<Getoid>();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        for(OID oid : oids)
        {
            session.save(new Bulkoid(oid.toDottedString()));
        }
        session.getTransaction().commit();
   }
    protected void saveBulkparams(Bulk bulk)
   {
       Session session = HibernateUtil.getSessionFactory().getCurrentSession(); 
         if(session.getTransaction().isActive() == false )
         { session.beginTransaction();}
        // Bulk b =(Bulk) session.createQuery("from Bulk").list().get(0);
        // b.setMaxrepetitions( bulk.getMaxrepetitions());
        // b.setNonrepeaters(bulk.getNonrepeaters());
         bulk.setId(1);
         System.out.println("id:"+bulk.getId());
         session.update(bulk);
         session.getTransaction().commit();        
   }
    //----------------update oid databases for get PDU-------------------------
   protected void updateGet(List<OID> oids)
   {
       Session session = HibernateUtil.getSessionFactory().getCurrentSession(); 
       if(session.getTransaction().isActive() == false )
         { session.beginTransaction();}
       List<Getoid> goid= session.createQuery("from Getoid").list();
       int max;
       int oidsmax = oids.size();
       int bazamax = goid.size();
       if(goid.size() <= oids.size())
           max = oids.size();
       else
           max = goid.size();
       for(int i = 0; i<max;i++)
       {
           if((i < bazamax) &&(i< oidsmax))
               goid.get(i).setOid(oids.get(i).toDottedString());
           else if(bazamax < oidsmax)
               session.save(new Getoid(oids.get(i).toDottedString()));
           else if(bazamax > oidsmax)
               session.delete(goid.get(i));
       }
       session.getTransaction().commit();
   }
   public void trapListen()
   {         
        try
        {
            LocalAddress la = new LocalAddress();
            UdpAddress udpaddress = new UdpAddress(la.getLacalAddress()+"/162");
            this.trap.listen(udpaddress);
        }catch(IOException e){
            System.err.println("Error in Listening for Trap");
            System.err.println("Exception Message = " + e.getMessage());
        }
   }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addDevice_Item;
    private javax.swing.JMenuItem addProfil_Item;
    private javax.swing.JTextField addresText;
    private javax.swing.JTextArea discoveryText;
    private javax.swing.JButton discovery_Button;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField repeat;
    private javax.swing.JButton select_Button;
    private javax.swing.JMenuItem sendRequest_Item;
    private javax.swing.JMenuItem setPDU_Item;
    private javax.swing.JTextPane trapPane;
    // End of variables declaration//GEN-END:variables
}
