package com.zsy.goods.service.impl;

import com.zsy.goods.common.utils.UUIDUtil;
import com.zsy.goods.mapper.UserMapper;
import com.zsy.goods.pojo.User;
import com.zsy.goods.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户模块业务层
 * @author ASUS
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 用户名注册校验
	 * @param loginname
	 * @return
	 */
	public boolean ajaxValidateLoginname(String loginname) {
		int index = userMapper.ajaxValidateLoginname(loginname);
		if(index == 0) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Email校验
	 * @param email
	 * @return
	 */
	public boolean ajaxValidateEmail(String email) {
		int index = userMapper.ajaxValidateEmail(email);
		if(index == 0) {
			return true;
		}else {
			return false;
		}
	}

	//注册功能
	@Override
	public Integer regist(User user) {
		/*
		 * 1. 数据的补齐
		 */
		user.setUid(UUIDUtil.getUUID());

		user.setStatus(true);

		user.setActivationCode(UUIDUtil.getUUID() + UUIDUtil.getUUID());

		/*
		 * 2. 向数据库插入
		 */
		int index = userMapper.add(user);
		return index;
	}

	/**
	 * 登录功能
	 */
	@Override
	public User login(User user) {
		
		return userMapper.findByLoginnameAndLoginpass(user.getLoginname(), user.getLoginpass());
	}

	/**
	 * 修改密码
	 */
	@Override
	public Integer updatePassword(String uid, String newPass, String oldPass) {
		/*
		 * 1. 校验老密码
		 */
		int index = userMapper.findByUidAndPassword(uid, oldPass);
		if(index == 0) {//如果老密码错误
			return index;//原密码错误
		}
		/*
		 * 2. 修改密码
		 */
		index = userMapper.updatePassword(uid, newPass);
		return index;//此时index!=0的s
	}

	@Override
	public boolean findByUidAndPassword(String uid, String password) {
		int index = userMapper.findByUidAndPassword(uid, password);
		if(index == 1) {
			return true;
		}else {
			return false;
		}
	}

}
