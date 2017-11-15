package snmp_server;

import org.snmp4j.AbstractTarget;
import org.snmp4j.CommunityTarget;
import org.snmp4j.UserTarget;
import org.snmp4j.security.AuthGeneric;
import org.snmp4j.security.PrivacyProtocol;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;


public class Profil {
    
    private OctetString name;
    
    private OctetString version;
    private int versionInt;
    
    private OctetString securityName;
    private OctetString contextName;
    private SecurityLevel securityLevel;
    
    private AuthGeneric AuthProtocol;
    private OctetString AuthPassword;
    
    private  PrivacyProtocol PrivProtocol;
    private OctetString PrivPassword;
    
    //private USM usm;
    public Profil()
    {
        securityName = new OctetString("public");
        contextName = new OctetString("noAuth");
        securityLevel= SecurityLevel.noAuthNoPriv;
        AuthProtocol = null;
        AuthPassword = null;
        PrivProtocol = null;
        PrivPassword = null;
        name = new OctetString("default");
    }
    public Profil(OctetString name, int version, OctetString SecuName, AuthGeneric AuthProt ,OctetString AuthPass,  PrivacyProtocol PrivProt, OctetString PrivPass)
    {
        this.name = name;
        this.versionInt = version;
        this.securityName = SecuName;
        if(versionInt == 3)
        {     
        if(AuthProt != null)
        {
            this.AuthProtocol = AuthProt;
            this.contextName = new OctetString("Auth");    
            this.securityLevel = SecurityLevel.authNoPriv;
        }
        else
        {
            this.AuthProtocol = null; 
        }
        this.AuthPassword = AuthPass;
        if((PrivProt != null))
        {
            this.PrivProtocol = PrivProt;
            this.contextName = new OctetString("Priv");
            this.securityLevel = SecurityLevel.authPriv;
        }
        else
        {
            this.PrivProtocol = null;
        }
        this.PrivPassword = PrivPass;
        if(AuthProt == null && PrivProt == null)
        {
            this.contextName = new OctetString("noAuth");
            this.securityLevel = SecurityLevel.noAuthNoPriv;
        }
        }else if(versionInt == 0 || versionInt == 1)
        {
            this.securityLevel = SecurityLevel.noAuthNoPriv;
            this.contextName = new OctetString("version1or2");
            this.AuthProtocol = null;
            this.PrivProtocol = null;
            this.PrivPassword = null;
            this.AuthPassword = null;
            
        }
        
      //  USM usm = new USM(SecurityProtocols.getInstance(), new
      //  OctetString(MPv3.createLocalEngineID()), 0);  
     //   SecurityModels.getInstance().addSecurityModel(usm);
    }
    public AbstractTarget getTarget()
    {
        AbstractTarget ab;
        if(versionInt == 0 || versionInt ==1)
        {
            ab = new CommunityTarget();
        }
        else if(versionInt == 3)
        {
            ab = new UserTarget();
        }
        else
        {
            ab = null;
        }
        return ab;
    }
    //----------------------SETTLERS---------------------------------------

    public void setName(OctetString name) {
        this.name = name;
    }
    
    public void setVersion(OctetString version) {
        this.version = version;
    }

    public void setVersionInt(int versionInt) {
        this.versionInt = versionInt;
    }
   
    public void setSecurityName(OctetString securityName) {
        this.securityName = securityName;
    }

    public void setContextName(OctetString contextName) {
        this.contextName = contextName;
    }

    public void setSecurityLevel(SecurityLevel securityLevel) {
        this.securityLevel = securityLevel;
    }

    public void setAuthProtocol(AuthGeneric AuthProtocol) {
        this.AuthProtocol = AuthProtocol;
    }

    public void setAuthPassword(OctetString AuthPassword) {
        this.AuthPassword = AuthPassword;
    }

    public void setPrivProtocol(PrivacyProtocol PrivProtocol) {
        this.PrivProtocol = PrivProtocol;
    }

    public void setPrivPassword(OctetString PrivPassword) {
        this.PrivPassword = PrivPassword;
    }
   //---------------------GETTLERS-----------------------------------------

    public OctetString getName() {
        return name;
    }
    
    public OctetString getVersion() {
        return version;
    }

    public int getVersionInt() {
        return versionInt;
    }

    
    public OctetString getSecurityName() {
        return securityName;
    }

    public OctetString getContextName() {
        return contextName;
    }

    public SecurityLevel getSecurityLevel() {
        return securityLevel;
    }

    public AuthGeneric getAuthProtocol() {
        return AuthProtocol;
    }
    public OID getAuthProtOID()
    {
        return AuthProtocol.getID();
    }
     public OID getPrivProtOID()
    {
        return PrivProtocol.getID();
    } 
    public OctetString getAuthPassword() {
        return AuthPassword;
    }

    public  PrivacyProtocol getPrivProtocol() {
        return PrivProtocol;
    }

    public OctetString getPrivPassword() {
        return PrivPassword;
    }

    @Override
    public String toString() {
        return "Profil{" + "name=" + name + ", version=" + version + ", versionInt=" + versionInt + ", securityName=" + securityName + ", contextName=" + contextName + ", securityLevel=" + securityLevel + ", AuthProtocol=" + AuthProtocol + ", AuthPassword=" + AuthPassword + ", PrivProtocol=" + PrivProtocol + ", PrivPassword=" + PrivPassword + '}';
    }

    
    
    
    
}
