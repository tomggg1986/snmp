package snmp_server;


import hibernate.Bulk;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;
import org.snmp4j.AbstractTarget;
import org.snmp4j.MessageDispatcher;
import org.snmp4j.MessageDispatcherImpl;
import org.snmp4j.PDU;
import org.snmp4j.ScopedPDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
//import org.snmp4j.security.AuthMD5;
import org.snmp4j.security.UsmUser;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;

public class SNMP /**implements CommandResponder**/ {

 private Snmp snmp = null;
 private Profil profil; 
 ThreadPool threadpool;
 MessageDispatcher mtDispatcher; 
 private AbstractTarget abTarget; 
 public  SNMP(Profil profil){
  try 
  {     
      long startTime = System.currentTimeMillis();
      this.threadpool =ThreadPool.create("DispatcherPool", 10);
      this.mtDispatcher = new MultiThreadedMessageDispatcher(threadpool,new MessageDispatcherImpl());
      // add message processing models
      mtDispatcher.addMessageProcessingModel(new MPv1());
      mtDispatcher.addMessageProcessingModel(new MPv2c());
      mtDispatcher.addMessageProcessingModel(new MPv3());
      this.profil = profil;
      System.out.println(this.profil.toString());
      snmp = this.createSnmpSession();   
  }catch (IOException e) {
   e.printStackTrace();
   System.out.println(e.getMessage());  
  }
 } 
  private  Snmp createSnmpSession() throws IOException 
  {
      Snmp snmp;
      TransportMapping transport = new DefaultUdpTransportMapping();
      transport.listen();
      snmp = new Snmp(mtDispatcher, transport);
      if(profil.getVersionInt() == 3)   
      {
          USM usm = new USM(SecurityProtocols.getInstance(), new
          OctetString(MPv3.createLocalEngineID()), 0);
          SecurityModels.getInstance().addSecurityModel(usm);
          UsmUser user;
          if(profil.getPrivProtocol() != null)
          {
              user = new UsmUser(
              profil.getSecurityName(),
              profil.getAuthProtocol().getID(),
              profil.getAuthPassword(),
              profil.getPrivProtocol().getID(), 
              profil.getPrivPassword());
              System.out.println("User"+user.toString());
         }
         else if(profil.getAuthProtocol() != null){
              user = new UsmUser(
              profil.getSecurityName(),
              profil.getAuthProtocol().getID(),
              profil.getAuthPassword(),
              null, 
              null);
        }
       else
       {
           user = new UsmUser(
           profil.getSecurityName(),
           null,
           null,
           null, 
           null);
       }
       System.out.println(snmp.toString());
     snmp.getUSM().addUser(profil.getSecurityName(), user);
   }
   return snmp;
 }
 public void setTargetAddress(UdpAddress address){
   abTarget = profil.getTarget();
   System.out.println(profil.getVersionInt());
   abTarget.setAddress(address);
   abTarget.setVersion(profil.getVersionInt());// org.snmp4j.mp.*;
   abTarget.setSecurityLevel(profil.getSecurityLevel().getSnmpValue());
   abTarget.setSecurityName(profil.getSecurityName());
 }
 public void listen()
 {
     try
     {
     snmp.listen();
     }
     catch(Exception e)
     {
         e.printStackTrace();
         System.out.println("Listen error:"+e.getMessage());
     }
     
 }
 /**public void stopListen()
 {
     try
     {
        
     } 
     catch(IOException e)
     {
         e.printStackTrace();
         System.out.println("Listen error:"+e.getMessage());
     }
     
 }**/

