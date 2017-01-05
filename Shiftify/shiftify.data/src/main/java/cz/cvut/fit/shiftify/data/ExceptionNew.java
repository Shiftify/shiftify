package cz.cvut.fit.shiftify.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cz.cvut.fit.shiftify.data.models.User;

/**
 * Created by petr on 12/6/16.
 */

public class ExceptionNew {

    private String name;
    private Calendar start;
    private Calendar end;
    private User user;
    private ExceptionNewType type;
    private Integer id;

    public ExceptionNew(ExceptionNewType type, String name, Calendar start, Calendar end, User user) {
        this.type = type;
        this.name = name;
        this.start = start;
        this.end = end;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getStart() {
        return start;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ExceptionNewType getType() {
        return type;
    }

    public void setType(ExceptionNewType type) {
        this.type = type;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getId() {
        return id;
    }

    public static List<ExceptionNew> getExceptions() {
        List<ExceptionNew> list = new ArrayList<>();
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.add(Calendar.DATE, 15);
        cal2.add(Calendar.DATE, 16);
        ExceptionNew e1 = new ExceptionNew(ExceptionNewType.REST, "Vyjimka 1", cal1, cal2, new User());
        e1.setId(1);
        cal1.add(Calendar.DATE, 5);
        cal2.add(Calendar.DATE, 5);
        ExceptionNew e2 = new ExceptionNew(ExceptionNewType.WORK, "Vyjimka 2", cal1, cal2, new User());
        e2.setId(2);
        cal1.add(Calendar.DATE, 10);
        cal2.add(Calendar.DATE, 10);
        ExceptionNew e3 = new ExceptionNew(ExceptionNewType.WORK, "Vyjimka 3", cal1, cal2, new User());
        e3.setId(3);
        list.add(e1);
        list.add(e2);
        list.add(e3);
        return list;
    }

}
