package cz.cvut.fit.shiftify.data;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import cz.cvut.fit.shiftify.data.managers.ScheduleTypeManager;
import cz.cvut.fit.shiftify.data.managers.UserManager;
import cz.cvut.fit.shiftify.data.models.Schedule;
import cz.cvut.fit.shiftify.data.models.ScheduleShift;
import cz.cvut.fit.shiftify.data.models.ScheduleType;
import cz.cvut.fit.shiftify.data.models.User;

/**
 * Created by petr on 1/5/17.
 */

public class DataSeeder {

    private static final int USERS_COUNT = 8;
    private static final int SCHEDULE_TYPES_COUNT = 2;


    public static void initAll() {
        initUsers();
        initScheduleTypesAndScheduleShifts();
        initSchedules();

    }

    private static void initUsers() {
        UserManager userManager = new UserManager();
        List<User> users = userManager.allUsers();

        if (users.size() < USERS_COUNT) {
            try {
                userManager.deleteAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
            users.clear();

            users.add(new User("Michal", "Plameňák", "777222111", "random@something.org"));
            users.add(new User("Petr", "Kůň"));
            users.add(new User("Martin", "Salamini", null, "some@address.com", "meloun"));
            users.add(new User("Martin", "Salamini", "+420423458932"));
            users.add(new User("Adam", "Moron", "+420423458932"));
            users.add(new User("Jaromir", "Jagr", "+420423458932"));
            users.add(new User("Nekdo", "Nekdovic", "+420423458932"));
            users.add(new User("Nikdo", "Kdokolic", "+420427458932"));
            for (User u : users) {
                try {
                    userManager.add(u);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private static void initScheduleTypesAndScheduleShifts() {
        ScheduleTypeManager scheduleTypeManager = new ScheduleTypeManager();
        try {
            scheduleTypeManager.deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ScheduleType scheduleType = new ScheduleType("Železárny", 7, "Slouží jako rozpis pro vrátnýho.");
        List<ScheduleShift> shifts = new ArrayList<>();
        shifts.add(new ScheduleShift("ranni", Utilities.GregCalFrom(6, 0), Utilities.GregCalFrom(8, 0), 1l, 2, "To se bude blbě vstávat."));
        shifts.add(new ScheduleShift("nocni", Utilities.GregCalFrom(22, 0), Utilities.GregCalFrom(8, 0), 1l, 3));
        shifts.add(new ScheduleShift("ranni", Utilities.GregCalFrom(6, 0), Utilities.GregCalFrom(8, 0), 1l, 5, "To se bude blbě vstávat."));
        shifts.add(new ScheduleShift("odpoledni", Utilities.GregCalFrom(14, 0), Utilities.GregCalFrom(8, 0), 1l, 6));
        scheduleType.setShifts(shifts);

        try {
            scheduleTypeManager.add(scheduleType);
            for (ScheduleShift shift : scheduleType.getShifts()) {
                scheduleTypeManager.addShift(scheduleType.getId(), shift);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        shifts.clear();
        shifts.add(new ScheduleShift("ranni", Utilities.GregCalFrom(6, 0), Utilities.GregCalFrom(8, 0), 2l, 1, "To se bude blbě vstávat."));
        shifts.add(new ScheduleShift("ranni", Utilities.GregCalFrom(6, 0), Utilities.GregCalFrom(8, 0), 2l, 2));
        shifts.add(new ScheduleShift("odpoledni", Utilities.GregCalFrom(14, 0), Utilities.GregCalFrom(8, 0), 2l, 3));
        shifts.add(new ScheduleShift("odpoledni", Utilities.GregCalFrom(14, 0), Utilities.GregCalFrom(8, 0), 2l, 4, "Já jsem poznámka."));
        shifts.add(new ScheduleShift("nocni", Utilities.GregCalFrom(22, 0), Utilities.GregCalFrom(8, 0), 2l, 5));
        shifts.add(new ScheduleShift("nocni", Utilities.GregCalFrom(22, 0), Utilities.GregCalFrom(8, 0), 2l, 6, "komentář"));
        scheduleType = new ScheduleType("Železárny_hasič", 8);
        scheduleType.setId(2L);
        scheduleType.setShifts(shifts);
        try {
            scheduleTypeManager.add(scheduleType);
            for (ScheduleShift shift : scheduleType.getShifts()) {
                scheduleTypeManager.addShift(scheduleType.getId(), shift);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initSchedules() {
        UserManager userManager = new UserManager();
        ScheduleTypeManager scheduleTypeManager = new ScheduleTypeManager();
        userManager.deleteScheduleAll();

        List<ScheduleType> scheduleTypes = scheduleTypeManager.scheduleTypesAll();

        for (User user : userManager.allUsers()) {
            List<Schedule> schedules = new ArrayList<>();
            schedules.add(new Schedule(user.getId(), scheduleTypes.get(0).getId(),
                    new GregorianCalendar(116, 10, 2), new GregorianCalendar(116, 10, 10), 1));
            schedules.add(new Schedule(user.getId(), scheduleTypes.get(1).getId(),
                    new GregorianCalendar(116, 10, 2), new GregorianCalendar(116, 10, 10), 2));
            for (Schedule schedule : schedules) {
                try {
                    userManager.addSchedule(schedule);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
