
package test;

//download snmp4j-2.2.1.jar (or newer) from snmp4j.org (look in maven repository)
//compile with javac -classpath snmp4j-2.2.1.jar:. snmpTrap.java
//java -classpath snmp4j-2.2.1.jar:. snmpTrap
import org.snmp4j.*;
import org.snmp4j.security.*;
import org.snmp4j.util.*;
import org.snmp4j.smi.*;
import org.snmp4j.log.*;
import org.snmp4j.mp.*;
import org.snmp4j.transport.*;
import java.util.*;
import java.io.*;
//our main class
public class snmpTrap implements CommandResponder, TransportStateListener {
 //configuration
 private static boolean _logging = false;
 private String _securityname = "security name";
 private String _authphrase = "authentication phrase";
 private String _privacyphrase = "privacy phrase";
 private String _listenaddress = "tcp:0.0.0.0/162";//listen on all interfaces on port 1620, traditionally snmp informs sent to port 162
 //end configuration
 
 private DefaultTcpTransportMapping transport; //for tcp
 //private DefaultUdpTransportMapping transport; //for udp
 private static LogAdapter logger = null;
 public snmpTrap() {
 try{
 if(_logging){
 logger.setLogLevel(LogLevel.ALL);
 System.out.println("INFO PRINT Mode is on...");
 }
 } catch (Exception e){
 //continue without error, debug will be off
 }
 }
public void initSNMP() throws Exception {
 
//The engine id below remains the same across restarts and creates issues when Trap sending applications
//run for a long period of time and expect the engine id to remain running with a given uptime.
//When the app is restarted, the uptime resets, and the client receives an error that is encapsulated 
//in an OID: usmStatsNotInTimeWindows; OID, 1.3.6.1.6.3.15.1.1.2
//The below code makes sure that the engine id is never the same (based on epoch time), and this solves that problem.
 
//OctetString localEngineID = new OctetString(MPv3.createLocalEngineID());
String epoch = String.valueOf(System.currentTimeMillis()/1000);
OctetString localEngineID = new OctetString("ABC"+epoch);
ThreadPool threadPool = ThreadPool.create("Trap", 5);//start 5 threads to handle 5 connections simultaneously
 MultiThreadedMessageDispatcher dispatcher = new MultiThreadedMessageDispatcher(threadPool, new MessageDispatcherImpl());
//tcp
 Address listenAddress = GenericAddress.parse(System.getProperty("snmp4j.listenAddress", _listenaddress));
 transport = new DefaultTcpTransportMapping((TcpAddress)listenAddress);
 transport.addTransportStateListener(this);
 
 //udp
 //Address listenAddressu = GenericAddress.parse(System.getProperty("snmp4j.listenAddress", "udp:0.0.0.0/1062"));
 //DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping((UdpAddress)listenAddressu);
SecurityProtocols.getInstance().addDefaultProtocols();
 SecurityProtocols.getInstance().addAuthenticationProtocol(new AuthSHA());//Use SHA encryption for authentication
 SecurityProtocols.getInstance().addPrivacyProtocol(new PrivAES128()); //Use AES 128 Bit encryption for privacy / encryption.  Net-SNMP uses 128 Bit AES encryption by default, 192/256 bit encryption won't work.
USM usm = new USM(SecurityProtocols.getInstance(), localEngineID, 0);
 UsmUser usera = new UsmUser(
 new OctetString(_securityname), //security name
 AuthSHA.ID, //authprotocol
 new OctetString(_authphrase), //authpassphrase
 PrivAES128.ID, //privacyprotocol
 new OctetString(_privacyphrase) //privacypassphrase
 );
Snmp snmp = new Snmp(dispatcher, transport);
 snmp.getMessageDispatcher().addMessageProcessingModel(new MPv3(localEngineID.getValue()));
 SecurityModels.getInstance().addSecurityModel(usm);
 snmp.getUSM().addUser(usera);
 snmp.addCommandResponder(this);
 
 if(logger.isInfoEnabled()){
 logger.info("Snmp Receiver is now listening for connections");
 }
 snmp.listen();
}
/**public static void main(String[] args) {
 //setup logging factory
 LogFactory.setLogFactory(new ConsoleLogFactory());
 logger = LogFactory.getLogger(snmpTrap.class);
 snmpTrap ts = new snmpTrap();
 try{
 ts.initSNMP();
 } catch (Exception e){
 e.printStackTrace();
 }
 **/
 
