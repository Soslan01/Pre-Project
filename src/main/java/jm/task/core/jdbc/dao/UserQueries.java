package jm.task.core.jdbc.dao;

public interface UserQueries {
    String createTableSql = "CREATE TABLE IF NOT EXISTS USERS("
            + "ID INT(5) NOT NULL PRIMARY KEY AUTO_INCREMENT UNIQUE, "
            + "USERNAME VARCHAR(26) NOT NULL, "
            + "LASTNAME VARCHAR(26) NOT NULL, "
            + "AGE INT(3) NOT NULL)";

    String dropUsersTableSql = "DROP TABLE IF EXISTS myDBTest.USERS";

    String insertTableSql = "INSERT INTO myDBTest.USERS(USERNAME, LASTNAME, AGE) VALUES(?,?,?)";

    String removeUsersSql = "DELETE FROM myDBTest.USERS WHERE id = ?";

    String getUsersSql = "SELECT ID, USERNAME, LASTNAME, AGE FROM myDBTest.USERS";

    String truncateUsersSql = "TRUNCATE TABLE USERS";
}
