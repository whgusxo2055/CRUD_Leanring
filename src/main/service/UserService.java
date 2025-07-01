package DClab.CRUD_Learn.src.main.service;

import DClab.CRUD_Learn.src.main.dto.request.UserJoinRequestDto;
import DClab.CRUD_Learn.src.main.dto.request.UserLoginRequestDto;
import DClab.CRUD_Learn.src.main.dto.response.UserResponseDto;
import DClab.CRUD_Learn.src.main.dto.request.UserUpdateRequestDto;

public interface UserService{
    void join(UserJoinRequestDto requestDto);
    UserResponseDto findByEmail(String email);
    String updatePassword(UserUpdateRequestDto requestDto);
    String delete(UserLoginRequestDto requestDto);
}
