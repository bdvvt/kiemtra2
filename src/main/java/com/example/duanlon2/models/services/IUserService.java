package com.example.duanlon2.models.services;

import com.example.duanlon2.models.constants.RoleName;
import com.example.duanlon2.models.constants.UserStatus;
import com.example.duanlon2.models.dto.req.UserPassReq;
import com.example.duanlon2.models.dto.req.UserReq;
import com.example.duanlon2.models.dto.req.UserStatusReq;
import com.example.duanlon2.models.dto.req.UserUpRoleReq;
import com.example.duanlon2.models.entities.User;

import java.util.List;

public interface IUserService {
    User createUser(UserReq req);
    User updateUser(User currentUser,Long id, UserReq req);
    User updateUserPassword(User currentUser,Long id, UserPassReq req);
    User updateUserRole(Long id, UserUpRoleReq req);
    User findById(Long id);
    void deleteUser(Long id);
    List<User> findAll(RoleName role, UserStatus status);
    User updateUserStatus(Long id, UserStatusReq req);
}
