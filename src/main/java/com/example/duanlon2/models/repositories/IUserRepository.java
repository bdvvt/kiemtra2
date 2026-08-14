package com.example.duanlon2.models.repositories;

import com.example.duanlon2.models.constants.RoleName;
import com.example.duanlon2.models.entities.User;
import com.example.duanlon2.models.constants.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.email = :identifier OR u.username = :identifier")
    Optional<User> findByEmailOrUsername(@Param("identifier") String identifier);

    @Query("""
                    SELECT u 
                    FROM User u
                    JOIN u.roles r
                    WHERE (:role IS NULL OR r.roleName = :role)
                    AND (:status IS NULL OR u.status = :status)
                                        """)
    List<User> findAllByRoleAndStatus(@Param("role") RoleName role, @Param("status") UserStatus status);

    @Query("""
       SELECT u 
       FROM User u 
       JOIN u.roles r 
       WHERE u.id = :id AND r.roleName = 'TEACHER'
       """)
    Optional<User> findTeacherById(@Param("id") Long id);

}