 //inform packets require a response, this function creates and sends the response (From the SNMP4J Library examples)
 protected void sendInformResponse(CommandResponderEvent event) throws MessageException {
 PDU responsePDU = (PDU) event.getPDU().clone();
 responsePDU.setType(PDU.RESPONSE);
 responsePDU.setErrorStatus(PDU.noError);
 responsePDU.setErrorIndex(0);
 event.getMessageDispatcher().returnResponsePdu(event.getMessageProcessingModel(),
 event.getSecurityModel(),
 event.getSecurityName(),
 event.getSecurityLevel(),
 responsePDU,
 event.getMaxSizeResponsePDU(),
 event.getStateReference(),
 new StatusInformation());
 }
//triggered everytime an SNMP message is successfully received because this class implements CommandResponder
 public void processPdu(CommandResponderEvent e) {
 if(logger.isInfoEnabled()){
 logger.info("Entering the processing step of the PDU");
 }
//get the pdu (SNMP message OIDs essentially)
 PDU thetrap = e.getPDU();
 if(logger.isInfoEnabled()){
 logger.info("Successfully got the PDU");
 }
 
 if(logger.isInfoEnabled()){
 logger.info("Processing the varbindings");
 }
String json_trap = "";
 
 //iterate through the pdu's contents
 Vector<? extends VariableBinding> v = thetrap.getVariableBindings();
 Iterator<? extends VariableBinding> it = v.iterator();
 for(; it.hasNext();){
 
 VariableBinding i = it.next();
 
 //create the array key using the oid key
 json_trap += "\""+i.getOid().toString()+"\":\""+i.getVariable().toString()+"\",\n";
 }
//trim final comma and newline on json_trap
 json_trap = json_trap.substring(0, json_trap.length()-2);
//print the trap's contents
 System.out.println("TRAP CONTENTS:\n "+json_trap);
try{
 sendInformResponse(e);
 }catch(Exception ee){
 ee.printStackTrace();
 }
 
 e.setProcessed(true);
 if(logger.isInfoEnabled()){
 logger.info("Processed the PDU");
 }
 }
//only one thread should use the transport object at a time
 private synchronized void closeSocket(TransportStateEvent change) throws IOException{
 transport.close((TcpAddress)change.getPeerAddress());
 }
 
 //triggered everytime a TransportStateEvent is changed, used to close the socket gracefully on our end
 //exists because this class implements TransportStateListener
 public void connectionStateChanged(TransportStateEvent change) {
 if(logger.isInfoEnabled()){
 logger.info("NOTIFICATION RECEIVED, A connection from "+change.getPeerAddress()+" changed state... "+change.getNewState());
 }
 boolean close = false;
 if(change.getNewState() == TransportStateEvent.STATE_DISCONNECTED_REMOTELY){
 close = true;
 }else if(change.getNewState() == TransportStateEvent.STATE_DISCONNECTED_TIMEOUT){
 close = true;
 }else if(change.getNewState() == TransportStateEvent.STATE_CLOSED){
 close = true;
 }
 
 if(close == true && !change.isCancelled()){
 try {
 if(logger.isInfoEnabled()){
 logger.info("Closing the connection");
 }
 //System.out.println("Closing the connection");
 change.setCancelled(true);
 closeSocket(change);
 //Note, the library holds onto the closed connection object for another 60 seconds by default
 //until the timeout occurs, then this object is freed. This shows up in a profiler as java.lang.Object
 } catch (IOException e) {
 e.printStackTrace();
 }
 }
 
 }
}
/*
//upon successful SNMP inform submission, this will output
Entering the processing step of the PDU
Successfully got the PDU
Processing the varbindings
TRAP CONTENTS:
 "1.3.6.1.2.1.1.3.0":"20691031",
"1.3.6.1.6.3.1.1.4.1.0":"1.3.6.1.4.1.1236.2.1.5.6.2",
"1.3.6.1.2.1.1.5.0":"1",
"1.3.6.1.4.1.1236.1.1.1.1.1.0":"999-999-1000",
"1.3.6.1.4.1.1236.1.1.1.4.1.0":"1",
"1.3.6.1.2.1.1.2.0":"1.3.6.1.4.1.1236.1.1"
Processed the PDU
NOTIFICATION RECEIVED, A connection from 127.0.0.1/44591 changed state... 2
Closing the connection
Socket to 127.0.0.1/44591 closed
Socket to 127.0.0.1/44591 closed due to timeout
*/