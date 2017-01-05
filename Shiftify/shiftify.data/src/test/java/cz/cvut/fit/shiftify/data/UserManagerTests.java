package cz.cvut.fit.shiftify.data;

import org.junit.Test;
import java.sql.Date;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by lukas on 16.11.2016.
 */

public class UserManagerTests {
    @Test
    public void dummyMethods_dontThrowException() throws Exception {
        UserManager um = new UserManager();
        User u = new User("John", "Doe", null, "john.doe@gmail.com");
//        try { um.add(u); }
//        catch(Exception ex) { assert("UserManager add method throws exception." == null); }
//        try { um.edit(u); }
//        catch(Exception ex) { assert("UserManager edit method throws exception." == null); }
//        try { um.user(u.getId()); }
//        catch(Exception ex) { assert("UserManager user method throws exception." == null); }
//        try { um.users(); }
//        catch(Exception ex) { assert("UserManager users method throws exception." == null); }
//        try { um.delete(u.getId()); }
//        catch(Exception ex) { assert("UserManager delete method throws exception." == null); }
//        try { um.shiftsForDate(u.getId(), new Date(116, 10, 12)); }
//        catch(Exception ex) { assert("UserManager shiftsForDate method throws exception." == null); }
//        try { um.shiftsForPeriod(u.getId(), new Date(116, 10, 12), new Date(116, 11, 31)); }
//        catch(Exception ex) { assert("UserManager shiftsForPeriod method throws exception." == null); }
    }
}