package cz.cvut.fit.shiftify.helpers;

import cz.cvut.fit.shiftify.data.WorkDay;
import cz.cvut.fit.shiftify.data.models.User;

/**
 * Created by Vojta on 08.02.2017.
 */

public class UserWorkdayWrapper {

    private WorkDay workday;
    private User user;

    public UserWorkdayWrapper(User u, WorkDay w) {

        this.user = u;
        this.workday = w;
    }

    public WorkDay getWorkday() {
        return workday;
    }

    public User getUser() {
        return user;
    }
    public void setWorkday(WorkDay w){
        this.workday = w;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
