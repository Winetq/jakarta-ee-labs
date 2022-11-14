package jakarta.ee.user;

import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.Part;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class UserService {

    private UserRepository repository;

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

    @Transactional
    public void updateUserAvatar(User user, String updatedFileName, byte[] updatedAvatar) {
        repository.update(User.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .birthday(user.getBirthday())
                .login(user.getLogin())
                .password(user.getPassword())
                .role(user.getRole())
                .avatarFileName(updatedFileName)
                .avatar(updatedAvatar)
                .build()
        );
    }

    @Transactional
    public void updateUserAvatar(User user, Part avatar) {
        if (avatar != null && !avatar.getSubmittedFileName().isEmpty()) {
            try {
                updateUserAvatar(user, avatar.getSubmittedFileName(), avatar.getInputStream().readAllBytes());
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
