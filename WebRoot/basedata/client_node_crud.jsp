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
	  //调用删除区域的方法
	  boolean flag = manager.removeClientOrNodeById(Integer.parseInt(id));
	  
	  if(flag){
		  
		  msg = "删除成功！";
		  
	  }else{
		  
		  msg = "删除失败！";
	  }
  }
%>
<html>
	<head>
		<link rel="stylesheet" href="../style/erp.css" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>代理商维护</title>
		<script type="text/javascript">

	function addRegion() {
		window.self.location = "client_node_add.jsp?id=<%=id%>";	
	}
	
	function modifyRegion() {
		var name = document.getElementById("name");
		
		if(name.value==""){
			;
			alert("区域名不能为空！");
			return;
		}
		
		window.self.location = "client_node_modify.jsp?id=<%= id%>";
	}
	
	function deleteRegion() {
		
		if(confirm("您确定要删除吗？")){
	  	
			clientForm.submit();
		}		
	}
	
	function addClient() {
		window.self.location = "client_add.jsp?id=<%= id%>";
	}
	
</script>
	</head>

	<body class="body1">
		<form id="clientForm" name="clientForm" method="post" action="client_node_crud.jsp">
	        <input type="hidden" name="cmd" value="delete"/>
	        <input type="hidden" name="id" value="<%=id%>"/>
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
			<p></p>
			<p></p>
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="213">
						<div align="right">
							当前区域名称：
						</div>
					</td>
					<td width="410">
						<label>
							<input name="name" type="text" class="text1" id="name"
								readonly="true" value="<%=client==null?"":client.getName() %>"/>
						</label>
					</td>
				</tr>
			</table>
			<p></p>
			<label>
				<br />
			</label>
			<hr />
			<p align="center">
				<input name="btnAddRegion" type="button" class="button1"
					id="btnAddRegion" onClick="addRegion()" value="添加区域" />
				&nbsp;
				<input name="btnDeleteRegion" type="button" class="button1"
					id="btnDeleteRegion" value="删除区域" onClick="deleteRegion()" />
				&nbsp;
				<input name="btnModifyRegion" type="button" class="button1"
					id="btnModifyRegion" onClick="modifyRegion()" value="修改区域" />
				&nbsp;
				<input name="btnAddClient" type="button" class="button1"
					id="btnAddClient" onClick="addClient()" value="添加代理商" />
			</p>
		</form>
	</body>
</html>
