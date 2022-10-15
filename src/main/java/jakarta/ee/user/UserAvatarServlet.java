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
import javax.servlet.http.Part;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.io.InputStream;
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
            getAvatar(ServletUtility.parseRequestPath(request), response);
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

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isCorrectUrl(request)) {
            deleteAvatar(ServletUtility.parseRequestPath(request), response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void deleteAvatar(String path, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(path.replaceAll("/", ""));
        Optional<User> user = service.find(id);
        if (user.isPresent()) {
            user.get().setAvatar(new byte[0]);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (isCorrectUrl(request)) {
            putAvatar(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void putAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = ServletUtility.parseRequestPath(request);
        Long id = Long.parseLong(path.replaceAll("/", ""));
        Optional<User> user = service.find(id);
        if (user.isPresent()) {
            Part avatar = request.getPart(Parameters.AVATAR);
            if (avatar != null) {
                InputStream is = avatar.getInputStream();
                user.get().setAvatar(is.readAllBytes());
            }
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private boolean isCorrectUrl(HttpServletRequest request) {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        return Paths.AVATARS.equals(servletPath) && path.matches(Patterns.AVATAR);
    }

}
