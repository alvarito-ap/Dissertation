package com.Alvaro.TFG.Service;

import com.Alvaro.TFG.Model.Role;
import com.Alvaro.TFG.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Alvaro.TFG.Model.User;
import com.Alvaro.TFG.Repository.UserRepository;

@Service("userService")
public class UserService {
	//declare as private set of repositories needed
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.roleRepository = roleRepository;
	}
	
	public User findUserByName(String name) {
		return userRepository.findUserByName(name);
	}

	public User findUserByUsername(String username){
		return userRepository.findUserByUsername(username);
	}
	
	public User saveUser(User user) {
		user.setStatus(true);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		Role role = roleRepository.findByType("USER");
		user.setRole(role);
		userRepository.save(user);
		return user;
	}
	
}
