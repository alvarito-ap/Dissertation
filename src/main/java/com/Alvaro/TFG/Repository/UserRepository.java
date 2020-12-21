package com.Alvaro.TFG.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Alvaro.TFG.Model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findUserByName(String name);
	User findUserByUsername(String username);
	List<User> findUsersByRoleType(String role);
}
