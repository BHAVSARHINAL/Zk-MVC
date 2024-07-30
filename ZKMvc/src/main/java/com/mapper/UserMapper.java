package com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model.User;
/**
 * <h3>This class represents that connection with mapper file</h3>
 * @author : Hinal Bhavsar
 * @version 1.01 20-07-2024
 */
public interface UserMapper {

	void insertUser(User user);

	void updateUser(User user);

	void deleteUser(User user);

	List<User> getUsers(@Param("offset") int offset, @Param("limit") int limit);

	int getTotalUserCount();

}