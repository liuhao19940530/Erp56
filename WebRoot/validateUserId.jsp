<%@page import="com.xunpoit.user.User"%>
<%@page import="com.xunpoit.manager.UserManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
   String userId = request.getParameter("id");
   String time = request.getParameter("time");
   
   System.out.println(userId+":"+time);
   
   UserManager manager = new UserManager();
   
   User user = manager.findUserId(userId);
   
   if(user != null){
	   
	   out.print("此用户代码存在数据库中！");
   }
%>