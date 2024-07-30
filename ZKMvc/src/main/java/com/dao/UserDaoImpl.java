package com.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mapper.UserMapper;
import com.model.User;
import com.util.MyBatisUtil;
/**
 * <h3>This class represents that connectivity with mapper class</h3>
 * @author : Hinal Bhavsar
 * @version 1.01 20-07-2024
 */
@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	UserMapper mapper;
	
	@Override
	public void insertUser(User user) {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			session.insert("com.mapper.UserMapper.insertUser", user);
			session.commit();
		}
	}

	@Override
	public boolean emailExists(String email) {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			User user = session.selectOne("com.mapper.UserMapper.findByEmail", email);
			return user != null;
		}
	}

	@Override
	public boolean contactExists(String contact) {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			User user = session.selectOne("com.mapper.UserMapper.findByContact", contact);
			return user != null;
		}
	}

	@Override
	public void deleteUser(User user) {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			session.delete("com.mapper.UserMapper.deleteUser", user.getId());
			session.commit();
		}
	}

	@Override
	public void updateUser(User user) {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			session.update("com.mapper.UserMapper.updateUser", user);
			session.commit();
		}
	}

	@Override
	public List<User> getUsers(int page, int pageSize) {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			return session.selectList("com.mapper.UserMapper.getUsers", new Pagination(page, pageSize));
		}
	}

	public int getTotalUserCount() {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			return session.selectOne("com.mapper.UserMapper.getTotalUserCount");
		}
	}

}