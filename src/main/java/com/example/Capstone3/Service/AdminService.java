package com.example.Capstone3.Service;

import com.example.Capstone3.Api.ApiException;
import com.example.Capstone3.Model.Admin;
import com.example.Capstone3.Repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public List<Admin> getAdmins(){
        return adminRepository.findAll();
    }

    public void addAdmin(Admin admin){
        adminRepository.save(admin);
    }

    public void updateAdmin(Integer id, Admin admin){
        Admin oldAdmin = adminRepository.findAdminById(id);
        if(oldAdmin == null){
            throw new ApiException("Admin not found");
        }
        oldAdmin.setFullName(admin.getFullName());
        oldAdmin.setUserName(admin.getUserName());
        oldAdmin.setPassword(admin.getPassword());
        adminRepository.save(oldAdmin);
    }

    public void deleteAdmin(Integer id){
        Admin admin = adminRepository.findAdminById(id);
        if(admin == null){
            throw new ApiException("Admin not found");
        }
        adminRepository.delete(admin);
    }
}
