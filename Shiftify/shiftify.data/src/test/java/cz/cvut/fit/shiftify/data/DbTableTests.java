package cz.cvut.fit.shiftify.data;

import org.junit.Test;

import java.sql.Time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Created by lukas on 16.11.2016.
 */

public class DbTableTests {
    @Test
    public void CreateTableQueryCheck() throws Exception {
        String query = new User().createTableQuery();
        assertEquals(query, "CREATE TABLE 'User'('Id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "'FirstName' NVARCHAR(100) NOT NULL,'Surname' NVARCHAR(100) NOT NULL," +
                "'Nickname' NVARCHAR(100) NOT NULL,'PhoneNumber' NVARCHAR(50) UNIQUE," +
                "'Email' NVARCHAR(255) UNIQUE,'PicturePath' NVARCHAR(1000));\n" +
                "CREATE UNIQUE INDEX 'unique_User_Names' ON 'User'('FirstName','Surname','Nickname');\n");
    }
}