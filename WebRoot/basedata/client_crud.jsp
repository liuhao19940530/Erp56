<%@page import="com.xunpoit.client.Client"%>
<%@page import="com.xunpoit.manager.ClientManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
String id = request.getParameter("id");

ClientManager manager = ClientManager.getInstance();
Client client = null;

if(id != null){

	 try{
		 
     client = manager.getClientOrNodeById(Integer.valueOf(id));
     
	 }catch(Exception e){
		 
		 e.printStackTrace();
	 }  
   
}  

//调用删除的方法
String cmd = request.getParameter("cmd");

String msg = "";

if("delete".equals(cmd)){
	  //调用删除代理商的方法
	  boolean flag = manager.removeClientOrNodeById(Integer.parseInt(id));
	  
	  if(flag){
		  
		  msg = "删除成功！";
		  
	  }else{
		  
		  msg = "删除失败！";
	  }
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link rel="stylesheet" href="../style/erp.css" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>代理商维护</title>
		<script type="text/javascript">
		   function deleteRegion(){
			   
			   if(confirm("您确定要删除吗？")){
				   
				   clientForm.submit();
			   }
		   }
		</script>
	</head>

	<body class="body1">
		<form id="clientForm" name="clientForm" method="post" action="client_crud.jsp">
		    <input type="hidden" name="cmd" value="delete"/>
		    <input type="hidden" name="id" value="<%= id%>"/>
			<table width="95%" border="0" cellspacing="0" cellpadding="0"
				height="8">
				<tr>
					<td width="522" class="p1" height="2" nowrap="nowrap">
						<img src="../images/mark_arrow_02.gif" width="14" height="14" />
						&nbsp;
						<b>基础数据管理&gt;&gt;代理商维护</b>
						<font color="red"><%=msg %></font>
					</td>
				</tr>
			</table>
			<hr width="97%" align="center" size="0" />
			<p>
			<p>
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="213">
						<div align="right">
							当前代理商名称：
						</div>
					</td>
					<td width="410">
						<label>
							<input name="clientName" type="text" class="text1" size="40"
								id="clientName" readonly="true" value="<%=client==null?"":client.getName()%>"/>
						</label>
					</td>
				</tr>
			</table>
			<p>
				<label>
					<br />
				</label>
			<hr />
			<p align="center">
				<input name="btnModifyClient" type="button" class="button1"
					id="btnModifyClient" onClick="self.location='client_modify.jsp?id=<%=id %>'"
					value="修改代理商" />
				&nbsp;
				<input name="btinDeleteClient" type="button" class="button1"
					id="btinDeleteClient" value="删除代理商" onclick="deleteRegion()"/>
				&nbsp;
				<input name="btnViewDetail" type="button" class="button1"
					id="btnViewDetail" onClick="self.location='client_detail.jsp?id=<%=id %>'"
					value="查看详细信息" />
			</p>
		</form>
	</body>
</html>
