package jm.task.core.jdbc.dao;

public class UserQueries {

    public static String createTableSql = "CREATE TABLE IF NOT EXISTS USERS("
            + "ID INT(5) NOT NULL PRIMARY KEY AUTO_INCREMENT UNIQUE, "
            + "USERNAME VARCHAR(26) NOT NULL, "
            + "LASTNAME VARCHAR(26) NOT NULL, "
            + "AGE INT(3) NOT NULL)";

    public static String dropUsersTableSql = "DROP TABLE IF EXISTS myDBTest.USERS";

    public static String insertTableSql = "INSERT INTO myDBTest.USERS(USERNAME, LASTNAME, AGE) VALUES(?,?,?)";

    public static String removeUsersSql = "DELETE FROM myDBTest.USERS WHERE id = ?";

    public static String getUsersSql = "SELECT ID, USERNAME, LASTNAME, AGE FROM myDBTest.USERS";

    public static String truncateUsersSql = "TRUNCATE TABLE USERS";
}
