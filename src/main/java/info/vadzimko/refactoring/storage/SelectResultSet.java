package info.vadzimko.refactoring.storage;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectResultSet {

    private ArrayList<HashMap<String, String>> values;
    private int rowIdx;

    public SelectResultSet() {
        this.rowIdx = -1;
        this.values = new ArrayList<>();
    }

    public void addRow(HashMap<String, String> row) {
        values.add(row);
    }

    public boolean next() {
        rowIdx++;
        return rowIdx < values.size();
    }

    public String getString(String columnName) {
        return values.get(rowIdx).get(columnName.toLowerCase());
    }

    public Integer getInt(String columnName) {
        String str = getString(columnName);
        if (str == null) {
            return null;
        }

        return Integer.valueOf(str);
    }
}
