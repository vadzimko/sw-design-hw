package info.vadzimko.refactoring.storage;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

public class SelectQuery extends Query {

    private SelectResultSet selectResultSet;

    public SelectQuery(String query) {
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
