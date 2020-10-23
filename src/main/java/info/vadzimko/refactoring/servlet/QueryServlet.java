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

                StringBuilder sb = new StringBuilder();

                while (selectQuery.next()) {
                    String name = selectQuery.getString("name");
                    int price = selectQuery.getInt("price");
                    sb.append(name).append("\t").append(price).append("</br>");
                }
                response.getWriter().println(ResponseBuilder.buildResponse("Product with max price: ", sb.toString()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("min".equals(command)) {
            try {
                SelectQuery selectQuery = new SelectQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
                selectQuery.execute();

                StringBuilder sb = new StringBuilder();
                while (selectQuery.next()) {
                    String name = selectQuery.getString("name");
                    int price = selectQuery.getInt("price");
                    sb.append(name).append("\t").append(price).append("</br>");
                }
                response.getWriter().println(ResponseBuilder.buildResponse("Product with min price: ", sb.toString()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("sum".equals(command)) {
            try {
                SelectQuery selectQuery = new SelectQuery("SELECT SUM(price) as sum FROM PRODUCT");
                selectQuery.execute();

                StringBuilder sb = new StringBuilder();
                sb.append("Summary price: \n");
                if (selectQuery.next()) {
                    sb.append(selectQuery.getInt("sum"));
                }
                response.getWriter().println(ResponseBuilder.buildResponse(sb.toString()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("count".equals(command)) {
            try {
                SelectQuery selectQuery = new SelectQuery("SELECT COUNT(*) as count FROM PRODUCT");
                selectQuery.execute();
                
                StringBuilder sb = new StringBuilder();
                sb.append("Number of products: \n");
                if (selectQuery.next()) {
                    sb.append(selectQuery.getInt("count"));
                }
                response.getWriter().println(ResponseBuilder.buildResponse(sb.toString()));
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
