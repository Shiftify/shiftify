package cz.cvut.fit.shiftify.data;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.List;

import cz.cvut.fit.shiftify.data.backup.StorageBackuper;
import cz.cvut.fit.shiftify.data.managers.UserManager;
import cz.cvut.fit.shiftify.data.models.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class BackupTest {
    private static final String FIRST_NAME = "Backup";
    private static final String SECOND_NAME = "Test";
    private static final String PHONE_NUMBER = "+420123456789";
    private static final String EMAIL = "aaa@bbb.com";


    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private UserManager userManager;

    @Before
    public void setupDb() throws Exception {
        userManager = new UserManager();
        userManager.deleteAll();

        User user = new User();
        user.setFirstName(FIRST_NAME);
        user.setSurname(SECOND_NAME);
        user.setPhoneNumber(PHONE_NUMBER);
        user.setEmail(EMAIL);

        userManager.add(user);
    }

    @Test
    public void backupTest() throws Exception {
        File backup = folder.newFile("backup.db");

        StorageBackuper.backup(backup);

        assertTrue("Backup should exist.", backup.exists());

        userManager.deleteAll();

        assertTrue("User list should be empty after deleting", userManager.allUsers().isEmpty());

        StorageBackuper.restore(backup);

        List<User> users = userManager.allUsers();

        assertTrue("User list should contain 1 user after backup.", users.size() == 1);

        User user = users.get(0);

        assertEquals(FIRST_NAME, user.getFirstName());
        assertEquals(SECOND_NAME, user.getSurname());
        assertEquals(PHONE_NUMBER, user.getPhoneNumber());
        assertEquals(EMAIL, user.getEmail());
    }
}
