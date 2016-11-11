package cz.cvut.fit.shiftify.data;

/**
 * Created by lukas on 11.11.2016.
 */

public class Role {
    public Role() { }
    public Role(String name) {
        this(name, null);
    }
    public Role(String name, String description) {
        Name = name;
        Description = description;
    }

    public Integer Id;
    public String Name;
    public String Description;
}
