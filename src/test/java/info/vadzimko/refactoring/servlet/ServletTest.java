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
import java.sql.Statement;

import static org.mockito.Mockito.when;

public class ServletTest {
    private QueryServlet queryServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        clearTable();
        insertTestRows();
        queryServlet = new QueryServlet();
    }

    private void clearTable() {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            String sql = "DELETE FROM PRODUCT WHERE 1";
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void insertTestRows() {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            String sql = "INSERT INTO PRODUCT " +
                    "(NAME, PRICE) VALUES " +
                    "('cat', 10)," +
                    "('dog', 20)";
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private StringWriter mockServletResponseWriter() throws IOException {
        StringWriter writer = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        return writer;
    }


    @Test
    public void maxTest() throws IOException {
        when(request.getParameter("command")).thenReturn("max");
        StringWriter writer = mockServletResponseWriter();

        queryServlet.doGet(request, response);
        Assert.assertEquals(writer.toString(),
                "<html><body>\n" +
                        "<h1>Product with max price: </h1>\n" +
                        "dog\t20</br>\n" +
                        "</body></html>\n");
    }

    @Test
    public void minTest() throws IOException {
        when(request.getParameter("command")).thenReturn("min");
        StringWriter writer = mockServletResponseWriter();

        queryServlet.doGet(request, response);
        Assert.assertEquals(writer.toString(),
                "<html><body>\n" +
                        "<h1>Product with min price: </h1>\n" +
                        "cat\t10</br>\n" +
                        "</body></html>\n");
    }

    @Test
    public void countTest() throws IOException {
        when(request.getParameter("command")).thenReturn("count");
        StringWriter writer = mockServletResponseWriter();

        queryServlet.doGet(request, response);
        Assert.assertEquals(writer.toString(),
                "<html><body>\n" +
                        "Number of products: \n" +
                        "2\n" +
                        "</body></html>\n");
    }

    @Test
    public void sumTest() throws IOException {
        when(request.getParameter("command")).thenReturn("sum");
        StringWriter writer = mockServletResponseWriter();

        queryServlet.doGet(request, response);
        Assert.assertEquals(writer.toString(),
                "<html><body>\n" +
                        "Summary price: \n" +
                        "30\n" +
                        "</body></html>\n");
    }

}
