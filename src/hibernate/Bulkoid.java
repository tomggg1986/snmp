
package hibernate;


public class Bulkoid {
    
    private long id;
    private String oid;
    
    public Bulkoid(){}
    public Bulkoid(String oid)
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
