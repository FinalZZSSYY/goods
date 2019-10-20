package com.zsy.goods.controller;

import com.zsy.goods.pojo.User;
import com.zsy.goods.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



/**
 * 用户模块WEB层
 * @author ASUS
 *
 */
@Controller
@RequestMapping("/UserController")
public class UserController {
	
	@Autowired
	private UserService userServiceImpl;
	
	//ajax用户名是否注册校验
	@RequestMapping("/ajaxValidateLoginname")
	public String ajaxValidateLoginname(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		/*
		 * 1. 获取用户名
		 */
		String loginname = req.getParameter("loginname");
		System.out.println(loginname+"...........................");
		/*
		 * 2. 通过service得到校验结果
		 */
		boolean b = userServiceImpl.ajaxValidateLoginname(loginname);
		/*
		 * 3. 发给客户端
		 */
		resp.getWriter().print(b);
		return null;
	}
	
	//ajax Email是否注册校验
	@RequestMapping("/ajaxValidateEmail")
	public String ajaxValidateEmail(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		/*
		 * 1. 获取Email
		 */
		String email = req.getParameter("email");
		/*
		 * 2. 通过service得到校验结果
		 */
		boolean b = userServiceImpl.ajaxValidateEmail(email);
		/*
		 * 3. 发给客户端
		 */
		resp.getWriter().print(b);
		return null;
	}
	
	/**
	 * ajax验证码是否正确校验
	 */
	@RequestMapping("/ajaxValidateVerifyCode")
	public String ajaxValidateVerifyCode(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		/*
		 * 1. 获取输入框中的验证码
		 */
		String verifyCode = req.getParameter("verifyCode");
		/*
		 * 2. 获取图片上真实的校验码
		 */
		String vcode = (String) req.getSession().getAttribute("vCode");
		/*
		 * 3. 进行忽略大小写比较，得到结果
		 */
		boolean b = verifyCode.equalsIgnoreCase(vcode);
		/*
		 * 4. 发送给客户端
		 */
		resp.getWriter().print(b);
		return null;
	}
	
	@GetMapping("/regist")
	public String getRegist(){
		return "user/regist";
	}
	
	//注册
	@PostMapping("/regist")
	public String regist(User user,Model model){
		System.out.println(user+"..............");
		User formUser = user;
		/*
		 * 2. 校验之, 如果校验失败，保存错误信息，返回到regist.html显示
		 */
		Map<String,String> errors = validateRegist(formUser);
		System.out.println(errors);
		if(errors.get("loginname") != null && errors.get("loginpass") != null && errors.get("reloginpass") != null && errors.get("email") != null) {
			model.addAttribute("form", formUser);
			model.addAttribute("errors", errors);
			return "user/regist";
		}
		/*
		 * 3. 使用service完成业务
		 */
		int index = userServiceImpl.regist(formUser);
		/*
		 * 4. 保存成功信息，转发到msg.jsp显示！
		 */
		model.addAttribute("code", "success");
		model.addAttribute("msg", "注册成功！");
		return "msg";
	}
	
