package cz.cvut.fit.shiftify.data;

import org.junit.Test;

import cz.cvut.fit.shiftify.data.models.User;

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
        // User
        String query = DbTable.createTableQuery(User.class);
        assertEquals(query, "CREATE TABLE 'User'(" +
                "'Id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,'InsertedOn' DATETIME NOT NULL," +
                "'FirstName' NVARCHAR(100) NOT NULL,'Surname' NVARCHAR(100) NOT NULL," +
                "'Nickname' NVARCHAR(100) NOT NULL,'PhoneNumber' NVARCHAR(50) UNIQUE," +
                "'Email' NVARCHAR(255) UNIQUE,'PicturePath' NVARCHAR(1000));\n" +
                "CREATE UNIQUE INDEX 'unique_User_Names' ON 'User'('FirstName','Surname','Nickname');\n");

        // ScheduleType
    }
    @Test
    public void InsertUpdateDeleteQueryCheck() throws Exception {
        // User
//        User user = new User("Lukas", "Komarek", "123 456 789", "komareklukas@something.com");
//        String query = user.insertQuery();
//        assertEquals(query, "INSERT INTO" +
//                " 'User'('InsertedOn','FirstName','Surname','Nickname','PhoneNumber','Email','PicturePath')" +
//                " VALUES('" + user.getInsertedOn().getTimeInMillis() +
//                "','Lukas','Komarek','','123 456 789','komareklukas@something.com','null');");
//        user.setId(10);
//        user.setPicturePath("/img/luk_kom.png");
//        query = user.updateQuery();
//        assertEquals(query, "UPDATE 'User' SET" +
//                " InsertedOn='" + user.getInsertedOn().getTimeInMillis() + "'," +
//                "FirstName='Lukas',Surname='Komarek',Nickname='',PhoneNumber='123 456 789'," +
//                "Email='komareklukas@something.com',PicturePath='/img/luk_kom.png' WHERE Id='10';");
//        query = user.deleteQuery();
//        assertEquals(query, "DELETE FROM 'User' WHERE Id='10';");

        // ScheduleType
    }
    @Test
    public void SelectQueryCheck() throws Exception {
        // User
//        String query = DbTable.selectQuery(User.class, new QueryCondition(User.COL_NAME_NICKNAME, ComparisonType.NOTEQUAL, ""));
//        assertEquals(query, "SELECT * FROM 'User' WHERE Nickname!='';");
//        query = DbTable.selectQuery(User.class, new QueryCondition(User.COL_NAME_NICKNAME, ComparisonType.NOTEQUAL, "")
//                .or(new QueryCondition(User.COL_NAME_EMAIL, ComparisonType.EQUAL, "michal.michna@at.com"))
//                .and(new QueryCondition(User.COL_NAME_FIRSTNAME, ComparisonType.EQUAL, "Michal")
//                        .or(new QueryCondition(User.COL_NAME_SURNAME, ComparisonType.EQUAL, "Michna"))));
//        assertEquals(query, "SELECT * FROM 'User' WHERE ((Nickname!='') OR (Email='michal.michna@at.com'))" +
//                " AND ((FirstName='Michal') OR (Surname='Michna'));");

        // ScheduleType
    }
}