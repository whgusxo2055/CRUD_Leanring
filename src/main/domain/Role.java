package DClab.CRUD_Learn.src.main.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    private final String value;
}
