package info.vadzimko.refactoring.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ServletTestUtils {

    public static void clearTable() {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            String sql = "DELETE FROM PRODUCT WHERE 1";
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
