package info.vadzimko.refactoring.storage;

import info.vadzimko.refactoring.html.ResponseBuilder;
import info.vadzimko.refactoring.storage.db.DBSelectQuery;
import info.vadzimko.refactoring.storage.db.DBUpdateQuery;

import java.util.ArrayList;

public class QueriesHandler {

    public static String selectAll(String title, String sql) {
        DBSelectQuery selectQuery = new DBSelectQuery(sql);
        selectQuery.execute();

        return buildProductsResponse(title, selectQuery);
    }

    public static String aggregate(String title, String sql, String aggregateName) {
        DBSelectQuery selectQuery = new DBSelectQuery(sql);
        selectQuery.execute();

        return buildAggregateResponse(title, selectQuery, aggregateName);
    }

    public static String update(String sql) {
        DBUpdateQuery updateQuery = new DBUpdateQuery(sql);
        updateQuery.execute();

        return "OK";
    }

    public static String fetchAllProducts(DBSelectQuery selectQuery) {
        ArrayList<String> bodyLines = new ArrayList<>();
        while (selectQuery.next()) {
            String name = selectQuery.getString("name");
            int price = selectQuery.getInt("price");
            bodyLines.add(name + "\t" + price + "</br>");
        }

        return String.join("\n", bodyLines);
    }

    public static String buildProductsResponse(String title, DBSelectQuery selectQuery) {
        return ResponseBuilder.buildResponse(title, fetchAllProducts(selectQuery));
    }

    public static String buildAggregateResponse(String title, DBSelectQuery selectQuery, String aggregateName) {
        StringBuilder sb = new StringBuilder();
        sb.append(title);

        if (selectQuery.next()) {
            sb.append(selectQuery.getInt(aggregateName));
        }

        return ResponseBuilder.buildResponse(sb.toString());
    }
}
