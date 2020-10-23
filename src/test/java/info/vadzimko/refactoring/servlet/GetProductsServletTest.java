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

public class GetProductsServletTest {
    private GetProductsServlet getProductsServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ServletTestUtils.clearTable();
        ServletTestUtils.insertTestRows();
        getProductsServlet = new GetProductsServlet();
    }

    @Test
    public void addTest() throws IOException {
        StringWriter writer = ServletTestUtils.mockServletResponseWriter(response);

        getProductsServlet.doGet(request, response);

        Assert.assertEquals("<html><body>\n" +
                        "cat\t10</br>\n" +
                        "dog\t20</br>\n" +
                        "</body></html>\n"
                , writer.toString());
    }

}
