
package snmp_server;

import graphic.MainFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.UnknownHostException;
import javax.swing.JFrame;
import org.snmp4j.log.LogAdapter;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;

/**
 *
 * @author Tomek
 */
public class main {
     private static boolean _logging = false;
             private static LogAdapter logger = null;
     public static void main(String[] args) {
         
       
         
        // try{
            
     
        
            OID oid1 = new OID(".1.3.6.1.2.1.1.1.0"); // nazwa
            OID oid2 = new OID(".1.3.6.1.2.1.1.3.0");//uptime
            OID oid3 = new OID(".1.3.6.1.2.1.2.2.1.6"); //mac
            OID oid4 = new OID(".1.3.6.1.2.1.2.2.1.2"); //interface
            OID oid4_1 = new OID(".1.3.6.1.2.1.2.2.1.2.4"); //interface_4
            OID oid5 = new OID(".1.3.6.1.2.1.25.2.3.1.5"); //memory used
            OID oid6 = new OID(".1.3.6.1.4.1.14988.1.1.1.3.1.4.7"); //SSID
            OID oid7 = new OID(".1.3.6.1.4.1.14988.1.1.1.3.1.7.7");//frequency
            OID oid8 = new OID(".1.3.6.1.2.1.2.2.1.17.2"); //ether2 packets-out 
            OID oid9 = new OID(".1.3.6.1.2.1.2.2.1.16.2");//ether2 bytes-out
            OID oid10 = new OID(".1.3.6.1.2.1.2.2.1.2.2");//ether2 name
            OID oid11 = new OID("1.3.6.1.2.1.31.1.1.1.1");
            OID oid12 = new OID(".1.3.6.1.2.1.2.1"); //number of interface
            OID oid13 = new OID(".1.3.6.1.2.1.2.2.1.2");
            //------------------------------------------------------------------
            OID oids[] = new OID[]{oid9}; 
            OID oids2[] = new OID[]{oid1,oid2};
            OID oids3[] = new OID[]{oid13,oid3};
      
             TrapReceiver snmp4jTrapReceiver = new TrapReceiver();
             MainFrame p_frame = new MainFrame(snmp4jTrapReceiver);
             p_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             Toolkit zestaw = Toolkit.getDefaultToolkit();
             Dimension wymiary = zestaw.getScreenSize();
             int wysokosc = wymiary.height;
             int szerokosc = wymiary.width;
             p_frame.setLocation((szerokosc/2)-100, (wysokosc/2)-150);
            // p_frame.setOID(oids2);
           //  p_frame.setOIDnext(oids3);
             p_frame.show();
             p_frame.trapListen();
            
            
            
            
           // snmp.printRequest(oids3);
            
           // snmpv3.printValue(oid1);
           /** for (int i =0;i<100;i++)
               snmp.printRequest(oids);
            System.out.print("________________");**/
            //snmp2.printRequest(oids2);
         //  try{
          //  snmp.printTree(snmp.getTree(oid4));
            //snmp3.printUserRequest(oids3);
         //   snmp.listen();
         
          //  }catch(IOException e){}
       /** TrapReceiver snmp4jTrapReceiver = new TrapReceiver();
    try
    {
        UdpAddress udpaddress = new UdpAddress("10.1.0.12/162");//address od listen interface
        snmp4jTrapReceiver.listen(udpaddress);
    }
    catch (IOException e)
    {
      System.err.println("Error in Listening for Trap");
      System.err.println("Exception Message = " + e.getMessage());
    }**/
  }   
    
}
