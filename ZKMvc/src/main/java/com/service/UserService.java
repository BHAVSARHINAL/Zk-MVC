package com.service;

import java.util.List;

import com.model.User;
/**
 * <h3>This class represents that method for crud operation</h3>
 * @author : Hinal Bhavsar
 * @version 1.01 20-07-2024
 */
public interface UserService {

	void addUser(User user);

	void updateUser(User user);

	void deleteUser(User user);

	boolean emailExists(String email);

	boolean contactExists(String contact);

	List<User> getUsers(int page, int pageSize);

	int getTotalUserCount();

}