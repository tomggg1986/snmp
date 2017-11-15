package hibernate;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.util.Date;

public class Device {
    
    private Long id;
    private String name;
    private String ipaddres;
    
    public Device()
    {
        
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIpaddres() {
        return ipaddres;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIpaddres(String ipaddres) {
        this.ipaddres = ipaddres;
    }
    
    
}
