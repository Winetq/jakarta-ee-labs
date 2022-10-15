package jakarta.ee.user;

import jakarta.ee.http.MimeTypes;
import jakarta.ee.http.ServletUtility;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = UserAvatarServlet.Paths.AVATARS + "/*")
public class UserAvatarServlet extends HttpServlet {

    private UserService service;

    @Inject
    public UserAvatarServlet(UserService service) {
        this.service = service;
    }

    static class Paths {
        static final String AVATARS = "/api/avatars";
    }

    static class Patterns {
        static final String AVATAR = "^/[0-9]+$";

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATARS.equals(servletPath) && path.matches(Patterns.AVATAR)) {
            getAvatar(path, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getAvatar(String path, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(path.replaceAll("/", ""));
        Optional<User> user = service.find(id);
        if (user.isPresent()) {
            byte[] avatar = user.get().getAvatar();
            response.addHeader(HttpHeaders.CONTENT_TYPE, MimeTypes.IMAGE_PNG);
            response.setContentLength(avatar.length);
            response.getOutputStream().write(avatar);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
