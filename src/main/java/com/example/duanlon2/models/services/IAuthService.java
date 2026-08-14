package com.example.duanlon2.models.services;

import com.example.duanlon2.models.dto.req.ActiveUserReq;
import com.example.duanlon2.models.dto.req.LoginReq;
import com.example.duanlon2.models.dto.req.RegisterReq;
import com.example.duanlon2.models.dto.res.LoginRes;
import jakarta.servlet.http.HttpServletRequest;

public interface IAuthService {
    void register(RegisterReq req);
    LoginRes login(LoginReq req);
    String activeUser(ActiveUserReq req);
    boolean verifyToken(String token);
    void logout(HttpServletRequest request);
}
