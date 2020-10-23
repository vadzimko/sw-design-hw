package info.vadzimko.refactoring.servlet;

import info.vadzimko.refactoring.html.ResponseBuilder;
import info.vadzimko.refactoring.storage.SelectQuery;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            SelectQuery selectQuery = new SelectQuery("SELECT * FROM PRODUCT");
            selectQuery.execute();

            response.getWriter().println(ResponseBuilder.buildProductsResponse(selectQuery));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
