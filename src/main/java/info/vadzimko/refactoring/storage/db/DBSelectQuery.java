package info.vadzimko.refactoring.storage.db;

import info.vadzimko.refactoring.storage.SelectResultSet;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

public class DBSelectQuery extends DBQuery {

    private SelectResultSet selectResultSet;

    public DBSelectQuery(String query) {
        super(query);
    }

    @Override
    void executeQuery() throws SQLException {
        ResultSet resultSet = statement.executeQuery(query);
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnsCount = metaData.getColumnCount();
        selectResultSet = new SelectResultSet();

        while (resultSet.next()) {
            HashMap<String, String> row = new HashMap<>();
            for (int i = 1; i <= columnsCount; i++) {
                row.put(metaData.getColumnName(i).toLowerCase(), resultSet.getString(i));
            }
            selectResultSet.addRow(row);
        }
    }

    public boolean next() {
        return selectResultSet.next();
    }

    public String getString(String column) {
        return selectResultSet.getString(column);
    }

    public Integer getInt(String column) {
        return selectResultSet.getInt(column);
    }

}
