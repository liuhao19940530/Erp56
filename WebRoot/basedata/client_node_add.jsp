<%@page import="com.xunpoit.manager.ClientManager"%>
<%@page import="com.xunpoit.client.Client"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
  String id = request.getParameter("id");//首先获取的是url带过来的id，后面提交了表单，也可以获取隐藏域中的id
  
  String cmd = request.getParameter("cmd");
  
  String msg = "";
  
  if("add".equals(cmd)){
	  
	  //调用添加的方法
	  ClientManager manager = ClientManager.getInstance();
	  
	  Client client = new Client();
	  
	  client.setPid(Integer.parseInt(id));
	  String name = request.getParameter("name");
	  client.setName(name);
	  
	//is_leaf和is_client是非空字段，此处先设置
	  client.setIsLeaf("Y");
		
	  client.setIsClient("N");
	  //调用添加区域的方法
	  boolean flag = manager.addClientOrNode(client);
	  
	  if(flag){
		  
		  msg = "添加成功！";
		  
		  response.sendRedirect("client_node_crud.jsp?id="+id);//跳转到上级页面
	  }else{
		  
		  msg = "添加失败！";
	  }
  }
  
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link rel="stylesheet" href="../style/erp.css" />
		<script src="../script/client_validate.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>添加区域节点</title>
		<script type="text/javascript">
			
		    function check(){
		    	
		    	var name = document.getElementById("name").value;
		    	if(name==""){
		    		
		    		alert("区域名不能为空！");
		    		return;
		    	}
		    	
		    	regionForm.submit();
		    	
		    }
        </script>
	</head>

	<body class="body1">
		<form name="regionForm" method="post" action="client_node_add.jsp">
		    <input type="hidden" name="id" value="<%=id%>"/>
		    <input type="hidden" name="cmd" value="add"/>
			<table width="95%" border="0" cellspacing="0" cellpadding="0"
				height="8">
				<tr>
					<td width="522" class="p1" height="2" nowrap="nowrap">
						<img src="../images/mark_arrow_03.gif" width="14" height="14" />
						&nbsp;
						<b>基础数据管理&gt;&gt;代理商维护&gt;&gt;添加区域节点</b>
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
							<font color="#FF0000">*</font>区域名称：
						</div>
					</td>
					<td width="410">
						<label>
							<input name="name" type="text" class="text1" id="name" />
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
				<input name="btnAdd" class="button1" type="button" value="添加" onclick="check()"/>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="btnBack" class="button1" type="button" value="返回"
					onclick="history.go(-1)" />
			</p>
		</form>
	</body>
</html>
