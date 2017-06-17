package com.tmall.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.tmall.model.User;
import com.tmall.service.UserService;

@Controller("login")
@Scope("prototype")
public class loginAction  extends ActionSupport implements ServletRequestAware{

	private HttpServletRequest request;
	
	@Resource
	private UserService userService;
	
	@Override
	public String execute() throws Exception {
		String username = request.getParameter("userName");
		username = HtmlUtils.htmlEscape(username);//ת���ֹ�������
		String password = request.getParameter("password");
		password = HtmlUtils.htmlEscape(password);
		User user = userService.getUserByName(username);
		if(!userService.userIsExist(username))
		{
			System.out.println("�û�������");
			request.setAttribute("msg", "�û������������");
			return "login";
		}else if(user.getPassword() == password){
			System.out.println("�������");
			request.setAttribute("msg", "�û������������");
			return "login";
		}
		request.getSession().setAttribute("user", user);
		return SUCCESS;
	}
	
	
	public String logout() throws Exception{
		request.getSession().removeAttribute("user");
		return "logout";
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;	
	}

}
