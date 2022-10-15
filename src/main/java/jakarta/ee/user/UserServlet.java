package jakarta.ee.user;

import jakarta.ee.http.MimeTypes;
import jakarta.ee.http.ServletUtility;
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
import java.util.Optional;

@WebServlet(urlPatterns = UserServlet.Paths.USERS + "/*")
public class UserServlet extends HttpServlet {

    private UserService service;

    @Inject
    public UserServlet(UserService service) {
        this.service = service;
    }

    static class Paths {
        static final String USERS = "/api/users";
    }

    static class Patterns {
        static final String USER = "^/[0-9]+$";

    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.USERS.equals(servletPath)) {
            if (path.matches(Patterns.USER)) {
                getUser(path, response);
            } else {
                getUsers(response);
            }
        }
    }

    private void getUser(String path, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(path.replaceAll("/", ""));
        Optional<User> user = service.find(id);
        if (user.isPresent()) {
            GetUserResponse dtoUser = GetUserResponse.entityToDtoMapper().apply(user.get());
            response.setContentType(MimeTypes.APPLICATION_JSON);
            response.getWriter().write(jsonb.toJson(dtoUser));
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getUsers(HttpServletResponse response) throws IOException {
        List<User> users = service.findAll();
        List<GetUserResponse> dtoUsers = GetUserResponse.entitiesToDtoMapper().apply(users);
        response.setContentType(MimeTypes.APPLICATION_JSON);
        response.getWriter().write(jsonb.toJson(dtoUsers));
    }

}
