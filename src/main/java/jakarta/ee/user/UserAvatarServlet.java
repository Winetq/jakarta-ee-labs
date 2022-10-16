package jakarta.ee.user;

import jakarta.ee.http.MimeTypes;
import jakarta.ee.http.ServletUtility;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = UserAvatarServlet.Paths.AVATARS + "/*")
@MultipartConfig(maxFileSize = 200 * 1024)
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

    static class Parameters {
        static final String AVATAR = "avatar";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isCorrectUrl(request)) {
            getAvatarForUser(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getAvatarForUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Optional<User> user = findUserById(request);
        if (user.isPresent()) {
            byte[] avatar = user.get().getAvatar();
            response.addHeader(HttpHeaders.CONTENT_TYPE, MimeTypes.IMAGE_PNG);
            response.setContentLength(avatar.length);
            response.getOutputStream().write(avatar);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isCorrectUrl(request)) {
            deleteAvatarForUser(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void deleteAvatarForUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Optional<User> user = findUserById(request);
        if (user.isPresent()) {
            service.deleteAvatar(user.get());
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (isCorrectUrl(request)) {
            putAvatarForUser(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void putAvatarForUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Optional<User> user = findUserById(request);
        if (user.isPresent()) {
            service.updateAvatar(user.get(), request.getPart(Parameters.AVATAR));
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (isCorrectUrl(request)) {
            postAvatarForUser(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void postAvatarForUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<User> user = findUserById(request);
        if (user.isPresent()) {
            if (user.get().getAvatarFileName().isEmpty()) {
                service.createAvatar(user.get(), request.getPart(Parameters.AVATAR));
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private boolean isCorrectUrl(HttpServletRequest request) {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        return Paths.AVATARS.equals(servletPath) && path.matches(Patterns.AVATAR);
    }

    private Optional<User> findUserById(HttpServletRequest request) {
        String path = ServletUtility.parseRequestPath(request);
        Long id = Long.parseLong(path.replaceAll("/", ""));
        return service.find(id);
    }

}
