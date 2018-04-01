package com.wongxinjie.hackernews.controller;


import com.wongxinjie.hackernews.bean.ResultBean;
import com.wongxinjie.hackernews.bean.vo.UserVo;
import com.wongxinjie.hackernews.entity.User;
import com.wongxinjie.hackernews.exception.HNUserException;
import com.wongxinjie.hackernews.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1.0")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<ResultBean<Long>> login(@RequestBody UserVo user) {
        ResultBean<Long> response = new ResultBean<>();
        try {
            Long userId = loginService.login(user.getEmail(), user.getPassword());
            response.setData(userId);
            return ResponseEntity.ok(response);
        } catch (HNUserException e) {
            response.setCode(e.getCode());
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
    }


    @PostMapping("/register")
    public ResponseEntity<ResultBean<Long>> register(@RequestBody UserVo user) {
        ResultBean<Long> response = new ResultBean<>();
        try {
            Long userId = loginService.register(user.getEmail(), user.getPassword());
            response.setData(userId);
            return ResponseEntity.ok(response);
        } catch (HNUserException e) {
            response.setCode(e.getCode());
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<ResultBean<Boolean>> logout() {
        Long userId = 65487L;
        Boolean success = loginService.logout(userId);
        ResultBean<Boolean> response = new ResultBean<>(success);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<ResultBean<Long>> resetPassword(@RequestBody UserVo user) {
        ResultBean<Long> response = new ResultBean<>();
        Long userId = 65487L;
        try {
            Long uid = loginService.resetPassword(userId, user.getPassword(), user.getUsername());
            response.setData(uid);
            return ResponseEntity.ok(response);
        } catch (HNUserException e) {
            response.setCode(e.getCode());
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/my/profile")
    public ResponseEntity<ResultBean<Long>> updateProfile(@RequestBody UserVo user){
        ResultBean<Long> response = new ResultBean<>();
        Long userId = 65487L;

        try {
            Long uid = loginService.updateProfile(userId, user.getUsername());
            response.setData(uid);
            return ResponseEntity.ok(response);
        } catch (HNUserException e) {
            response.setCode(e.getCode());
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/my/profile")
    public ResponseEntity<ResultBean<User>> getProfile() {
        ResultBean<User> response = new ResultBean<>();
        Long userId = 65487L;
        User user = loginService.getUserProfile(userId);
        response.setData(user);
        return ResponseEntity.ok(response);
    }
}
