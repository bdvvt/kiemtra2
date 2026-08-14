package com.example.duanlon2.models.repositories;

import com.example.duanlon2.models.constants.RoleName;
import com.example.duanlon2.models.entities.Course;
import com.example.duanlon2.models.entities.Role;
import com.example.duanlon2.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<com.example.duanlon2.models.entities.Role,Long> {
    Optional<Role> findByRoleName(RoleName roleName);
}

