<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>添加物料</title>
		<link rel="stylesheet" href="../style/erp.css">
		<script src="../script/client_validate.js"></script>
		<script type="text/javascript">
		   function check(){
			   
			   var itemNo = document.getElementById("itemNo");
			   
			   var itemName = document.getElementById("itemName");
			   
			   if(itemNo.value==""||itemName.value==""){
				   
				   alert("输入的物料代码或物料名称不能为空！");
				   
				   return;
			   }
			   
			   itemForm.action = "../servlet/ItemAddUser";
			   
			   itemForm.submit();
		   }
		   
		   document.onkeydown=function(){
			   
			   if(event.keyCode==13&&event.srcElement.type!='button'){
				   
				   event.keyCode=9;
			   }
		   };
		   
		   //Ajax异步提交部分
		   var xmlHttp;
		   
		   function createXmlHttp(){
			   
			   if(window.ActiveXObject){
				   
				   xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
				   
			   }else if(window.XMLHttpRequest){
				   
				   xmlHttp = new XMLHttpRequest();
			   }
		   }
		   
		   function validate(itemNo){
			   
			   createXmlHttp();//实例化xmlHttp
			   
			   xmlHttp.open("GET","../servlet/AddServlet?id="+itemNo+"&time="+new Date().getTime(),true);
			   
			   xmlHttp.send(null);
			   
			   xmlHttp.onreadystatechange=function(){
				   
				 if(xmlHttp.readyState==4){
					 
					 if(xmlHttp.status==200){
						 
						 var validateStr = document.getElementById("validateStr");
						 
						 validateStr.innerHTML = "<font color='red'>"+ xmlHttp.responseText +"</font>";
					 }
				 }
				   
			   };
		   }
		   
		   function init(){
			   
			   var itemNo = document.getElementById("itemNo");
			   
			   itemNo.focus();
		   }
		</script>
	</head>

	<body class="body1" onload="init()">
		<form name="itemForm" target="_self" id="itemForm" method="post">
			<div align="center">
				<table width="95%" border="0" cellspacing="2" cellpadding="2">
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
				</table>
				<table width="95%" border="0" cellspacing="0" cellpadding="0"
					height="25">
					<tr>
						<td width="522" class="p1" height="25" nowrap>
							<img src="../images/mark_arrow_03.gif" width="14" height="14">
							&nbsp;
							<b>基础数据管理&gt;&gt;物料维护&gt;&gt;添加</b><br>
							<font color="red">${message}</font>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="22%" height="29">
							<div align="right">
								<font color="#FF0000">*</font>物料代码:&nbsp;
							</div>
						</td>
						<td width="78%">
							<input name="itemNo" type="text" class="text1" id="itemNo"
								size="10" maxlength="10" onblur="validate(this.value)">
							<span id="validateStr"></span>	
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>物料名称:&nbsp;
							</div>
						</td>
						<td>
							<input name="itemName" type="text" class="text1" id="itemName"
								size="20" maxlength="20">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								物料规格:&nbsp;
							</div>
						</td>
						<td>
							<label>
								<input name="spec" type="text" class="text1" id="spec"
									size="20" maxlength="20">
							</label>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								物料型号:&nbsp;
							</div>
						</td>
						<td>
							<input name="pattern" type="text" class="text1" id="pattern"
								size="20" maxlength="20">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>类别:&nbsp;
							</div>
						</td>
						<td>
							<select name="category" class="select1" id="category">
							  <c:forEach items="${requestScope.categoryList}" var="category">
								<option value="${category.id}" selected="selected">
									${category.name }
							    </option>
							  </c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>计量单位:&nbsp;
							</div>
						</td>
						<td>
							<select name="unit" class="select1" id="unit">
							  <c:forEach items="${requestScope.unitList }" var="unit">
								<option value="${unit.id }">
									${unit.name}
								</option>
							</c:forEach>
							</select>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<div align="center">
					<input name="btnAdd" class="button1" type="button" id="btnAdd"
						value="添加" onclick="check()">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="btnBack" class="button1" type="button" id="btnBack"
						value="返回" onClick="history.go(-1)">
				</div>
			</div>
		</form>
	</body>
</html>
