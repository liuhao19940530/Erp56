<%@page import="com.xunpoit.user.User"%>
<%@page import="com.xunpoit.manager.UserManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
   request.setCharacterEncoding("utf-8");
   response.setContentType("text/html;charset=utf-8");
   
   //获取传递的userId，带进数据库中查询，然后返回一个user对象
   String userId=request.getParameter("userId");
   UserManager manager = new UserManager();
   User user = manager.findUserId(userId);
   
   //查询到的user对象的信息，经过修改后，再次保存到数据库中
   String msg="";
   String cmd=request.getParameter("cmd");//此时的cmd可以区分，是经过user_add.jsp加进数据库的，还是经过user_modify.jsp加载的
   
   if("modify".equals(cmd)){
		
  %>
    <!-- 
           利用javabean的自省机制，可以减少代码量，省去了request.getParameter()获取表单的值的步骤
     -->
	 <jsp:useBean id="users" class="com.xunpoit.user.User"></jsp:useBean>
	 <jsp:setProperty property="*" name="users"></jsp:setProperty>
  <% 	 
     boolean flag = manager.modifyUser(users);
  
     if(flag){
    	 
    	 msg="修改成功！";
    	 
    	 response.sendRedirect("user_maint.jsp");
    	 
     }else{
    	 
    	 msg="修改失败！";
    	 
     }
   }
    
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>修改用户</title>
		<link rel="stylesheet" href="../style/erp.css">
		<script src="../script/client_validate.js"></script>
		<script type="text/javascript">
	function goBack() {
		window.self.location ="user_maint.jsp"
	}
	
	function modifyUser() {
		
		var userName = document.getElementById("userName");
		
		userName.focus();
		
		if(userName.value==""){
			
			alert("用户名不能为空！");
			return;
		}
		
		var password = document.getElementById("password");
		
		password.focus();
		
		var reg=/^[\w]{6,12}$/;
		
		if(!reg.test(password.value)){
			
			alert("6到12位的任意字符！");
			return;
		}
		
	    var contactTel=document.getElementById("contactTel");
	    
	    contactTel.focus();
	    
	    for(var i=0;i<contactTel.value.length;i++){
	    	
	       var ch=contactTel.value.charAt(i);
	       
	       if(!(ch>='0'&&ch<='9')){
	    	   
	    	   alert("电话必须是数字！");
	    	   return;
	       }
	    }
	    
	    var email=document.getElementById("email");
	    
	    email.focus();
		
		var userForm = document.getElementById("userForm");
		
		userForm.action="user_modify.jsp?cmd=modify";
		
		userForm.submit();
	
    }
	
	function init(){
		
		var userName=document.getElementById("userName");
		
		userName.focus();
	}
	
	document.onkeydown=function(){
		
		if(event.keyCode==13&&event.srcElement.type!='button'){
			
			event.keyCode=9;
		}
	};
	
</script>

	</head>

	<body class="body1" onload="init()">
		<form name="userForm" id="userForm" method="post">
		    
			<div align="center">
				<table width="95%" border="0" cellspacing="2" cellpadding="2">
					<tr>
						<td>&nbsp;
							
						</td>
					</tr>
				</table>
				<table width="95%" border="0" cellspacing="0" cellpadding="0"
					height="25">
					<tr>
						<td width="522" class="p1" height="25" nowrap>
							<img src="../images/mark_arrow_03.gif" width="14" height="14">
							&nbsp;
							<b>系统管理&gt;&gt;用户维护&gt;&gt;修改</b>
							<font color="red"><%=msg %></font>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="22%" height="29">
							<div align="right">
								用户代码:&nbsp;
							</div>
						</td>
						<td width="78%">
							<input name="userId" type="text" class="text1" id="userId"
								size="10" maxlength="10" readonly="true" value="<%=user.getUserId()%>">
						
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>用户名称:&nbsp;
							</div>
						</td>
						<td>
							<input name="userName" type="text" class="text1" id="userName"
								size="20" maxlength="20" value="<%=user.getUserName() %>">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>密码:&nbsp;
							</div>
						</td>
						<td>
							<label>
								<input name="password" type="password" class="text1"
									id="password" size="20" maxlength="20" value="<%=user.getPassword() %>">
							</label>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								联系电话:&nbsp;
							</div>
						</td>
						<td>
							<input name="contactTel" type="text" class="text1"
								id="contactTel" size="20" maxlength="20" value="<%=user.getContactTel()==null?"":user.getContactTel()%>"/>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								email:&nbsp;
							</div>
						</td>
						<td>
							<input name="email" type="text" class="text1" id="email"
								size="20" maxlength="20" value="<%=user.getEmail()==null?"":user.getEmail() %>"/>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<div align="center">
					<input name="btnModify" class="button1" type="button"
						id="btnModify" value="修改" onClick="modifyUser()">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="btnBack" class="button1" type="button" id="btnBack"
						value="返回" onClick="goBack()" />
				</div>
			</div>
		</form>
	</body>
</html>
