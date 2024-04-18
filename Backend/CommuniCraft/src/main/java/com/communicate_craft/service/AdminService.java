package com.communicate_craft.service;

import com.communicate_craft.dto.AuthenticationResponse;
import com.communicate_craft.dto.RegisterRequest;
import com.communicate_craft.model.User;

import java.util.List;

public interface AdminService {
    List<User> getAllAdmins();

    User getAdminById(Integer adminId);

    void deleteAdminById(Integer adminId);
    AuthenticationResponse registerNewAdmin(RegisterRequest request);
}
