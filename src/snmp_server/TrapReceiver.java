package snmp_server;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.MessageDispatcher;
import org.snmp4j.MessageDispatcherImpl;
import org.snmp4j.MessageException;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.log.LogFactory;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.StateReference;
import org.snmp4j.mp.StatusInformation;
import org.snmp4j.security.Priv3DES;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.smi.TcpAddress;
import org.snmp4j.smi.TransportIpAddress;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.tools.console.SnmpRequest;
import org.snmp4j.transport.AbstractTransportMapping;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;

public class TrapReceiver implements CommandResponder
{
  JTextPane TextPane;
  List<VariableBinding> vbs; 
  int color = 0;
  //Highlighter hilite;
 // DefaultHighlighter.DefaultHighlightPainter whitePainter = new DefaultHighlighter.DefaultHighlightPainter(Color.WHITE);
 // DefaultHighlighter.DefaultHighlightPainter grayPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.GRAY);
  public TrapReceiver()
  {
       vbs = new ArrayList<VariableBinding>();
  }
  /**
   * This method will listen for traps and response pdu's from SNMP agent.
   */
  public synchronized void listen(TransportIpAddress address) throws IOException
  {
    AbstractTransportMapping transport;
    if (address instanceof TcpAddress)
    {
      transport = new DefaultTcpTransportMapping((TcpAddress) address);
    }
    else
    {
      transport = new DefaultUdpTransportMapping((UdpAddress) address);
    }

    ThreadPool threadPool = ThreadPool.create("DispatcherPool", 10);
    MessageDispatcher mtDispatcher = new MultiThreadedMessageDispatcher(threadPool, new MessageDispatcherImpl());

    // add message processing models
    mtDispatcher.addMessageProcessingModel(new MPv1());
    mtDispatcher.addMessageProcessingModel(new MPv2c());
    mtDispatcher.addMessageProcessingModel(new MPv3());

    // add all security protocols
    SecurityProtocols.getInstance().addDefaultProtocols();
    SecurityProtocols.getInstance().addPrivacyProtocol(new Priv3DES());

    //Create Target
    //CommunityTarget target = new CommunityTarget();
    //target.setCommunity( new OctetString("public"));
    
    Snmp snmp = new Snmp(mtDispatcher, transport);
    snmp.addCommandResponder(this);
    
    transport.listen();
    System.out.println("Listening on " + address);

    try
    {
      this.wait();
    }
    catch (InterruptedException ex)
    {
      Thread.currentThread().interrupt();
    }
  }
  public void setField(javax.swing.JTextPane textPane)
{
    TextPane = textPane;
  //  hilite = TextPane.getHighlighter();
}
public List<VariableBinding> getListVB()
{
    return vbs;
}
  /**
   * This method will be called whenever a pdu is received on the given port specified in the listen() method
   */
  public synchronized void processPdu(CommandResponderEvent cmdRespEvent)
  {
    System.out.println("Received PDU...");
    PDU pdu = cmdRespEvent.getPDU();
    String address = cmdRespEvent.getPeerAddress().toString(); 
    if (pdu != null)
    {
      vbs.clear();
      vbs.addAll(pdu.getVariableBindings());
      StyledDocument doc = (StyledDocument) TextPane.getDocument();
      Style style = doc.addStyle("styl_1", null);
      
      String s = "Trap receive from: "+ address+" Type: "+pdu.getType()+"\n";   

      String space ="-----------------------------------------------------------\n";
      int size = TextPane.getHeight();
   
      
      System.out.println("Trap Type = " + pdu.getType());
      System.out.println("Variable Bindings = " + pdu.getVariableBindings());
      System.out.println("Trap Error Status:" + pdu.getErrorStatus());
      System.out.println("Model:"+cmdRespEvent.getMessageProcessingModel());
      if(color == 0)
      {
         // StyleConstants.setBackground(style, Color.LIGHT_GRAY);
          StyleConstants.setForeground(style, Color.red);
          StyleConstants.setBold(style, true);
         
          color = 1;
          try
          {       
               doc.insertString(doc.getLength(), s+space, style);          
          }
          catch(BadLocationException e)
          {
              System.out.println(e.getStackTrace());
          }
      }else
      {
        //  StyleConstants.setBackground(style, Color.white);
          StyleConstants.setForeground(style, Color.black);
          StyleConstants.setBold(style, true);
          color = 0;
          try
          {
               doc.insertString(doc.getLength(), s+space, style);
              
          }
          catch(BadLocationException e)
          {
              System.out.println(e.getStackTrace());
          }
      }
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
}