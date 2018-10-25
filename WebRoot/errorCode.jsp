<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

  //首先获取error code
  Integer errorCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
  
  if(errorCode==404){
	  
	  //跳转到处理404错误的页面
	  response.sendRedirect(basePath+"404.jsp");
	  
  }else if(errorCode == 500){
	  
	  response.sendRedirect(basePath+"500.jsp");
  }
%>

