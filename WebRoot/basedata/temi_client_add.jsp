<%@page import="javax.swing.JOptionPane"%>
<%@page import="com.xunpoit.termi.TermiManager"%>
<%@page import="com.xunpoit.client.TermiClientLevel"%>
<%@page import="com.xunpoit.client.AbstractDataDict"%>
<%@page import="com.xunpoit.manager.DataDictManager"%>
<%@page import="com.xunpoit.termi.Termi"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
  String id = request.getParameter("id");

  String cmd = request.getParameter("cmd");
  
  if("add".equals(cmd)){
	  
	  Termi termi = new Termi();
	  
	  termi.setPid(Integer.valueOf(id));
	  
	  termi.setTermiClientId(request.getParameter("temiId"));
	  
	  termi.setTermiName(request.getParameter("temiName"));
	  
	  TermiClientLevel level = new TermiClientLevel();
	  
	  level.setId(request.getParameter("temiType"));
	  
	  termi.setTermiLevel(level);
	  
	  termi.setContactMan(request.getParameter("contactor"));
	  
	  termi.setContactTel(request.getParameter("contactTel"));
	  
	  termi.setAddress(request.getParameter("address"));
	  
	  termi.setZipCode(request.getParameter("zipCode"));
	  
	  termi.setIsLeaf("Y");
	  
	  termi.setIsTermiClient("Y");
	  
	  //调用TermiManager中的addTermi()方法
	  TermiManager manager = TermiManager. getInstance();
	  
	  boolean flag = manager.addTermi(termi);
	  
	  if(flag){
		  
		  JOptionPane.showMessageDialog(null,"添加成功！");
		  
	  }else{
		  
		  JOptionPane.showMessageDialog(null,"添加失败！");
	  }
  }
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>添加终端客户</title>
		<link rel="stylesheet" href="../style/erp.css">
		<script src="../script/client_validate.js"></script>
		<script type="text/javascript">
		   function check(){
			   
			   var temiId = document.getElementById("temiId");
			   
			   if(temiId.value==""){
				   
				   alert("终端客户代码不能为空！");
				   return;
			   }
			   
			   var temiName = document.getElementById("temiName");
			   
			   if(temiName.value==""){
				   
				   alert("终端客户名称不能为空！");
				   return;
			   }
			   
			   var contactor = document.getElementById("contactor");
			   
			   if(contactor.value==""){
				   
				   alert("联系人不能为空！");
				   return;
			   }
			   
			   var contactTel = document.getElementById("contactTel");
			   
			   var reg = new RegExp(/^[0-9]{7,11}$/);
			   
			   if(!reg.test(contactTel.value)){
				   
				   alert("联系电话必须是7到11位的数字！");
				   return;
			   }
			   
 			   var address = document.getElementById("address");
			   
			   if(address.value==""){
				   
				   alert("联系地址不能为空！");
				   return;
			   }
			   
 			   var zipCode = document.getElementById("zipCode");
			   
			   if(zipCode.value==""){
				   
				   alert("邮编不能为空！");
				   return;
			   }
		   
			   temiClientForm.action = "temi_client_add.jsp";
			   temiClientForm.submit();
			   
		   }
		   
		   document.onkeydown = function(){
			   
			   if(event.keyCode==13&&event.srcElement.type!='button'){
				   
				   event.keyCode = 9;
			   }
		   };
		   
		   var xmlHttp;
		   
		   function createXMLHttp(){
			   
			   if(window.ActiveXObject){
				   
				   xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
				   
			   }else if(window.XMLHttpRequest){
				   
				   xmlHttp = new XMLHttpRequest();
			   }
		   }
		   
		   function validateTermiId(termiId){
			   
			  //实例化xmlHttp
			  createXMLHttp();
			  
			  xmlHttp.open("GET","../servlet/validate?id="+termiId+"&time="+new Date().getTime(),true);
			  
			  xmlHttp.send(null);
			  
			  xmlHttp.onreadystatechange = function(){
				  
				  if(xmlHttp.readyState == 4){
					  
					  if(xmlHttp.status == 200){
						  
						  //alert(xmlHttp.responseText);
						  var validateStr = document.getElementById("validateStr");
						  
						  validateStr.innerHTML = "<font color='red'>"+xmlHttp.responseText+"</font>";
					  }
				  }
			  };
			  
   }
		</script>
	</head>

	<body class="body1">
		<form name="temiClientForm" target="_self" id="temiClientForm">
		    <input type="hidden" name="cmd" value="add"/>
		    <input type="hidden" name="id" value="<%=id %>"/>
			<div align="center">
				<table width="95%" border="0" cellspacing="2" cellpadding="2">
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
				</table>
				<table width="95%" border="0" cellspacing="0" cellpadding="0"
					height="8">
					<tr>
						<td width="522" class="p1" height="2" nowrap>
							<img src="../images/mark_arrow_03.gif" width="14" height="14">
							&nbsp;
							<b>基础数据管理&gt;&gt;代理商维护&gt;&gt;添加终端客户</b>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="22%" height="29">
							<div align="right">
								<font color="#FF0000">*</font>终端客户代码:&nbsp;
							</div>
						</td>
						<td width="78%">
							<input name="temiId" type="text" class="text1" id="temiId"
								size="20" maxlength="20" onblur="validateTermiId(this.value)">
							<span id="validateStr"></span>	
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>终端客户名称:&nbsp;
							</div>
						</td>
						<td>
							<input name="temiName" type="text" class="text1" id="temiName"
								size="20" maxlength="20">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>终端客户类型:&nbsp;
							</div>
						</td>
						<td>
							<select name="temiType" class="select1" id="temiType">
								<%
								  DataDictManager dictManager = DataDictManager.getInstance();
								  
								  List<AbstractDataDict> dict = dictManager.findAll("D",TermiClientLevel.class.getName());
								
								  for(Iterator<AbstractDataDict> it = dict.iterator();it.hasNext();){
									  
									  AbstractDataDict data = it.next();
								
								%>
								<option value="<%=data.getId() %>">
									<%=data.getName() %>
								</option>
				                <%
				                }
				                %>
							</select>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								联系人:&nbsp;
							</div>
						</td>
						<td>
							<input name="contactor" type="text" class="text1" id="contactor"
								size="20" maxlength="20">
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
								联系地址:&nbsp;
							</div>
						</td>
						<td>
							<input name="address" type="text" class="text1" id="address"
								size="20" maxlength="20">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								邮编:&nbsp;
							</div>
						</td>
						<td>
							<input name="zipCode" type="text" class="text1" id="zipCode"
								size="20" maxlength="20">
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
