package com.communicate_craft.service;

import com.communicate_craft.model.User;

import java.util.List;

public interface AdminService extends RegistrationService {
    List<User> getAllAdmins();

    User getAdminById(Long adminId);

    void deleteAdminById(Long adminId);
}
