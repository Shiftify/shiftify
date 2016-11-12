package cz.cvut.fit.shiftify.data;

import java.util.Vector;
import java.sql.Date;

/**
 * Created by lukas on 11.11.2016.
 */

// dummy implementation at this point
public class UserManager {
    public void add(User user) throws Exception {
        user.setId(5);
    }
    public void edit(int userId, User user) throws Exception {
        user.setId(userId);
    }
    public void delete(int userId) throws Exception {
    }
    public User user(int userId) throws Exception {
        User user = new User("Michal", "Plameňák", "777222111", "random@something.org");
        user.setId(1);
        return user;
    }
    public Vector<User> users() throws Exception {
        Vector<User> users = new Vector<User>();
        users.add(new User("Michal", "Plameňák", "777222111", "random@something.org"));
        users.add(new User("Petr", "Kůň"));
        users.add(new User("Martin", "Salamini", null, "some@address.com", "meloun"));
        users.add(new User("Martin", "Salamini", "+420423458932"));
        for (int i = 1; i <= users.size(); ++i) users.get(i).setId(i);
        return users;
    }

    public void addRole(int userId, int roleId) throws Exception {
    }
    public void deleteRole(int userId, int roleId) throws Exception {
    }
    public Vector<UserRole> roles(int userId) throws Exception {
        Vector<UserRole> userRoles = new Vector<UserRole>();
        userRoles.add(new UserRole(1, 1));
        return userRoles;
    }

    public void addSchedule(int userId, int scheduleTypeId, Schedule schedule) throws Exception {
        schedule.setId(5);
    }
    public void editSchedule(int scheduleId, Schedule schedule) throws Exception {
        schedule.setId(scheduleId);
    }
    public void deleteSchedule(int scheduleId) throws Exception {
    }
    public Schedule schedule(int scheduleId) throws Exception {
        Schedule schedule = new Schedule(1, 2, new Date(2016, 10, 2), null, 3);
        schedule.setId(scheduleId);
        return schedule;
    }
    // returns null if there is no schedule for this day
    public Schedule currentSchedule(int userId) throws Exception {
        Schedule schedule = new Schedule(userId, 2, new Date(2016, 10, 2), null, 3);
        schedule.setId(2);
        return schedule;
    }
    // returns the last schedule, even if not current, or null if the user never had a schedule
    public Schedule lastSchedule(int userId) throws Exception {
        Schedule schedule = new Schedule(userId, 1, new Date(2016, 11, 2), new Date(2016, 11, 10), 1);
        schedule.setId(4);
        return schedule;
    }
    public Vector<Schedule> SchedulesForPeriod(int userId, Date from, Date to) throws Exception {
        if (from == null || to == null) throw new Exception("From nor to date must be null.");
        if (from.after(to)) throw new Exception("From date cannot be after to date.");
        Vector<Schedule> schedules = new Vector<Schedule>();
        schedules.add(new Schedule(userId, 1, from, to, 1));
        schedules.add(new Schedule(userId, 1, to, null, 1));
        for (int i = 1; i <= schedules.size(); ++i) schedules.get(i).setId(i);
        return schedules;
    }
    public Vector<Schedule> schedules(int userId) throws Exception {
        Vector<Schedule> schedules = new Vector<Schedule>();
        schedules.add(new Schedule(userId, 1, new Date(2016, 11, 2), new Date(2016, 11, 10), 1));
        schedules.add(new Schedule(userId, 1, new Date(2016, 11, 11), null, 4));
        for (int i = 1; i <= schedules.size(); ++i) schedules.get(i).setId(i);
        return schedules;
    }
}
