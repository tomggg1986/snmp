/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

/**
 *
 * @author Tomek
 */
public class Nextoid {
    
    private long id;
    private String oid;
    
    public Nextoid(){}
    public Nextoid(String oid)
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
