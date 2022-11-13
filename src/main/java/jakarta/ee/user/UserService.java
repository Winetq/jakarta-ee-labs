package jakarta.ee.user;

import lombok.NoArgsConstructor;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.Part;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class UserService {

    private UserRepository repository;

    @Resource(name = "avatars.directory")
    private String avatarsDirectory;

    @Inject
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> find(Long id) {
        return repository.find(id);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void create(User user) {
        repository.create(user);
    }

    public void deleteAvatar(User user) {
        Path path = Path.of(avatarsDirectory, user.getAvatarFileName());
        try {
            if (Files.deleteIfExists(path)) {
                user.setAvatarFileName("");
                user.setAvatar(new byte[0]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void updateAvatar(User user, Part avatar) {
        if (avatar != null && !avatar.getSubmittedFileName().isEmpty()) {
            if (!user.getAvatarFileName().isEmpty()) {
                deleteAvatar(user);
            }
            createAvatar(user, avatar);
        }
    }

    public void createAvatar(User user, Part avatar) {
        if (avatar != null && !avatar.getSubmittedFileName().isEmpty()) {
            Path path = Path.of(avatarsDirectory, avatar.getSubmittedFileName());
            try {
                if (!Files.exists(path)) {
                    Files.createFile(path);
                    Files.write(path, avatar.getInputStream().readAllBytes(), StandardOpenOption.WRITE);
                }
                user.setAvatarFileName(avatar.getSubmittedFileName());
                user.setAvatar(avatar.getInputStream().readAllBytes());
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
