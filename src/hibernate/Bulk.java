
package hibernate;


public class Bulk {
  
    private int id;
    private int nonrepeaters;
    private int maxrepetitions;
    public Bulk(){}
    public Bulk(int non, int max)
    {
        this.nonrepeaters = non;
        this.maxrepetitions = max;
    }

    public int getId() {
        return id;
    }

    public int getNonrepeaters() {
        return nonrepeaters;
    }

    public int getMaxrepetitions() {
        return maxrepetitions;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNonrepeaters(int nonrepeaters) {
        this.nonrepeaters = nonrepeaters;
    }

    public void setMaxrepetitions(int maxrepetitions) {
        this.maxrepetitions = maxrepetitions;
    }
        
}
