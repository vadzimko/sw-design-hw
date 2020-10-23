package info.vadzimko.refactoring.servlet;

import info.vadzimko.refactoring.html.ResponseBuilder;
import info.vadzimko.refactoring.storage.QueriesHandler;
import info.vadzimko.refactoring.storage.SelectQuery;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        String responseBody;
        switch (command) {
            case "max": {
                String sql = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
                responseBody = QueriesHandler.selectAll("Product with max price: ", sql);
                break;
            }
            case "min": {
                String sql = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
                responseBody = QueriesHandler.selectAll("Product with min price: ", sql);
                break;
            }
            case "sum": {
                String sql = "SELECT SUM(price) as sum FROM PRODUCT";
                responseBody = QueriesHandler.aggregate("Summary price: \n", sql, "sum");
                break;
            }
            case "count": {
                String sql = "SELECT COUNT(price) as count FROM PRODUCT";
                responseBody = QueriesHandler.aggregate("Number of products: \n", sql, "count");
                break;
            }
            default: {
                responseBody = "Unknown command: " + command;
                break;
            }
        }

        ResponseBuilder.setOkResponse(response, responseBody);
    }

}
