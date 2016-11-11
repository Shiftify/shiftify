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
        this.name = name;
        this.description = description;
    }

    protected Integer id;
    protected String name;
    protected String description;

    // getters and setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
