
package hibernate;


public class Profils {
    
    private Long id;
    private String name;
    private String version;
    private String securityname;
    private String authprot;
    private String authpass;
    private String privprot;
    private String privpass;
    
    public Profils(){}

    //gettlers
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getSecurityname() {
        return securityname;
    }

    public String getAuthprot() {
        return authprot;
    }

    public String getAuthpass() {
        return authpass;
    }

    public String getPrivprot() {
        return privprot;
    }

    public String getPrivpass() {
        return privpass;
    }
    
    //settlers

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setSecurityname(String securityname) {
        this.securityname = securityname;
    }

    public void setAuthprot(String authprot) {
        this.authprot = authprot;
    }

    public void setAuthpass(String authpass) {
        this.authpass = authpass;
    }

    public void setPrivprot(String privprot) {
        this.privprot = privprot;
    }

    public void setPrivpass(String privpass) {
        this.privpass = privpass;
    }
    
}
