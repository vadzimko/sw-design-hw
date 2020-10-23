package info.vadzimko.refactoring.html;

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
}
