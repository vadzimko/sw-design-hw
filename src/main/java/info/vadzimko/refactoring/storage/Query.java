package info.vadzimko.refactoring.storage;

import java.sql.*;

abstract class Query {

    protected Statement statement;
    protected final String query;

    public Query(String query) {
        this.query = query;
        execute();
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
