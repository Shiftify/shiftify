package cz.cvut.fit.shiftify.data;

import org.junit.Test;

import cz.cvut.fit.shiftify.data.managers.ScheduleTypeManager;
import cz.cvut.fit.shiftify.data.models.ScheduleShift;
import cz.cvut.fit.shiftify.data.models.ScheduleType;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ScheduleTypeManagerTests {
    @Test
    public void dummyMethods_dontThrowException() throws Exception {
        ScheduleTypeManager stm = new ScheduleTypeManager();
        ScheduleType st = new ScheduleType();
        ScheduleShift ss = new ScheduleShift();
        try { stm.add(st); }
        catch(Exception ex) { assert("RoleManager add method throws exception." == null); }
        try { stm.edit(st); }
        catch(Exception ex) { assert("RoleManager edit method throws exception." == null); }
        try { stm.delete(st.getId()); }
        catch(Exception ex) { assert("RoleManager delete method throws exception." == null); }
    }
}