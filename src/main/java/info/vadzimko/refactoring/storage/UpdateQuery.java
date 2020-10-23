package info.vadzimko.refactoring.storage;

import java.sql.SQLException;

public class UpdateQuery extends Query {

    public UpdateQuery(String query) {
        super(query);
    }

    @Override
    void executeQuery() throws SQLException {
        statement.executeUpdate(query);
    }
}
