package com.dao;

import java.util.List;

import com.model.User;
/**
 * <h3>This class represents that connectivity with database</h3>
 * @author : Hinal Bhavsar
 * @version 1.01 20-07-2024
 */
public interface UserDao {

	void insertUser(User user);

	void updateUser(User user);

	void deleteUser(User user);

	boolean emailExists(String email);

	boolean contactExists(String contact);

	List<User> getUsers(int page, int pageSize);

	int getTotalUserCount();

}