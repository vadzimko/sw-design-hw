package info.vadzimko.refactoring.servlet;

import info.vadzimko.refactoring.html.ResponseBuilder;
import info.vadzimko.refactoring.storage.QueriesHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String responseBody = QueriesHandler.selectAll("SELECT * FROM PRODUCT");

        ResponseBuilder.setOkResponse(response, responseBody);
    }
}
