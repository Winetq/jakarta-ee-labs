package jakarta.ee.user;

import jakarta.ee.http.MimeTypes;
import jakarta.ee.user.dto.GetUserResponse;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = UserServlet.Paths.USER)
public class UserServlet extends HttpServlet {

    private UserService service;

    @Inject
    public UserServlet(UserService service) {
        this.service = service;
    }

    static class Paths {
        static final String USER = "/api/user";
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MimeTypes.APPLICATION_JSON);
        List<User> users = service.findAll();
        List<GetUserResponse> dtoUsers = GetUserResponse.entitiesToDtoMapper().apply(users);
        response.getWriter().write(jsonb.toJson(dtoUsers));
    }

}
