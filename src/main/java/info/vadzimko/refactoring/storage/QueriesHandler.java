package info.vadzimko.refactoring.storage;

import info.vadzimko.refactoring.html.ResponseBuilder;

import java.util.ArrayList;

public class QueriesHandler {

    public static String selectAll(String title, String sql) {
        SelectQuery selectQuery = new SelectQuery(sql);
        selectQuery.execute();

       return buildProductsResponse(title, selectQuery);
    }

    public static String aggregate(String title, String sql, String aggregateName) {
        SelectQuery selectQuery = new SelectQuery(sql);
        selectQuery.execute();

       return buildAggregateResponse(title, selectQuery, aggregateName);
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

    public static String buildProductsResponse(String title, SelectQuery selectQuery) {
        return ResponseBuilder.buildResponse(title, fetchAllProducts(selectQuery));
    }

    public static String buildAggregateResponse(String title, SelectQuery selectQuery, String aggregateName) {
        StringBuilder sb = new StringBuilder();
        sb.append(title);

        if (selectQuery.next()) {
            sb.append(selectQuery.getInt(aggregateName));
        }

        return ResponseBuilder.buildResponse(sb.toString());
    }
}
