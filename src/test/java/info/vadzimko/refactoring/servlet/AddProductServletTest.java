package info.vadzimko.refactoring.servlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class AddProductServletTest {
    private AddProductServlet addProductServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ServletTestUtils.clearTable();
        addProductServlet = new AddProductServlet();
    }

    private ArrayList<String> selectAllRows() {
        ArrayList<String> result = new ArrayList<>();

        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");
            response.getWriter().println("<html><body>");

            while (rs.next()) {
                result.add(rs.getString("name") + ";" + rs.getString("price"));
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Test
    public void addTest() throws IOException {
        when(request.getParameter("name")).thenReturn("cat", "dog");
        when(request.getParameter("price")).thenReturn("10", "20");
        ServletTestUtils.mockServletResponseWriter(response);

        addProductServlet.doGet(request, response);
        addProductServlet.doGet(request, response);
        ArrayList<String> expected = new ArrayList<>();

        expected.add("cat;10");
        expected.add("dog;20");
        Assert.assertEquals(expected, selectAllRows());
    }

}
