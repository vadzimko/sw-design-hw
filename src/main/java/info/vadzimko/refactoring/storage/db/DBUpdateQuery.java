package info.vadzimko.refactoring.storage.db;

import java.sql.SQLException;

public class DBUpdateQuery extends DBQuery {

    public DBUpdateQuery(String query) {
        super(query);
    }

    @Override
    void executeQuery() throws SQLException {
        statement.executeUpdate(query);
    }
}
