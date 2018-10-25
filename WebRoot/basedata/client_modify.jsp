<%@page import="com.xunpoit.client.AbstractDataDict"%>
<%@page import="com.xunpoit.client.ClientLevel"%>
<%@page import="com.xunpoit.manager.DataDictManager"%>
<%@page import="com.xunpoit.client.Client"%>
<%@page import="com.xunpoit.manager.ClientManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<% 
String id = request.getParameter("id");
String msg = "";

ClientManager manager = ClientManager.getInstance();
Client client = null;

//完成修改
String cmd = request.getParameter("cmd");
	
if("modify".equals(cmd)){
		
		Client mClient = new Client();
		
		mClient.setId(Integer.parseInt(id));
	
		mClient.setName(request.getParameter("clientName"));
		
		ClientLevel level = new ClientLevel();
		level.setId(request.getParameter("clientLevel"));
		mClient.setClientLevelId(level);
		
		mClient.setBankAcctNo(request.getParameter("bankAcctNo"));
		mClient.setContactTel(request.getParameter("contactTel"));
		mClient.setAddress(request.getParameter("address"));
		mClient.setZipCode(request.getParameter("zipCode"));
		
		boolean flag = manager.modifyName(mClient);//调用ClientManager中的修改方法
		
		if(flag){
			
			msg = "修改成功！";
			
			response.sendRedirect("client_crud.jsp?id="+id);
		}else{
			
			msg = "修改失败！";
		}
}	

if(id != null){

	 try{
		 
     client = manager.getClientOrNodeById(Integer.valueOf(id));
     
	 }catch(Exception e){
		 
		 e.printStackTrace();
	 }  
   
}  

DataDictManager dataManager =  DataDictManager.getInstance();

List<AbstractDataDict> dataList = dataManager.findAll("C",ClientLevel.class.getName());//不容易出错

%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>修改代理商</title>
		<link rel="stylesheet" href="../style/erp.css">
		<script src="../script/client_validate.js"></script>
		<script type="text/javascript">
           function check(){
        	   
        	   var clientName = document.getElementById("clientName");
        	   
        	   if(clientName.value==""){
        		   
        		   alert("代理商名称不能为空!");
        		   return;
        	   }
        	   
        	   var bankAcctNo = document.getElementById("bankAcctNo").value;
        	   
        	   var reg = /^[0-9]{15,20}$/;
        	   
        	   if(!reg.test(bankAcctNo)){
        		   
        		   alert("银行账户必须是15到20位的数字！");
        		   return;
        	   }
        	   
        	   var contactTel = document.getElementById("contactTel").value;
        	   
        	   var reg01 = /^[0-9]{6,11}$/;
        	   
			   if(!reg01.test(contactTel)){
        		   
        		   alert("电话必须是6到11位的数字！");
        		   return;
        	   }
        	   
 			   var address = document.getElementById("address");
   	   
			   if(address.value==""){
        		   
        		   alert("地址不能为空！");
        		   return;
        	   }
			   
			   var zipCode = document.getElementById("zipCode");
		   	   
			   if(zipCode.value==""){
        		   
        		   alert("邮编不能为空！");
        		   return;
        	   }
			   
        	   clientForm.submit();
           }
        </script>
	</head>

	<body class="body1">
		<form name="clientForm" target="_self" id="clientForm" action="client_modify.jsp">
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
							<b>基础数据管理&gt;&gt;代理商维护&gt;&gt;修改代理商</b>
							<font color="red"><%=msg %></font>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="22%" height="29">
							<div align="right">
								代理商代码:&nbsp;
							</div>
						</td>
						<td width="78%">
							<input name="clientId" type="text" class="text1" id="clientId"
								size="10" maxlength="10" readonly="true" value="<%=client.getClientId()%>">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>代理商名称:&nbsp;
							</div>
						</td>
						<td>
							<input name="clientName" type="text" class="text1"
								id="clientName" size="40" maxlength="40" value="<%=client.getName()%>">
						</td>
					</tr>
					<tr>
						<td height="15">
							<div align="right">
								<font color="#FF0000">*</font>代理商类型:&nbsp;
							</div>
						</td>
						<td>
							<select name="clientLevel" class="select1" id="clientLevel">
							<%
							  
							   for(Iterator<AbstractDataDict> it=dataList.iterator();it.hasNext();){
								  
								   AbstractDataDict data = it.next();
								   String selected = "";
							       if((client.getClientLevelId().getId()).equals(data.getId())){
								   
								      selected = "selected='selected'";
							       }
							      
							%>
								<option value="<%=data.getId()%>" <%=selected %>>
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
								银行帐号:&nbsp;
							</div>
						</td>
						<td>
							<input name="bankAcctNo" type="text" class="text1"
								id="bankAcctNo" size="20" maxlength="20" value="<%=client.getBankAcctNo()==null?"":client.getBankAcctNo()%>">
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
								id="contactTel" size="20" maxlength="20" value="<%=client.getContactTel()==null?"":client.getContactTel()%>">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								地址:&nbsp;
							</div>
						</td>
						<td>
							<input name="address" type="text" class="text1" id="address"
								size="20" maxlength="20" value="<%=client.getAddress()==null?"":client.getAddress()%>">
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
								size="10" maxlength="10" value="<%=client.getZipCode()==null?"":client.getZipCode()%>">
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<div align="center">
					<input name="btnModify" class="button1" type="button" onclick="check()"
						id="btnModify" value="修改">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="btnBack" class="button1" type="button" id="btnBack"
						value="返回" onclick="history.go(-1)" />
				</div>
			</div>
		</form>
	</body>
</html>
