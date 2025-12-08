package com.example.Capstone3.Repository;

import com.example.Capstone3.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Admin findAdminById(Integer id);
}
