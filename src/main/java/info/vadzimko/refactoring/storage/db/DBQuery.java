package info.vadzimko.refactoring.storage.db;

import java.sql.*;

abstract class DBQuery {

    protected Statement statement;
    protected final String query;

    public DBQuery(String query) {
        this.query = query;
    }

    public void execute() {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            statement = c.createStatement();
            executeQuery();
            statement.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    abstract void executeQuery() throws SQLException;

}
