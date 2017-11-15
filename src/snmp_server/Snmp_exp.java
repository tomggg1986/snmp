    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snmp_server;

import java.io.IOException;
import java.util.Vector;
import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.CommunityTarget;
import org.snmp4j.MessageDispatcher;
import org.snmp4j.MessageDispatcherImpl;
import org.snmp4j.MessageException;
import org.snmp4j.PDU;
import org.snmp4j.Session;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.UserTarget;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.log.LogFactory;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.mp.StateReference;
import org.snmp4j.mp.StatusInformation;
import org.snmp4j.security.AuthMD5;
import org.snmp4j.security.Priv3DES;
import org.snmp4j.security.PrivDES;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.security.UsmUser;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.tools.console.SnmpRequest;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;

/**
 *
 * @author Tomek
 */
public class Snmp_exp implements Session, CommandResponder {
    Snmp snmp;
    CommunityTarget target;
    UserTarget userTarget;
    Address address;
    TransportMapping transport;
    ThreadPool threadpool;
    MessageDispatcher mtDispatcher;
    USM usm;
    
    public Snmp_exp(String address)
    {
        super();
        this.address = GenericAddress.parse(address);
        this.target = createTarget(); 
        this.threadpool =ThreadPool.create("DispatcherPool", 10);
        this.mtDispatcher = new MultiThreadedMessageDispatcher(threadpool,new MessageDispatcherImpl());
        // add message processing models
        mtDispatcher.addMessageProcessingModel(new MPv1());
        mtDispatcher.addMessageProcessingModel(new MPv2c());
        // add all security protocols
        SecurityProtocols.getInstance().addDefaultProtocols();
        SecurityProtocols.getInstance().addPrivacyProtocol(new Priv3DES());
        
       
        try
        {
            
            this.transport = createTransportUdp();
            snmp = new Snmp(mtDispatcher,transport);
            snmp.addCommandResponder(this);
          
            transport.listen();
           
        }catch(IOException e)
        {
            throw new RuntimeException(e);
        }
        
    }
    public void setAuth()
    {
        usm = new USM(SecurityProtocols.getInstance(),
                     new OctetString(MPv3.createLocalEngineID()), 0);
                     SecurityModels.getInstance().addSecurityModel(usm);
        UsmUser umsUser = new UsmUser(new OctetString("MD5DES"),AuthMD5.ID,
                                      new OctetString("ptokuptoku"),
                                           PrivDES.ID,
                                      new OctetString("ptokuptoku"));
        
       usm.addUser(new OctetString("MD5DES"),umsUser);
       
    }
    //--------------------------------------------------------------------------
    public synchronized void listen() 
    {
        try
    {
      this.wait();
    }
    catch (InterruptedException ex)
    {
      Thread.currentThread().interrupt();
    }
    }
    //--------------------------------------------------------------------------
     public synchronized void processPdu(CommandResponderEvent cmdRespEvent)
  {
    System.out.println("Received PDU...");
    PDU pdu = cmdRespEvent.getPDU();
    if (pdu != null)
    {

      System.out.println("Trap Type = " + pdu.getType());
      System.out.println("Variable Bindings = " + pdu.getVariableBindings());
      int pduType = pdu.getType();
      if ((pduType != PDU.TRAP) && (pduType != PDU.V1TRAP) && (pduType != PDU.REPORT)
      && (pduType != PDU.RESPONSE))
      {
        pdu.setErrorIndex(0);
        pdu.setErrorStatus(0);
        pdu.setType(PDU.RESPONSE);
        StatusInformation statusInformation = new StatusInformation();
        StateReference ref = cmdRespEvent.getStateReference();
        try
        {
          System.out.println(cmdRespEvent.getPDU());
          cmdRespEvent.getMessageDispatcher().returnResponsePdu(cmdRespEvent.getMessageProcessingModel(),
          cmdRespEvent.getSecurityModel(), cmdRespEvent.getSecurityName(), cmdRespEvent.getSecurityLevel(),
          pdu, cmdRespEvent.getMaxSizeResponsePDU(), ref, statusInformation);
        }
        catch (MessageException ex)
        {
          System.err.println("Error while sending response: " + ex.getMessage());
          LogFactory.getLogger(SnmpRequest.class).error(ex);
        }
      }
    }
  }
    private CommunityTarget createTarget()
    {
        CommunityTarget t = new CommunityTarget();
        t.setCommunity(new OctetString("public"));
        t.setAddress(address);
        t.setRetries(2);
        t.setTimeout(1500);
        t.setVersion(SnmpConstants.version2c);
        return t;      
    }
    private TransportMapping createTransportUdp() throws IOException
    {
          TransportMapping t = new DefaultUdpTransportMapping(); 
          return t;
    }      
    public void printRequest(OID oids[]) throws IOException
    {
        ResponseEvent event;
        PDU pdu = new PDU();
        for(OID oid : oids)
        {
            pdu.add(new VariableBinding(oid));
        }     
        event = this.snmp.send(pdu, target,null);
        if(event != null)
        {
            System.out.println(event.getResponse().size());
            for(int i=0;i<event.getResponse().size(); i++){
	        System.out.println(event.getResponse().get(i).getVariable().toString());
                }  
        }       
    }
     public void printUserRequest(OID oids[]) throws IOException
    {
        ResponseEvent event;
        PDU pdu = new PDU();
        for(OID oid : oids)
        {
            pdu.add(new VariableBinding(oid));
        }     
        event = this.snmp.send(pdu, userTarget,null);
        if(event != null)
        {
            System.out.println(event.getResponse().size());
            for(int i=0;i<event.getResponse().size(); i++){
	        System.out.println(event.getResponse().get(i).getVariable().toString());
                }  
        }       
    }
    public Vector<VariableBinding> getTree(OID oid) throws IOException
    {
        Vector v = new Vector();
        ResponseEvent event;
        PDU pdu = new PDU();
        pdu.setType(PDU.GETBULK);
        pdu.setMaxRepetitions(20);
        
        
        pdu.add(new VariableBinding(oid));
        event = this.snmp.send(pdu, target);
      //  PDU respondpdu = event.getResponse(); 
        if(event != null)//respondpdu != null
        {
            v = event.getResponse().getVariableBindings();//getResponse() zwraca PDU getVariableBindings() zwraca Vector z VariableBindings
        }
        return v;       
    }
    public void printTree(Vector<VariableBinding> vector)//print respond from getTree
    {           
            System.out.println(vector.size());
            for(int i =0; i<vector.size();i++ )
            {
                System.out.println(vector.get(i));
            }
    }

    @Override
    public void close() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ResponseEvent send(PDU pdu, Target target) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void send(PDU pdu, Target target, Object o, ResponseListener rl) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ResponseEvent send(PDU pdu, Target target, TransportMapping tm) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void send(PDU pdu, Target target, TransportMapping tm, Object o, ResponseListener rl) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void cancel(PDU pdu, ResponseListener rl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
