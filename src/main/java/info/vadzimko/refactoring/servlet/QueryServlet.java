package info.vadzimko.refactoring.servlet;

import info.vadzimko.refactoring.html.ResponseBuilder;
import info.vadzimko.refactoring.storage.SelectQuery;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            try {
                SelectQuery selectQuery = new SelectQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
                selectQuery.execute();

                response.getWriter().println(ResponseBuilder.buildProductsResponse("Product with max price: ", selectQuery));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("min".equals(command)) {
            try {
                SelectQuery selectQuery = new SelectQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
                selectQuery.execute();

                response.getWriter().println(ResponseBuilder.buildProductsResponse("Product with min price: ", selectQuery));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("sum".equals(command)) {
            try {
                SelectQuery selectQuery = new SelectQuery("SELECT SUM(price) as sum FROM PRODUCT");
                selectQuery.execute();

                response.getWriter().println(ResponseBuilder.buildAggregateResponse("Summary price: \n", selectQuery, "sum"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("count".equals(command)) {
            try {
                SelectQuery selectQuery = new SelectQuery("SELECT COUNT(*) as count FROM PRODUCT");
                selectQuery.execute();

                response.getWriter().println(ResponseBuilder.buildAggregateResponse("Number of products: \n", selectQuery, "count"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
