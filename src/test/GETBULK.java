/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.util.Vector;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

/**
 *
 * @author Tomek
 */
public class GETBULK {
    public static void main(String[] args) throws IOException, InterruptedException {
    Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
    snmp.listen();

    CommunityTarget target = new CommunityTarget();
    target.setCommunity(new OctetString("public"));
    target.setVersion(SnmpConstants.version2c);
    target.setAddress(new UdpAddress("10.1.0.99/161"));
    target.setTimeout(3000);    //3s
    target.setRetries(1);

    PDU pdu = new PDU();
    pdu.setType(PDU.GETBULK);
    pdu.setMaxRepetitions(50); 
    pdu.setNonRepeaters(0);
    pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.31.1.1.1.1"))); 

    ResponseEvent responseEvent = snmp.send(pdu, target);
    PDU response = responseEvent.getResponse();

    if (response == null) {
        System.out.println("TimeOut...");
    } 
    else 
    {
        if (response.getErrorStatus() == PDU.noError) 
        {
            Vector<? extends VariableBinding> vbs = response.getVariableBindings();
            for (VariableBinding vb : vbs) {
                System.out.println(vb + " ," + vb.getVariable().getSyntaxString());
            }
        } 
        else 
        {
            System.out.println("Error:" + response.getErrorStatusText());
        }
    }
}
}
