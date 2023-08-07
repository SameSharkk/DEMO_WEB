package com.example.demo.controller;


import com.example.demo.Dto.ChangePasswordRequest;
import com.example.demo.entity.User;
import com.example.demo.service.serviceImpl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/test_change-password/{username}")
    public ResponseEntity<Object> changePassword (@PathVariable(name = "username") String username, @RequestBody ChangePasswordRequest passwordReq) {
        User user = userService.findByUsername(username).get();
        String newPassword = passwordReq.getNewPassword();
        // Mã hóa mật khẩu mới
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userService.saveUser(user);
        return ResponseEntity.ok("Đổi mật khẩu thành công");
    }



}
