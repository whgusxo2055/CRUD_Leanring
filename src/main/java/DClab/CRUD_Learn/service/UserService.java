package DClab.CRUD_Learn.service;

import DClab.CRUD_Learn.dto.request.UserJoinRequestDto;
import DClab.CRUD_Learn.dto.request.UserLoginRequestDto;
import DClab.CRUD_Learn.dto.response.UserResponseDto;
import DClab.CRUD_Learn.dto.request.UserUpdateRequestDto;

public interface UserService{
    void join(UserJoinRequestDto requestDto);
    UserResponseDto findByEmail(String email);
    String updatePassword(UserUpdateRequestDto requestDto);
    String delete(UserLoginRequestDto requestDto);
}
