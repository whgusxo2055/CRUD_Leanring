package DClab.CRUD_Learn.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserJoinRequestDto {
    private String email;
    private String password;
    private String name;
}
