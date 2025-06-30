package DClab.CRUD_Learn.controller;

import DClab.CRUD_Learn.dto.request.UserJoinRequestDto;
import DClab.CRUD_Learn.dto.request.UserLoginRequestDto;
import DClab.CRUD_Learn.dto.response.UserResponseDto;
import DClab.CRUD_Learn.dto.request.UserUpdateRequestDto;
import DClab.CRUD_Learn.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 메인 페이지 - 인증된 사용자만 접근 가능
    @GetMapping("/")
    public String home() {
        // 현재 로그인한 사용자 정보를 가져올 수 있음
        return "index";
    }

    @GetMapping("/login-page")
    public String loginPage() {
        // 이미 로그인된 사용자는 메인페이지로 리다이렉트
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/join-page")
    public String joinPage() {
        return "join";
    }

    // 회원가입 API
    @PostMapping("/join")
    @ResponseBody
    public ResponseEntity<String> join(@RequestBody UserJoinRequestDto requestDto) {
        try {
            userService.join(requestDto);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
    }

    @GetMapping("/{email}")
    @ResponseBody
    public ResponseEntity<UserResponseDto> getUser(@PathVariable String email){
        UserResponseDto userDto=userService.findByEmail(email);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<?> update(@RequestBody UserUpdateRequestDto requestDto) {
        return ResponseEntity.ok(userService.updatePassword(requestDto));
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<?> delete(@RequestBody UserLoginRequestDto requestDto) {
        return ResponseEntity.ok(userService.delete(requestDto));
    }
}
