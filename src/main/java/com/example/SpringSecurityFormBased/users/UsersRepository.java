package com.example.SpringSecurityFormBased.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    @Transactional
    public Users findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Users u " + "SET u.isEnabled = TRUE " + "WHERE u.email = ?1 ")
    int enableUser(String email);
}