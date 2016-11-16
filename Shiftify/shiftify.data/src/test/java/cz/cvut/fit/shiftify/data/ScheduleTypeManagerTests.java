package cz.cvut.fit.shiftify.data;

import org.junit.Test;

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
        try { stm.edit(st.getId(), st); }
        catch(Exception ex) { assert("RoleManager edit method throws exception." == null); }
        try { stm.addShift(st.getId(), ss); }
        catch(Exception ex) { assert("RoleManager addShift method throws exception." == null); }
        try { stm.editShift(ss.getId(), ss); }
        catch(Exception ex) { assert("RoleManager editShift method throws exception." == null); }
        try { stm.deleteShift(ss.getId()); }
        catch(Exception ex) { assert("RoleManager deleteShift method throws exception." == null); }
        try { stm.delete(st.getId()); }
        catch(Exception ex) { assert("RoleManager delete method throws exception." == null); }
    }
}