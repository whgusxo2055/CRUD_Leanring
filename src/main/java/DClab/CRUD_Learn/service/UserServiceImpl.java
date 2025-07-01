package DClab.CRUD_Learn.service;


import DClab.CRUD_Learn.domain.Role;
import DClab.CRUD_Learn.domain.User;
import DClab.CRUD_Learn.dto.request.UserJoinRequestDto;
import DClab.CRUD_Learn.dto.request.UserLoginRequestDto;
import DClab.CRUD_Learn.dto.request.UserUpdateRequestDto;
import DClab.CRUD_Learn.dto.response.UserResponseDto;
import DClab.CRUD_Learn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Spring Security를 위한 UserDetailsService 구현
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(String.valueOf(Role.USER)) // 기본 역할 설정
                .build();
    }

    @Override
    @Transactional
    public void join(UserJoinRequestDto requestDto) {
        if(userRepository.findByEmail(requestDto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        userRepository.save(User.builder()
                .email(requestDto.getEmail())
                .password(encodedPassword)
                .name(requestDto.getName())
                .build());
    }

    @Override
    public UserResponseDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        return UserResponseDto.from(user);
    }

    @Transactional
    @Override
    public String updatePassword(UserUpdateRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if(!passwordEncoder.matches(requestDto.getOldPassword(), user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String encodedNewPassword = passwordEncoder.encode(requestDto.getNewPassword());
        userRepository.save(user.updatePassword(encodedNewPassword));
        return "비밀번호 변경 완료";
    }

    @Override
    @Transactional
    public String delete(UserLoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        userRepository.delete(user);
        return "삭제 완료";
    }
}
