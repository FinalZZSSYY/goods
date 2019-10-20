package com.zsy.goods.mapper;

import com.zsy.goods.pojo.User;
import org.apache.ibatis.annotations.Param;


/**
 * 用户模块持久层
 * @author ASUS
 *
 */
public interface UserMapper {
	/**
	 * 校验用户名是否注册
	 */
	Integer ajaxValidateLoginname(@Param("loginname") String loginname);
	
	/**
	 * 校验Email是否注册
	 */

	Integer ajaxValidateEmail(@Param("email")String email);
	
	/**
	 * 添加用户 
	 */

	Integer add(@Param("user")User user);
	
	/**
	 * 按用户名和密码查询
	 */
	User findByLoginnameAndLoginpass(@Param("loginname")String loginname,@Param("loginpass") String loginpass);
	
	/**
	 * 修改密码
	 */

	Integer updatePassword(@Param("uid")String uid, @Param("password")String password);
	
	/**
	 * 按uid和password查询源密码
	 */

	Integer findByUidAndPassword(@Param("uid")String uid, @Param("password")String password);
}
