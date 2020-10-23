package info.vadzimko.refactoring.servlet;

import info.vadzimko.refactoring.html.ResponseBuilder;
import info.vadzimko.refactoring.storage.QueriesHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));

        String sql = "INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";
        String responseBody = QueriesHandler.update(sql);

        ResponseBuilder.setOkResponse(response, responseBody);
    }
}
