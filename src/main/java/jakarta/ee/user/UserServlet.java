package jakarta.ee.user;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = UserServlet.Paths.USER)
public class UserServlet extends HttpServlet {

    static class Paths {
        static final String USER = "/api/user";
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String languageHeader = request.getHeader("Accept-Language");
        System.out.println(languageHeader);
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("<h1>");
        out.print("Hello! Michael!!!");
        out.print("</h1>");
    }

}