	/*
	 * 注册校验
	 * 对表单的字段进行逐个校验，如果有错误，使用当前字段名称为key，错误信息为value，保存到map中
	 * 返回map
	 */
	@RequestMapping("/validateRegist")
	private Map<String,String> validateRegist(User formUser) {
		Map<String,String> errors = new HashMap<String,String>();

		/*
		 * 1. 校验登录名
		 */
		String loginname = formUser.getLoginname();
		if(loginname == null || loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空！");
		} else if(loginname.length() < 2 || loginname.length() > 20) {
			errors.put("loginname", "用户名长度必须在2~20之间！");
		} else if(!userServiceImpl.ajaxValidateLoginname(loginname)) {
			errors.put("loginname", "用户名已被注册！");
		}else{
			errors.put("loginname", null);
		}
		
		/*
		 * 2. 校验登录密码
		 */
		String loginpass = formUser.getLoginpass();
		if(loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpass", "密码不能为空！");
		} else if(loginpass.length() < 3 || loginpass.length() > 20) {
			errors.put("loginpass", "密码长度必须在3~20之间！");
		}else{
			errors.put("loginpass", null);
		}
		
		/*
		 * 3. 确认密码校验
		 */
		String reloginpass = formUser.getReloginpass();
		if(reloginpass == null || reloginpass.trim().isEmpty()) {
			errors.put("reloginpass", "确认密码不能为空！");
		} else if(!reloginpass.equals(loginpass)) {
			errors.put("reloginpass", "两次输入不一致！");
		}else{
			errors.put("reloginpass", null);
		}
		
		/*
		 * 4. 校验email
		 */
		String email = formUser.getEmail();
		if(email == null || email.trim().isEmpty()) {
			errors.put("email", "Email不能为空！");
		} else if(!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
			errors.put("email", "Email格式错误！");
		} else if(!userServiceImpl.ajaxValidateEmail(email)) {
			errors.put("email", "Email已被注册！");
		}else{
			errors.put("email", null);
		}
		
//		/*
//		 * 5. 验证码校验
//		 */
//		String verifyCode = formUser.getVerifyCode();
//		String vcode = (String) session.getAttribute("vCode");
//		if(verifyCode == null || verifyCode.trim().isEmpty()) {
//			errors.put("verifyCode", "验证码不能为空！");
//		} else if(!verifyCode.equalsIgnoreCase(vcode)) {
//			errors.put("verifyCode", "验证码错误！");
//		}else{
//			errors.put("verifyCode", null);
//		}
		
		return errors;
	}

	@GetMapping("/login")
	public String getLogin(){
		System.out.println("==============================");
		return "user/login";
	}
	
	//登录功能
	@PostMapping("/login")
	public String login(User user,
						Model model,
						HttpServletRequest request){
		System.out.println(user+"+++++++++++++++++++++");
		/*
		 * 1. 封装表单数据到User
		 * 2. 校验表单数据
		 * 3. 使用service查询，得到User
		 * 4. 查看用户是否存在，如果不存在：
		 *   * 保存错误信息：用户名或密码错误
		 *   * 保存用户数据：为了回显
		 *   * 转发到login.jsp
		 * 5. 如果存在，查看状态，如果状态为false：
		 *   * 保存错误信息：您没有激活
		 *   * 保存表单数据：为了回显
		 *   * 转发到login.jsp
		 * 6. 登录成功：
		 * 　　* 保存当前查询出的user到session中
		 *   * 保存当前用户的名称到cookie中，注意中文需要编码处理。
		 */
		User formUser = user;
		/*
		 * 2. 校验
		 */
		Map<String,String> errors = validateLogin(formUser);
		if(errors.get("loginname") != null && errors.get("loginpass") != null ) {
		    model.addAttribute("form", formUser);
            model.addAttribute("errors", errors);
			return "user/login";
		}
		
		/*
		 * 3. 调用userService#login()方法
		 */
		User u = userServiceImpl.login(formUser);
		/*
		 * 4. 开始判断
		 */
		if(u == null) {
            model.addAttribute("msg", "用户名或密码错误！");
            model.addAttribute("user", formUser);
			return "user/login";
		} else {
			// 保存用户到session
			request.getSession().setAttribute("user", u);
			// 获取用户名保存到cookie中
//			String loginname = u.getLoginname();
//			loginname = URLEncoder.encode(loginname, "utf-8");
//			Cookie cookie = new Cookie("loginname", loginname);
//			cookie.setMaxAge(60 * 60 * 24 * 10);//保存10天
//			resp.addCookie(cookie);
			return "main";//到主页
		}
	}
	
