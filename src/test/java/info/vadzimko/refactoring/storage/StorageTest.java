package info.vadzimko.refactoring.storage;

import info.vadzimko.refactoring.storage.db.DBSelectQuery;
import info.vadzimko.refactoring.storage.db.DBUpdateQuery;
import org.junit.Assert;
import org.junit.Test;

public class StorageTest {

    private void clearTable() {
        DBUpdateQuery query = new DBUpdateQuery("DELETE FROM PRODUCT");
        query.execute();
    }

    private void doInsert(String name, Integer price) {
        String sql = "INSERT INTO PRODUCT (NAME, PRICE) VALUES ('" + name + "', " + price + ")";
        DBUpdateQuery query = new DBUpdateQuery(sql);
        query.execute();
    }

    private DBSelectQuery selectAll() {
        DBSelectQuery selectQuery = new DBSelectQuery("SELECT * FROM PRODUCT");
        selectQuery.execute();
        return selectQuery;
    }

    @Test
    public void testEmpty() {
        clearTable();
        Assert.assertEquals(0, selectAll().getRowsCount());
    }

    @Test
    public void testEmptyCleaned() {
        doInsert("cat", 10);
        Assert.assertNotEquals(0, selectAll().getRowsCount());

        clearTable();
        Assert.assertEquals(0, selectAll().getRowsCount());
    }

    @Test
    public void testInsert() {
        clearTable();
        doInsert("cat", 10);

        DBSelectQuery selectQuery = selectAll();
        Assert.assertEquals(1, selectQuery.getRowsCount());

        selectQuery.next();
        Assert.assertEquals("cat", selectQuery.getString("name"));
        Assert.assertEquals(10, selectQuery.getInt("price"));
    }

    @Test
    public void testInsertMany() {
        clearTable();
        doInsert("cat", 10);
        doInsert("dog", 20);
        doInsert("cucumber", 30);

        DBSelectQuery selectQuery = selectAll();
        Assert.assertEquals(3, selectQuery.getRowsCount());
        selectQuery.next();

        selectQuery.next();
        Assert.assertEquals("dog", selectQuery.getString("name"));
        Assert.assertEquals(20, selectQuery.getInt("price"));

        selectQuery.next();
        Assert.assertEquals("cucumber", selectQuery.getString("name"));
        Assert.assertEquals(30, selectQuery.getInt("price"));

        Assert.assertFalse(selectQuery.next());
    }

}
