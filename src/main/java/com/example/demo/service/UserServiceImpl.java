package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDAO;
import com.example.demo.model.User;
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserDAO userDAO;

	
	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		String password = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(password);
		userDAO.save(user);
	}

	@Override
	public boolean isUserPresent(User user) {
		// TODO Auto-generated method stub
		User user1 = findById(user.getUsername());
		if(user1 == null)
			return false;
		return true;
	}

	@Override
	public User findById(String id) {
		// TODO Auto-generated method stub
		List<User> users = userDAO.findAll();
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getUsername().equals(id))
				return users.get(i);
		}
		return null;
	}
	
	public UserDetails loadUserByUsername(String username) {
		List<User> users = userDAO.findAll();
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getUsername().equals(username))
				return users.get(i);
		}
		return null;
	}
}
