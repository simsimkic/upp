package com.upp.nc.nc.repositories;

import com.upp.nc.nc.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);
    List<User> findByRole(int role);
    List<User> findByReviewer(boolean reviewer);

}
