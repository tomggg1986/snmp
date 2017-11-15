package snmp_server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.snmp4j.security.AuthGeneric;
import org.snmp4j.security.PrivacyProtocol;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;


public final class  ProfilFactory {
    
    
    public static final OctetString addName(OctetString name)
    {
        if(name.length() >0 && name.length() <20)
        {
            return name;
        }
        else
        {
            return null;
        }           
    }
    public static final int addVersion(String version)
    {
        if(version.equals("snmpv1"))
        {
            return 0;
        }else if(version.equals("snmpv2c"))
        {
            return 1;        
        }else if(version.equals("snmpv3"))
        {
            return 3;
        }else
        {
            return -1;
        }   
    }
    public static final OctetString addSecurityName(OctetString secuName)
    {
        if(secuName.length() > 0 && secuName.length() < 20)
        {
            return secuName;
        }else
        {
            return null;
        }
    }
    public static final AuthGeneric addAuthProtocol(OctetString authProt, HashMap map)
    {
        Iterator i = map.entrySet().iterator();
        AuthGeneric o = null;
        while(i.hasNext())
        {
            Map.Entry entry = (Map.Entry)i.next();
            String s = entry.getKey().toString();
            if(s.equals(authProt.toString()))
            {
                  o = (AuthGeneric) entry.getValue();     
            }
        }     
        return o;
    }
    public static final OctetString addAuthPassword(OctetString password)
    {
       if(password.length() >0 && password.length() <20)
        {
            return password;
        }
        else
        {
            return null;
        }   
    }
    public static final  PrivacyProtocol addPrivProtocol( OctetString privProt, HashMap map)
    {
        Iterator i = map.entrySet().iterator();
        PrivacyProtocol o = null;
        while(i.hasNext())
        {
            Map.Entry entry = (Map.Entry)i.next();
            String s = entry.getKey().toString();
            if(s.equals(privProt.toString()))
            {
                  o = (PrivacyProtocol)entry.getValue();     
            }
            else
            {
                o = null;
            }
        }
        return o;
    }
    public static final OctetString addPrivPassword(OctetString password)
    {
       if(password.length() >0 && password.length() <20)
        {
            return password;
        }
        else
        {
            return null;
        }   
    }
}
