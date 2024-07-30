package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.UserDao;
import com.dao.UserDaoImpl;
import com.model.User;
/**
 * <h3>This class represents that connectivity with database</h3>
 * @author : Hinal Bhavsar
 * @version 1.01 20-07-2024
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao = new UserDaoImpl();

	@Override
	public void addUser(User user) {
		userDao.insertUser(user);
	}

	@Override
	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	@Override
	public boolean emailExists(String email) {
		return userDao.emailExists(email);
	}

	@Override
	public boolean contactExists(String contact) {
		return userDao.contactExists(contact);
	}

	@Override
	public void deleteUser(User user) {
		userDao.deleteUser(user);
	}

	@Override
	public List<User> getUsers(int page, int pageSize) {
		return userDao.getUsers(page, pageSize);
	}

	@Override
	public int getTotalUserCount() {
		return userDao.getTotalUserCount();
	}

}