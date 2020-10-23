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

            StringBuilder sb = new StringBuilder();
            boolean firstLine = true;
            while (selectQuery.next()) {
                if (!firstLine) {
                    sb.append("\n");
                }

                String name = selectQuery.getString("name");
                int price = selectQuery.getInt("price");
                sb.append(name).append("\t").append(price).append("</br>");
                firstLine = false;
            }

            response.getWriter().println(ResponseBuilder.buildResponse(sb.toString()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
