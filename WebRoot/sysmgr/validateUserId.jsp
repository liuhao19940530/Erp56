
<%@page import="com.xunpoit.user.User"%>
<%@page import="com.xunpoit.manager.UserManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
  request.setCharacterEncoding("utf-8");

  response.setContentType("text/html;charset=utf-8");
  
  String userId = request.getParameter("id");
  
  String time = request.getParameter("time");
  
  System.out.println(userId+":"+time);
  
  UserManager manager = new UserManager();
  
  User user = manager.findUserId(userId);
  
  if(user != null){
	  
	  out.print("此用户代码已经存在！请重新输入！");
  }
%>
