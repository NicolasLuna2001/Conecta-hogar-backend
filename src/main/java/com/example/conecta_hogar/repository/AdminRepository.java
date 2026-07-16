package com.example.conecta_hogar.repository;

import com.example.conecta_hogar.model.AdminModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminModel, Long > {


}