 public Vector<VariableBinding> getUDP(OID oid)
 {
     Vector v = new Vector();
     try
     {   
         PDU pdu = new ScopedPDU();
         PDU pduresponse = new PDU();
         VariableBinding VB = new VariableBinding();
       //  pdu.setContextName(contextName);
         pdu.setType(PDU.GET);
         pdu.setMaxRepetitions(10);
         pdu.add(new VariableBinding(oid));
         ResponseEvent response = snmp.send(pdu, abTarget);
         System.out.println(response.getRequest());
          if (response.getError() != null) //respondpdu != null
        {
            v = response.getResponse().getVariableBindings();//getResponse() zwraca PDU getVariableBindings() zwraca Vector z VariableBindings
        }
          else
          {
              System.out.println("Error:" + response.getError().toString());
          }
            
    }catch(IOException e){
        e.printStackTrace();
    } 
       return v; 
 }
  public Vector<VariableBinding> getUDP(List<OID> oids)
 {
     Vector v = new Vector();
     try
     {  
         PDU pdu;
         if(profil.getVersionInt() == SnmpConstants.version3)
         {
              ScopedPDU pdus = new ScopedPDU();
              pdus.setContextName(profil.getContextName());
              pdu = pdus;
         }else
         {
              pdu = new PDU();    
         }
         VariableBinding VB = new VariableBinding();
         pdu.setType(PDU.GET);
         pdu.setMaxRepetitions(40);
         for(OID oid : oids)
         {
         pdu.add(new VariableBinding(oid));
         }
         ResponseEvent response = snmp.send(pdu, abTarget);  
         if(response.getResponse() !=null)//respondpdu != null  getResponse().getErrorStatus() == 0
        {
             System.out.println("Error:"+response.getError());
             System.out.println(
                      "Error Status: "+response.getResponse().getErrorStatus()
                      +" Error Status Text: "+response.getResponse().getErrorStatusText()
                      +" Error Index: "+response.getResponse().getErrorIndex());
             v = response.getResponse().getVariableBindings();//getResponse() zwraca PDU getVariableBindings() zwraca Vector z VariableBindings
        }
         else
          {
              System.out.println(
                     " Null response\nError Status: "+response.getError());
              try{
                  Variable var = new OctetString("Response not arrive");
                  v.add(new VariableBinding(new OID("1.1.1.1"),var));
              }catch(Exception e)
                      {
                          System.out.println(e.getStackTrace());
                      }
          }
            
    }catch(IOException e){
        e.printStackTrace();
    } 
       return v; 
 }
   public Vector<VariableBinding> getNextRequest(List<OID> oidnext)
 {
     Vector v = new Vector();
     Vector vtemp = new Vector(); 
     try
     {  
         PDU pdu;
         if(profil.getVersionInt() == SnmpConstants.version3)
         {
              ScopedPDU pdus = new ScopedPDU();
              pdus.setContextName(profil.getContextName());
              pdu = pdus;
         }else
         {
              pdu = new PDU();    
         }
          for(OID oid : oidnext)
         {
              pdu.add(new VariableBinding(oid));     
         }
         String test = pdu.get(0).getOid().toDottedString(); 
         int size = test.length();
         VariableBinding vb = new VariableBinding();
         pdu.setType(PDU.GETNEXT);
         int i = 0;
         boolean r = true;      
         while(r)
         {
              ResponseEvent response = snmp.send(pdu, abTarget); 
              if(response.getResponse() != null)
              {
                  vtemp = response.getResponse().getVariableBindings();
                  pdu.clear();
                  for(int j =0;j<oidnext.size();j++)
                  {
                     pdu.add(new VariableBinding(response.getResponse().get(j).getOid()));
                  }        
                 
                  vb = (VariableBinding)vtemp.get(0);
                  String equals = vb.getOid().toDottedString();
                  equals = equals.substring(0, size);
                  if(!test.equals(equals))
                     { r = false;}
                  else
                  {   
                      r = true;
                      test = equals;
                      v.addAll(vtemp);
                  } 
                  }else
                  {
                      Variable var = new OctetString("Response not arrive");
                      v.add(new VariableBinding(new OID("1.1.1.1"),var));
                      System.out.println("Next function generate: Response Null");
                      r = false;
                  }
         }     
    }catch(IOException e){
        e.printStackTrace();
    } 
       return v; 
 }
   public Vector<VariableBinding> getBulkRequest(List<OID> oids,Bulk bulkparams)
 {
     Vector v = new Vector();
     try
     {  
         PDU pdu;
         if(profil.getVersionInt() == SnmpConstants.version3)
         {
              ScopedPDU pdus = new ScopedPDU();
              pdus.setContextName(profil.getContextName());
              pdu = pdus;
         }else
         {
              pdu = new PDU();    
         }
         VariableBinding VB = new VariableBinding();
         pdu.setType(PDU.GETBULK);
         pdu.setNonRepeaters(bulkparams.getNonrepeaters());
         pdu.setMaxRepetitions(bulkparams.getMaxrepetitions());
         for(OID oid : oids)
         {
         pdu.add(new VariableBinding(oid));
         }
         ResponseEvent response = snmp.send(pdu, abTarget);
        
         if(response.getResponse() !=null)//respondpdu != null  getResponse().getErrorStatus() == 0
         {
             System.out.println(response.getResponse().getVariableBindings().toString());
             System.out.println("Error:"+response.getError());
             System.out.println(
                      "Error Status: "+response.getResponse().getErrorStatus()
                      +" Error Status Text: "+response.getResponse().getErrorStatusText()
                      +" Error Index: "+response.getResponse().getErrorIndex());
             v = response.getResponse().getVariableBindings();//getResponse() zwraca PDU getVariableBindings() zwraca Vector z VariableBindings
         }
         else
          {
              Variable var = new OctetString("Response not arrive");
              v.add(new VariableBinding(new OID("1.1.1.1"),var));
              System.out.println("Next function generate: Response Null");
          }
            
    }catch(IOException e){
        e.printStackTrace();
    } 
       return v; 
 }
   public Vector<VariableBinding> setRequest(Vector<VariableBinding> vbs) throws Exception
   {
       Vector<VariableBinding> responList = new Vector<VariableBinding>();
       PDU pdu;
         if(profil.getVersionInt() == SnmpConstants.version3)
         {
              ScopedPDU pdus = new ScopedPDU();
              pdus.setContextName(profil.getContextName());
              pdu = pdus;
         }else
         {
              pdu = new PDU();    
         }
         pdu.setType(PDU.SET);
         pdu.addAll(vbs);
      
         ResponseEvent response = snmp.set(pdu, abTarget);
            if(response != null)//respondpdu != null  getResponse().getErrorStatus() == 0
        {
             System.out.println(response.getResponse().getVariableBindings().toString());
             System.out.println("Error:"+response.getError());         
             System.out.println(
                      "Error Status: "+response.getResponse().getErrorStatus()
                      +" Error Status Text: "+response.getResponse().getErrorStatusText()
                      +" Error Index: "+response.getResponse().getErrorIndex());
             responList.addAll((Vector<VariableBinding>) response.getResponse().getVariableBindings());//getResponse() zwraca PDU getVariableBindings() zwraca Vector z VariableBindings
             System.out.println("Klasa SNMP"+responList);
        }
         else
          {
              System.out.println("elseKlasa SNMP"+responList);
              System.out.println("GetError:"+response.getError());
              System.out.println(
                     "Error Status: "+response.getResponse().getErrorStatus()
                     +"Error Status Text: "+response.getResponse().getErrorStatusText()
                     +"Error Index: "+response.getResponse().getErrorIndex());
          }       
       return responList;      
   }
    public void printTree(Vector<VariableBinding> vector)//print respond from getTree
    {  
     
            System.out.println(vector.size());
            for(int i =0; i<vector.size();i++ )
            {
                System.out.println(vector.get(i).getVariable().toString());
            }
    }
     public void printValue(OID[] oids)
 {
     try
     {
        ScopedPDU pdu = new ScopedPDU();
        for(OID oid : oids)
        {
            pdu.add(new VariableBinding(oid));
        }     
          pdu.setContextName(profil.getContextName());
          pdu.setType(PDU.GET);
          ResponseEvent response = snmp.send(pdu, abTarget);
         // System.out.println(snmp.getUSM().getUserTable().getUser(securityName));
          if(response!= null)
        {
            System.out.println(response.getResponse().size());
            for(int i=0;i<response.getResponse().size(); i++)
            {
                  System.out.println(response.getResponse().get(i).getVariable().toString());// .getResponse(i) - zwraza UDP .getVariable - zwraca VariableBinding
                  System.out.println("........................................................");
                  //System.out.println(response.getRequest());
                  //System.out.println(response.getError());
                  //System.out.println("The cost time for snmpv3:" + (System.currentTimeMillis() - startTime));
            }
        }
     }
    catch (IOException e) {
         e.printStackTrace();
         System.out.println(e.getMessage());
  }     
 }
}