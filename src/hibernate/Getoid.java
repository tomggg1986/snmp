
package hibernate;


public class Getoid {
    
    
    private long id;
    private String oid;
    
    public Getoid(){}
    public Getoid(String oid)
    {
        this.oid = oid;
    }

    public long getId() {
        return id;
    }

    public String getOid() {
        return oid;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }
    
    
    
}
