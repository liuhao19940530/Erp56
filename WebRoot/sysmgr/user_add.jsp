<%@page import="com.xunpoit.manager.UserManager"%>
<%@page import="com.xunpoit.user.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
  
   request.setCharacterEncoding("utf-8");
   response.setContentType("text/html;charset=utf-8");

   String cmd = request.getParameter("cmd");
   String msg="";
   
   if("add".equals(cmd)){
	   //获取表单填写的数据
	   String userId = request.getParameter("userId");
	   String userName = request.getParameter("userName");
	   String password = request.getParameter("password");
	   String contactTel = request.getParameter("contactTel");
	   String email = request.getParameter("email");
	
	   //得到对象，并且持久化
	   User user = new User();
	   
	   user.setUserId(userId);
	   user.setUserName(userName);
	   user.setPassword(password);
	   user.setContactTel(contactTel);
	   user.setEmail(email);

	   //调用添加数据的方法
	   UserManager manager = new UserManager();
	   
	   boolean flag = manager.addUser(user);
	   
	   if(flag){
		   
		   msg="添加成功！";
		   
	   }else{
		   msg="添加失败！";
	   }
   }
   
%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>添加用户</title>
		<link rel="stylesheet" href="../style/erp.css">
		<script src="../script/client_validate.js"></script>
		<script type="text/javascript">
	function goBack() {
		window.self.location="user_maint.jsp";
	}
	
	function addUser() {
		
		/*
		第一种方式，常用的js逻辑判断
        //表单验证，验证userId
        var userId = document.getElementById("userId");
        
        if(userId.value.length < 4 || userId.value.length > 8){
        	
        	alert("用户代码必须是4到8位之间！");
        	
        	return;
        }
        
        for(var i=0;i<userId.value.length;i++){
        	
        	if(i==0){
	        	var ch = userId.value.charAt(i);
	        	
	        	if(ch < 'a' || ch > 'z'){
	        		
	        		alert("用户代码第一位必须是小写字母的a-z!");
	        		return;
	        	}
        	}else{
        		
        		if(!(ch >= 'a'&&ch<='z')||(ch>='A'&&ch<='Z')||(ch>='0'&&ch<='9')){
        			
        			alert("用户代码除了第一位，其他位必须是数字或字母");
        			
        			return;
        		}
        	}	
        }
        
        //验证用户名
        var userName = document.getElementById("userName");
        
        if(userName.value==""){
        	
        	alert("用户名不能为空！");
        	return;
        }
        
        //验证密码
        var password = document.getElementById("password");
        
        if(password.value.length<6||password.value.length>12){
        	
        	alert("密码必须是6到12位！");
        	return;
        }
        
        //验证电话，必须是数字
 		var contactTel = document.getElementById("contactTel");
        
        for(var i=0;i<contactTel.value.length;i++){
        	
        	var ch = contactTel.value.charAt(i);
        	
        	if(!(ch>='0'&&ch<='9')){
        		
        		alert("电话必须是数字！");
        		return;
        	}
        	
        }
         
        */
        
        //第二种，正则表达式
        var userId = document.getElementById("userId");//获取用户代码
        //聚集焦点
        userId.focus();
        //表示一起是4到8位，第一位必须是小写的字母，后面的可以是大小写字母，数字
        var reg=/^[a-z]{1}[a-zA-Z0-9]{3,7}$/;
        
        if(!reg.test(userId.value)){
        	
        	alert("一起是4到8位，第一位必须是小写的字母，后面的可以是大小写字母，数字！");
        	return;
        }
        
        //验证用户名
        var userName = document.getElementById("userName");
        //聚集焦点
        userName.focus();
        if(userName.value.length==0){
        	
        	alert("用户名必填！");
        	
        	return;
        }
        
        //验证密码
        var password = document.getElementById("password");
        //聚集焦点
        password.focus();
        //密码是6到12的数字，大小写字母，下划线和美元符号
        var reg01 = /^[a-zA-Z0-9_$]{6,12}$/;
        
        if(!reg01.test(password.value)){
        	
        	alert("密码是6到12的数字，大小写字母，下划线和美元符号！");
        	return;
        }
        
        //电话 必须是数字
        var contactTel = document.getElementById("contactTel");
        //聚集焦点
        contactTel.focus();
        var reg02=/^[0-9]{1,11}$/;
        
        if(!reg02.test(contactTel.value)){
        	
        	alert("电话必须是数字！");
        	return;
        }
        
        var email = document.getElementById("email");
        
        email.focus();//聚集焦点
        
        //表单验证成功，提交表单
        var userForm = document.getElementById("userForm");
        
        userForm.action = "user_add.jsp";
        
        userForm.submit();
        
	}
	
	//第一次跳转此页面，光标的焦点获取在userId元素
	function init(){
		
		var userId = document.getElementById("userId");
		
		userId.focus();
	}
	
	//enter键拥有tab换行键的功能，但是如果碰到的元素是按钮，还是拥有自己的提交功能
	document.onkeydown=function(){//匿名函数
		//alert(event.keyCode);
		//键盘事件的event对象，keyCode获取的是此键背后的ASCLL码，enter是13，tab是9
		//srcElement是返回触发事件的元素，如果元素的type是 button，操作就失效
		if(event.keyCode==13&&event.srcElement.type!='button'){
			
			event.keyCode=9;
		}
	}
	
	//首先由请求对象：会区分浏览器  IE7以下  使用new ActiveXObject("Microsoft.XMLHTTP")
	//其他浏览器是new XMLHttpRequest()
    var xmlHttp;
	function createXMLHttpRequest(){
		
		if(window.ActiveXObject){//如果是ie7及以下版本的浏览器 
			
			 xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		
		}else if(window.XMLHttpRequest){//如果是其他较新的浏览器
			
			 xmlHttp = new XMLHttpRequest();
		}
		
	}
	
	//失去焦点就触发     Ajax：异步提交机制
	function validateUserId(userId){
		
		//alert(userId);获得当前填写的值
		
		createXMLHttpRequest();//调用函数，实例化xmlHttp请求对象
		
		//open()指定哪个界面帮助完成相应的验证
		//消除缓存
		xmlHttp.open("GET","validateUserId.jsp?id="+userId+"&time="+new Date().getTime(),true);
		
		//发送请求
		xmlHttp.send(null);
		
		//响应内容
		xmlHttp.onreadystatechange=function(){
			
			if(xmlHttp.readyState==4){//得到响应  open 0 send 1 请求 2 解析 3 响应 4
				
				if(xmlHttp.status==200){//正常打开
					
					//反应，userId回复
					//alert(xmlHttp.responseText);
				   var validateMsg = document.getElementById("validate");
				   
				   validateMsg.innerHTML = "<font color='red'>"+xmlHttp.responseText+"</font>";
				}
			}
		};
		
	}
</script>
	</head>

	<body class="body1" onload="init()">
		<form name="userForm" target="_self" id="userForm">
			<div align="center">
			    <input type="hidden" name="cmd" value="add"/>
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
							<b>系统管理&gt;&gt;用户维护&gt;&gt;添加</b>
							<font color="red"><%=msg %></font>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="22%" height="29">
							<div align="right">
								<font color="#FF0000">*</font>用户代码:&nbsp;
							</div>
						</td>
						<td width="78%">
							<input name="userId" type="text" class="text1" id="userId"
								size="10" maxlength="10" onblur="validateUserId(this.value)"/>
								<span id="validate"></span>
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
								size="20" maxlength="20">
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
									id="password" size="20" maxlength="20">
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
								id="contactTel" size="20" maxlength="20">
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
								size="20" maxlength="20">
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<div align="center">
					<input name="btnAdd" class="button1" type="button" id="btnAdd"
						value="添加" onClick="addUser()">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="btnBack" class="button1" type="button" id="btnBack"
						value="返回" onClick="goBack()" />
				</div>
			</div>
		</form>
	</body>
</html>
