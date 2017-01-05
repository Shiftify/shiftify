package cz.cvut.fit.shiftify.data.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lukas on 11.11.2016.
 */

@Entity(generateConstructors = false, generateGettersSetters = false)
public class Role {

    @Id(autoincrement = true)
    protected Long id;

    @NotNull
    protected String name;
    protected String description;

    public Role() {
    }

    public Role(@NotNull String name) {
        this(name, null);
    }

    public Role(@NotNull String name, String description) {
        setName(name);
        setDescription(description);
    }

    public Role(Long id, @NotNull String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
