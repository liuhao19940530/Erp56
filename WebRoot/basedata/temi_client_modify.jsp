<%@page import="javax.swing.JOptionPane"%>
<%@page import="com.xunpoit.client.AbstractDataDict"%>
<%@page import="com.xunpoit.client.TermiClientLevel"%>
<%@page import="com.xunpoit.manager.DataDictManager"%>
<%@page import="com.xunpoit.termi.Termi"%>
<%@page import="com.xunpoit.termi.TermiManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
   String id = request.getParameter("id");

   //先调用根据id查询的方法
   TermiManager manager = TermiManager.getInstance();
   
   Termi termi = manager.queryTermi(Integer.parseInt(id));
   
   String cmd = request.getParameter("cmd");
   
   if("modify".equals(cmd)){
	   
	   Termi termiStr = new Termi();
	  
	   termiStr.setId(Integer.valueOf(id));
	   termiStr.setTermiClientId(request.getParameter("temiId")); 
	   termiStr.setTermiName(request.getParameter("temiName"));
	   
	   TermiClientLevel level = new TermiClientLevel();
	   
	   level.setId(request.getParameter("temiType"));
	   
	   termiStr.setTermiLevel(level);
	   
	   termiStr.setContactMan(request.getParameter("contactor"));
	   termiStr.setContactTel(request.getParameter("contactTel"));
	   termiStr.setAddress(request.getParameter("address"));
	   termiStr.setZipCode(request.getParameter("zipCode"));
	   
	   boolean flag = manager.modifyName(termiStr);
	   
	   if(flag){
		   
		   JOptionPane.showMessageDialog(null,"修改成功！");
		   
	   }else{
		   
		   JOptionPane.showMessageDialog(null,"修改失败！");
	   }
   }
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>修改终端客户</title>
		<link rel="stylesheet" href="../style/erp.css">
		<script src="../script/client_validate.js"></script>
		<script type="text/javascript">
		  function check(){
			  
			  var temiName = document.getElementById("clientId24");
			  
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
			  
			  if(contactTel.value==""){
				  alert("联系电话不能为空！");
				  return;
			  }
			  
		      var address = document.getElementById("address");
			  
			  if(address.value==""){
				  alert("联系地址不能为空！");
				  return;
			  }
			  
			  var zipCode= document.getElementById("zipCode");
			  
			  if(zipCode.value==""){
				  alert("邮编不能为空！");
				  return;
			  }
			  
			  temiClientForm.action="temi_client_modify.jsp";
			  temiClientForm.submit();
		  }
		</script>
	</head>

	<body class="body1">
		<form name="temiClientForm" target="_self" id="temiClientForm">
		    <input type="hidden" name="cmd" value="modify"/>
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
							<b>基础数据管理&gt;&gt;代理商维护&gt;&gt;修改终端客户</b>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="22%" height="29">
							<div align="right">
								终端客户代码:&nbsp;
							</div>
						</td>
						<td width="78%">
							<input name="temiId" type="text" class="text1" id="clientId4"
								size="10" maxlength="10" readonly="true" value="<%=termi.getTermiClientId()==null?"":termi.getTermiClientId()%>">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>终端客户名称:&nbsp;
							</div>
						</td>
						<td>
							<input name="temiName" type="text" class="text1" id="clientId24"
								size="10" maxlength="10" value="<%=termi.getTermiName() %>">
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
							    //调用t_data_dict表，根据category查询对应的字段信息
							      DataDictManager dictManager = DataDictManager.getInstance();
							    
							      List<AbstractDataDict> list = dictManager.findAll("D",TermiClientLevel.class.getName());//取得对应的类名
							      
							      for(Iterator<AbstractDataDict> it=list.iterator();it.hasNext();){
							    	  
							    	  AbstractDataDict dict = it.next();
				                      String selected = "";
				                      
				                      if(termi.getTermiLevel().getId().equals(dict.getId())){
				                    	  
				                    	  selected = "selected='selected'";
				                      }
							    %>
								<option value="<%=dict.getId() %>" <%=selected %>>
									<%=dict.getName() %>
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
								size="10" maxlength="10" value="<%=termi.getContactMan()==null?"":termi.getContactMan()%>">
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
								id="contactTel" size="10" maxlength="10" value="<%=termi.getContactTel()==null?"":termi.getContactTel()%>">
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
								size="10" maxlength="10" value="<%=termi.getAddress()==null?"":termi.getAddress()%>">
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
								size="10" maxlength="10" value="<%=termi.getZipCode()==null?"":termi.getZipCode()%>">
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<div align="center">
					<input name="btnModify" class="button1" type="button"
						id="btnModify" value="修改" onclick="check()">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="btnBack" class="button1" type="button" id="btnBack"
						value="返回" onclick="history.go(-1)" />
				</div>
			</div>
		</form>
	</body>
</html>
