package com.wongxinjie.hackernews.controller;


import com.wongxinjie.hackernews.bean.ResultBean;
import com.wongxinjie.hackernews.bean.vo.UserRequestVO;
import com.wongxinjie.hackernews.bean.vo.UserResponseVO;
import com.wongxinjie.hackernews.common.CookieUtils;
import com.wongxinjie.hackernews.config.security.SessionUser;
import com.wongxinjie.hackernews.entity.User;
import com.wongxinjie.hackernews.exception.UserException;
import com.wongxinjie.hackernews.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@RequestMapping("/v1.0")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<ResultBean<UserResponseVO>> login(@RequestBody UserRequestVO user, HttpServletRequest request, HttpServletResponse response) {
        ResultBean<UserResponseVO> payload = new ResultBean<>();
        try {
            UserResponseVO responseVO= loginService.login(user.getEmail(), user.getPassword());
            CookieUtils.setCookie(request, response, "ticket", responseVO.getTicket());

            payload.setData(responseVO);
            return ResponseEntity.ok(payload);
        } catch (UserException e) {
            payload.setCode(e.getCode());
            payload.setMessage(e.getMessage());
            return new ResponseEntity<>(payload, HttpStatus.FORBIDDEN);
        }
    }


    @PostMapping("/register")
    public ResponseEntity<ResultBean<Long>> register(@RequestBody UserRequestVO user) {
        ResultBean<Long> response = new ResultBean<>();
        try {
            Long userId = loginService.register(user.getEmail(), user.getPassword());
            response.setData(userId);
            return ResponseEntity.ok(response);
        } catch (UserException e) {
            response.setCode(e.getCode());
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<ResultBean<Boolean>> logout(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        String ticket = CookieUtils.getCookieValue(servletRequest, "ticket");
        Boolean success = loginService.logout(ticket);

        CookieUtils.deleteCookie(servletRequest, servletResponse, "ticket");
        ResultBean<Boolean> response = new ResultBean<>(success);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<ResultBean<Long>> resetPassword(@RequestBody UserRequestVO user, Authentication authentication) {
        ResultBean<Long> response = new ResultBean<>();
        SessionUser principal = (SessionUser) authentication.getPrincipal();

        try {
            Long uid = loginService.resetPassword(principal.getId(), user.getPassword(), user.getUsername());
            response.setData(uid);
            return ResponseEntity.ok(response);
        } catch (UserException e) {
            response.setCode(e.getCode());
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/me/profile")
    public ResponseEntity<ResultBean<Long>> updateProfile(@RequestBody UserRequestVO user, Authentication authentication){
        SessionUser pricipal = (SessionUser) authentication.getPrincipal();

        ResultBean<Long> response = new ResultBean<>();
        try {
            Long uid = loginService.updateProfile(pricipal.getId(), user.getUsername());
            response.setData(uid);
            return ResponseEntity.ok(response);
        } catch (UserException e) {
            response.setCode(e.getCode());
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/me/profile")
    public ResponseEntity<ResultBean<User>> getProfile(Authentication authentication) {
        ResultBean<User> response = new ResultBean<>();
        SessionUser principal = (SessionUser) authentication.getPrincipal();

        User user = loginService.getUserProfile(principal.getId());
        response.setData(user);
        return ResponseEntity.ok(response);
    }
}
