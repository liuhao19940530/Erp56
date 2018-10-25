<%@page import="com.xunpoit.manager.UserManager"%>
<%@page import="com.xunpoit.user.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%

   String msg="";
   String cmd = request.getParameter("cmd");
   
   if("modifyPassword".equals(cmd)){

	   String oldPassword = request.getParameter("oldPassword");
	   String newPassword = request.getParameter("newPassword"); 
	   String affirmNewPassword = request.getParameter("affirmNewPassword");
		  
	   UserManager manager = new UserManager();
	  
	   User user = manager.queryPassword(oldPassword);
	  
	   if(user != null){
		  
		   if(newPassword.equals(affirmNewPassword)){
			   
			   User user01 = (User)session.getAttribute("user02");
			   
			   String userId=user01.getUserId();
			   
			   User user02 = new User();
			   
			   user02.setPassword(affirmNewPassword);
			   
			   user02.setUserId(userId);
			   
			   boolean flag = manager.modifyPassword(user02);
			   
			   if(flag){
				   
				   msg="修改成功！";
				 
			   }
			   
		   }else{
			   
			   msg = "新密码和确认密码不一致！";
		   }   
		 
	   }else{
		  
		  msg="旧密码错误！请重新输入！";
	   }
   
   }	   
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>修改密码</title>
		<link rel="stylesheet" href="../style/erp.css">
		<script type="text/javascript">
	
	function modifyPasword() {
		
		var oldPassword = document.getElementById("oldPassword");
		oldPassword.focus();
		//alert(oldPassword);
		
		var newPassword = document.getElementById("newPassword");
		newPassword.focus();
		//alert(newPassword);
		
		var affirmNewPassowrd = document.getElementById("affirmNewPassword");
		//affirmNewPassword.focus();
		//alert(affirmNewPassowrd);
		
		if(oldPassword.value==""||newPassword.value==""||affirmNewPassowrd.value==""){
			
			alert("请先填写信息！");
			return;
		}
		
		var reg = /^[a-zA-Z0-9_$]{6,12}$/;
		
		if(!reg.test(newPassword.value)){
			
			alert("密码必须是6到12位的数字或字母或_,$！");
			return;
		}
		
		if(!reg.test(affirmNewPassowrd.value)){
			
			alert("密码必须是6到12位的数字或字母或_,$！");
			return;
		}
		
		var userForm = document.getElementsByName("userForm");
		
		userForm[0].action="password_modify.jsp";
		
		userForm[0].submit();
	}
	
	function init(){
		
		var oldPassword = document.getElementById("oldPassword");
		
		oldPassword.focus();
	}
	
	document.onkeydown=function(){
		
		if(event.keyCode==13&&event.srcElement.type!='button'){
			
			event.keyCode=9;
		}
	};

</script>
	</head>

	<body class="body1" onload="init()">
		<form name="userForm">
		    <input type="hidden" name="cmd" value="modifyPassword"/>
			<div align="center">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					height="51">
					<tr>
						<td class="p1" height="16" nowrap>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td class="p1" height="35" nowrap>
							&nbsp&nbsp
							<img src="../images/mark_arrow_02.gif" width="14" height="14">
							<b><strong>系统管理&gt;&gt;</strong>修改密码</b>
						</td>
					</tr>
				</table>
				<hr width="100%" align="center" size=0>
				<table width="50%" height="91" border="0" cellpadding="0"
					cellspacing="0">
					<tr>
						<td height="30">
							<div align="right">
								<font color="#FF0000">*</font>原密码:
							</div>
						</td>
						<td>
							<input name="oldPassword" type="password" class="text1"
								id="oldPassword" size="25">
							<font color="red"><%=msg %></font>	
						</td>
					</tr>
					<tr>
						<td height="27">
							<div align="right">
								<font color="#FF0000">*</font>新密码:
							</div>
						</td>
						<td>
							<input name="newPassword" type="password" class="text1"
								id="newPassword" size="25">
						</td>
					</tr>
					<tr>
						<td height="34">
							<div align="right">
								<font color="#FF0000">*</font>确认密码:
							</div>
						</td>
						<td>
							<input name="affirmNewPassword" type="password" class="text1"
								id="affirmNewPassword" size="25">
						</td>
					</tr>
				</table>
				<hr width="100%" align="center" size=0>
				<p>
					<input name="btnModify" class="button1" type="button"
						id="btnModify" value="修改" onClick="modifyPasword()">
					&nbsp; &nbsp;&nbsp;
				</p>
			</div>
		</form>
	</body>
</html>
