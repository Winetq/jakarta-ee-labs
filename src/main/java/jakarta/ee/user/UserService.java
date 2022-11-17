package jakarta.ee.user;

import lombok.NoArgsConstructor;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
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

    public void create(User user) {
        repository.create(user);
    }

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
