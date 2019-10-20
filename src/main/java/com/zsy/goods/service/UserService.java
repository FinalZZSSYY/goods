package com.zsy.goods.service;

import com.zsy.goods.pojo.User;

/**
 * 用户模块业务层
 *
 */
public interface UserService {
	
	//用户名注册校验
	boolean ajaxValidateLoginname(String loginname);
	
	//Email校验
	boolean ajaxValidateEmail(String email);
	
	//注册功能
	Integer regist(User user);
	
	//登录验证
	User login(User user);
	
	//修改密码
	Integer updatePassword(String uid, String newPass, String oldPass);
	
	//验证原密码
	boolean findByUidAndPassword(String uid, String password);
}
