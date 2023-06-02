package com.oslab.agent.controller.user;

import com.oslab.agent.model.transfer.userDto.SignInReqDto;
import com.oslab.agent.model.transfer.userDto.SignupReqDto;
import com.oslab.agent.model.transfer.userDto.User;
import com.oslab.agent.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("api/user/lookup/{user_id}")
    public ResponseEntity<?> lookup(@PathVariable Integer user_id) throws SQLException{
        User findUser = userService.lookup(user_id);
        System.out.println(findUser);
        return ResponseEntity.ok().body(userService.lookup(user_id));
    }

    @PostMapping("api/user/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupReqDto signupReqDto) throws SQLException{
        System.out.println(signupReqDto);
        boolean success = userService.signUp(signupReqDto);
        return success ? ResponseEntity.ok().body("Success") : ResponseEntity.internalServerError().body("Fail");
    }

    @PostMapping("api/user/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInReqDto signInReqDto) throws SQLException{
        boolean result = userService.signIn(signInReqDto);
        return result ? ResponseEntity.ok().body("로그인 성공") : ResponseEntity.ok().body("로그인 실패");
    }

    @GetMapping("api/user/duplicateEmail")
    public ResponseEntity<?> checkEmailDuplication(@RequestParam("email") String email) throws SQLException {
        boolean result = userService.checkEmailDuplication(email);
        return result ? ResponseEntity.ok().body("중복되는 이메일이 있습니다.") : ResponseEntity.ok().body("가입 가능한 이메일입니다.");
    }

    @GetMapping("api/user/checkAdmin")
    public ResponseEntity<?> checkAdmin(@RequestParam("email") String email) throws SQLException {
        boolean result = userService.checkAdmin(email);
        return result ? ResponseEntity.ok().body("조직 관리자입니다.") :  ResponseEntity.ok().body("관리자가 아닙니다.");
    }
}
