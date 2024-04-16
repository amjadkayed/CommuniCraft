package com.communicate_craft.admin;

import com.communicate_craft.authentication.dto.AuthenticationResponse;
import com.communicate_craft.authentication.dto.RegisterRequest;
import com.communicate_craft.user.User;

import java.util.List;

public interface AdminService {
    List<User> getAllAdmins();

    User getAdminById(Integer adminId);

    void deleteAdminById(Integer adminId);
    AuthenticationResponse registerNewAdmin(RegisterRequest request);
}