	/*
	 * 登录校验方法
	 */
	private Map<String,String> validateLogin(User formUser) {
		Map<String,String> errors = new HashMap<String,String>();
//		System.out.println("+++++++++"+formUser+"+++++++++++++");
//		System.out.println(session.getAttribute("vCode"));
		/*
		 * 1. 校验登录名
		 */
		String loginname = formUser.getLoginname();
		if(loginname == null || loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空！");
		} else if(loginname.length() < 2 || loginname.length() > 20) {
			errors.put("loginname", "用户名长度必须在2~20之间！");
		}else{
			errors.put("loginname", null);
		}
		
		/*
		 * 2. 校验登录密码
		 */
		String loginpass = formUser.getLoginpass();
		if(loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpass", "密码不能为空！");
		} else if(loginpass.length() < 3 || loginpass.length() > 20) {
			errors.put("loginpass", "密码长度必须在3~20之间！");
		}else{
			errors.put("loginpass", null);
		}
			
		
		/*
		 * 3. 验证码校验
		 */
//		String verifyCode = formUser.getVerifyCode();
//		String vcode = (String) session.getAttribute("vCode");
//		if(verifyCode == null || verifyCode.trim().isEmpty()) {
//			errors.put("verifyCode", "验证码不能为空！");
//		} else if(!verifyCode.equalsIgnoreCase(vcode)) {
//			errors.put("verifyCode", "验证码错误！");
//		}else{
//			errors.put("verifyCode", null);
//		}
		
		return errors;
	}

	@GetMapping("/updatePassword")
	public String getUpdatePassword(){
		return "user/pwd";
	}
	/**
	 * 修改密码　
	 */
	@PostMapping("/updatePassword")
	public String updatePassword(User user,
								 Model model,
								 HttpServletRequest request) {
		System.out.println(user+"+++++++++++++++++++++");
		/*
		 * 1. 封装表单数据到user中
		 * 2. 从session中获取uid
		 * 3. 使用uid和表单中的oldPass和newPass来调用service方法
		 *   > 如果出现异常，保存异常信息到request中，转发到pwd.jsp
		 * 4. 保存成功信息到rquest中
		 * 5. 转发到msg.jsp
		 */
		User formUser = user;
		User u = (User)request.getSession().getAttribute("user");
		// 如果用户没有登录，返回到登录页面，显示错误信息
		if(u == null) {
			model.addAttribute("msg", "您还没有登录！");
			return "user/login";
		}
		int index = userServiceImpl.updatePassword(u.getUid(), formUser.getNewpass(), formUser.getLoginpass());
		if(index == 0) {
			model.addAttribute("msg", "老密码错误！");//保存信息到request
			model.addAttribute("user", formUser);//为了回显
			return "user/pwd";
		}else {
			u.setLoginpass(formUser.getNewpass());
			u.setNewpass(formUser.getNewpass());
			// 保存用户到session
			request.getSession().setAttribute("user", u);
			model.addAttribute("msg", "修改密码成功");
			model.addAttribute("code", "success");
			return "msg";
		}
		
	}
	
	/**
	 * ajax 校验原密码
	 */
	@RequestMapping("/ajaxvalidateLoginpass")
	public String ajaxvalidateLoginpass(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		/*
		 * 1. 获取Email
		 */
		String loginpass = req.getParameter("loginpass");
		User u = (User)req.getSession().getAttribute("sessionUser");
		/*
		 * 2. 通过service得到校验结果
		 */
		System.out.println(u.getUid()+"+++++++++++++++++++++++++++++");
		boolean b = userServiceImpl.findByUidAndPassword(u.getUid(), loginpass);
		/*
		 * 3. 发给客户端,ajax接收
		 */
		resp.getWriter().print(b);
		return null;
	}
	
	/**
	 * 退出功能
	 */
	@GetMapping("/quit")
	public String quit(HttpServletRequest req){
		System.out.println("退出"+"++++++++++++++++++++++++++");
		req.getSession().removeAttribute("user");
		return "main";//重定向
	}

}
