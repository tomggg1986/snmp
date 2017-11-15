/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package snmp_server;

import java.net.InetAddress;
import org.snmp4j.smi.OctetString;

public class LocalAddress 
{
     InetAddress me ; 
     
     public LocalAddress()throws java.net.UnknownHostException
     {
         me = InetAddress.getLocalHost();
     }
     public void printLocalAddress() 
     {       
         System.out.println(me.getHostAddress());
     }
     public OctetString getLacalAddress()
     {
         OctetString s = new OctetString(me.getHostAddress());
         return s;
     }
     
}
