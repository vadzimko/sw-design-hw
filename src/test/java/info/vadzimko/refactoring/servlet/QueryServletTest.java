package info.vadzimko.refactoring.servlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;

import static org.mockito.Mockito.when;

public class QueryServletTest {
    private QueryServlet queryServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ServletTestUtils.clearTable();
        ServletTestUtils.insertTestRows();
        queryServlet = new QueryServlet();
    }

    private void mockServletRequestCommand(String command) {
        when(request.getParameter("command")).thenReturn(command);
    }

    private void assertCommandResult(String command, String expectedResult) throws IOException {
        mockServletRequestCommand(command);
        StringWriter writer = ServletTestUtils.mockServletResponseWriter(response);

        queryServlet.doGet(request, response);
        Assert.assertEquals(expectedResult, writer.toString());
    }

    @Test
    public void maxTest() throws IOException {
        assertCommandResult("max",
                "<html><body>\n" +
                        "<h1>Product with max price: </h1>\n" +
                        "dog\t20</br>\n" +
                        "</body></html>\n");
    }

    @Test
    public void minTest() throws IOException {
        assertCommandResult("min",
                "<html><body>\n" +
                        "<h1>Product with min price: </h1>\n" +
                        "cat\t10</br>\n" +
                        "</body></html>\n");
    }

    @Test
    public void countTest() throws IOException {
        assertCommandResult("count",
                "<html><body>\n" +
                        "Number of products: \n" +
                        "2\n" +
                        "</body></html>\n");
    }

    @Test
    public void sumTest() throws IOException {
        assertCommandResult("sum",
                "<html><body>\n" +
                        "Summary price: \n" +
                        "30\n" +
                        "</body></html>\n");
    }

}
