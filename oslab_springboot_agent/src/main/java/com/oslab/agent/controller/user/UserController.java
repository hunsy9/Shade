package com.oslab.agent.controller.user;

import com.oslab.agent.model.transfer.userDto.SignInReqDto;
import com.oslab.agent.model.transfer.userDto.SignupReqDto;
import com.oslab.agent.model.transfer.userDto.UserOrgs;
import com.oslab.agent.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class UserController {
    private final UserService userService;

    @GetMapping("api/user/getUserInfo/{user_id}")
    public ResponseEntity<?> getUserOrgInfo(@PathVariable Integer user_id) throws SQLException{
        return ResponseEntity.ok().body(userService.getUserOrgInfo(user_id));
    }

    @PostMapping("api/user/signup")
    public ResponseEntity<Boolean> signUp(@RequestBody SignupReqDto signupReqDto) throws SQLException{
        System.out.println(signupReqDto);
        boolean success = userService.signUp(signupReqDto);
        return ResponseEntity.ok().body(success);
    }

    @PostMapping("api/user/signin")
        public ResponseEntity<?> signIn(@RequestBody SignInReqDto signInReqDto) throws SQLException{
        boolean result = userService.signIn(signInReqDto);
        return result ? ResponseEntity.ok().body("로그인 성공") : ResponseEntity.ok().body("로그인 실패");
    }

    @GetMapping("api/user/duplicateEmail")
    public ResponseEntity<?> checkEmailDuplication(@RequestParam("email") String email) throws SQLException {
        boolean result = userService.checkEmailDuplication(email);
        return result ? ResponseEntity.ok().body(true) : ResponseEntity.ok().body(false);
    }

    @GetMapping("api/user/checkAdmin")
    public ResponseEntity<?> checkAdmin(@RequestParam("email") String email) throws SQLException {
        boolean result = userService.checkAdmin(email);
        return result ? ResponseEntity.ok().body("조직 관리자입니다.") :  ResponseEntity.ok().body("관리자가 아닙니다.");
    }
//    @GetMapping("api/user/getOrgInfo/{user_id}/{org_id}")
//    public ResponseEntity<?> getOrgInfo(@PathVariable Integer user_id, @PathVariable Integer org_id) throws SQLException {
//        return userService.
//    }

}
