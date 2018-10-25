<%@page import="com.xunpoit.client.Client"%>
<%@page import="com.xunpoit.manager.ClientManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
String id = request.getParameter("id");

ClientManager manager = ClientManager.getInstance();

String msg="";
Client client = null;
if(id != null){

	client = manager.getClientOrNodeById(Integer.valueOf(id));
	
	String cmd = request.getParameter("cmd");
	
	if("modify".equals(cmd)){
		
		Client mClient = new Client();
		
		mClient.setId(Integer.parseInt(id));
		
		mClient.setName(request.getParameter("name"));
		
		boolean flag = manager.modifyName(mClient);//调用ClientManager中的修改方法
		
		if(flag){
			
			msg = "修改成功！";
			
			response.sendRedirect("client_node_crud.jsp?id="+id);
		}else{
			
			msg = "修改失败！";
		}
	}	
}
%>
<html>
	<head>
		<link rel="stylesheet" href="../style/erp.css" />
		<script src="../script/client_validate.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>修改区域节点</title>
		<script type="text/javascript">
	function modifyRegion() {
		//表单验证
		var name = document.getElementById("name");
		
		if(name.value==""){
			
			alert("区域名不能为空！");
			return;
		}
		
		regionForm.submit();//根据name的属性来提交信息
	}
	
	function goBack() {
		window.self.location = "client_node_crud.jsp?id=<%=id%>";
	}
</script>
	</head>

	<body class="body1">
		<form id="regionForm" name="regionForm" method="post" action="client_node_modify.jsp">
		    <input type="hidden" name="cmd" value="modify"/>
		    <input type="hidden" name="id" value="<%=id %>"/>
			<table width="95%" border="0" cellspacing="0" cellpadding="0"
				height="8">
				<tr>
					<td width="522" class="p1" height="2" nowrap="nowrap">
						<img src="../images/mark_arrow_03.gif" width="14" height="14" />
						&nbsp;
						<b>基础数据管理&gt;&gt;代理商维护&gt;&gt;修改区域节点</b>
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
							<font color="red"><%=msg %></font>
						</div>
					</td>
					<td width="410">
						<label>
							<input name="name" type="text" class="text1" id="name" value="<%=client==null?"":client.getName()%>"/>
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
				<input name="btnModify" class="button1" type="button" id="btnModify"
					value="修改" onClick="modifyRegion()" />
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="btnModify" class="button1" type="button" id="btnModify"
					value="返回" onclick="goBack()" />
			</p>
		</form>
	</body>
</html>
