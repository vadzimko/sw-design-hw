package info.vadzimko.refactoring.html;

import info.vadzimko.refactoring.storage.SelectQuery;

import java.util.ArrayList;

public class ResponseBuilder {

    public static String buildResponse(String body) {
        return buildResponse("", body);
    }

    public static String buildResponse(String header, String body) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>\n");

        if (header.length() > 0) {
            sb.append("<h1>").append(header).append("</h1>\n");
        }

        sb.append(body).append("\n").append("</body></html>");
        return sb.toString();
    }

    public static String fetchAllProducts(SelectQuery selectQuery) {
        ArrayList<String> bodyLines = new ArrayList<>();
        while (selectQuery.next()) {
            String name = selectQuery.getString("name");
            int price = selectQuery.getInt("price");
            bodyLines.add(name + "\t" + price + "</br>");
        }

        return String.join("\n", bodyLines);
    }

    public static String buildProductsResponse(SelectQuery selectQuery) {
        return buildResponse(fetchAllProducts(selectQuery));
    }

    public static String buildProductsResponse(String header, SelectQuery selectQuery) {
        return buildResponse(header, fetchAllProducts(selectQuery));
    }

    public static String buildAggregateResponse(String header, SelectQuery selectQuery, String aggregateName) {
        StringBuilder sb = new StringBuilder();
        sb.append(header);

        if (selectQuery.next()) {
            sb.append(selectQuery.getInt(aggregateName));
        }

        return buildResponse(sb.toString());
    }
}
