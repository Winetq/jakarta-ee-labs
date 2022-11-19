package jakarta.ee.user.dto;

import jakarta.ee.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * GET user response. Contains only field's which can be displayed on frontend. User is defined in
 * {@link jakarta.ee.user.User}.
 */
@Builder
@Getter
public class GetUserResponse {
    private final Long id;
    private final String name;
    private final String surname;
    private final LocalDate birthday;
    private final String login;
    private final List<String> userRoles;

    public static Function<User, GetUserResponse> entityToDtoMapper() {
        return user -> GetUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .birthday(user.getBirthday())
                .login(user.getLogin())
                .userRoles(user.getUserRoles())
                .build();
    }

    public static Function<List<User>, List<GetUserResponse>> entitiesToDtoMapper() {
        return users -> users
                .stream()
                .map(user -> entityToDtoMapper().apply(user))
                .collect(Collectors.toList());
    }
}
