package info.vadzimko.refactoring.servlet;

import info.vadzimko.refactoring.storage.SelectQuery;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            try {
                SelectQuery selectQuery = new SelectQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
                selectQuery.execute();

                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>Product with max price: </h1>");

                while (selectQuery.next()) {
                    String name = selectQuery.getString("name");
                    int price = selectQuery.getInt("price");
                    response.getWriter().println(name + "\t" + price + "</br>");
                }
                response.getWriter().println("</body></html>");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("min".equals(command)) {
            try {
                SelectQuery selectQuery = new SelectQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
                selectQuery.execute();

                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>Product with min price: </h1>");

                while (selectQuery.next()) {
                    String name = selectQuery.getString("name");
                    int price = selectQuery.getInt("price");
                    response.getWriter().println(name + "\t" + price + "</br>");
                }
                response.getWriter().println("</body></html>");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("sum".equals(command)) {
            try {
                SelectQuery selectQuery = new SelectQuery("SELECT SUM(price) as sum FROM PRODUCT");
                selectQuery.execute();

                response.getWriter().println("<html><body>");
                response.getWriter().println("Summary price: ");

                if (selectQuery.next()) {
                    response.getWriter().println(selectQuery.getInt("sum"));
                }
                response.getWriter().println("</body></html>");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("count".equals(command)) {
            try {
                SelectQuery selectQuery = new SelectQuery("SELECT COUNT(*) as count FROM PRODUCT");
                selectQuery.execute();

                response.getWriter().println("<html><body>");
                response.getWriter().println("Number of products: ");

                if (selectQuery.next()) {
                    response.getWriter().println(selectQuery.getInt("count"));
                }
                response.getWriter().println("</body></html>");
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
