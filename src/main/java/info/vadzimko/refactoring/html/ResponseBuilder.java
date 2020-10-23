package info.vadzimko.refactoring.html;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseBuilder {

    public static String buildResponse(String body) {
        return buildResponse("", body);
    }

    public static String buildResponse(String title, String body) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>\n");

        if (title.length() > 0) {
            sb.append("<h1>").append(title).append("</h1>\n");
        }

        sb.append(body).append("\n").append("</body></html>");
        return sb.toString();
    }

    public static void setOkResponse(HttpServletResponse response, String responseBody) throws IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(responseBody);
    }
}
