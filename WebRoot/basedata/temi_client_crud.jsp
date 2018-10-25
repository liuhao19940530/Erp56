<%@page import="javax.swing.JOptionPane"%>
<%@page import="com.xunpoit.termi.Termi"%>
<%@page import="com.xunpoit.termi.TermiManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
  String id = request.getParameter("id");

  TermiManager manager = TermiManager.getInstance();
  
  Termi termi = manager.queryName(Integer.parseInt(id));
  
  String cmd = request.getParameter("cmd");
  
  if("delete".equals(cmd)){
	  
	  boolean flag = manager.removeTermi(Integer.valueOf(id));
	  
	  if(flag){
		  
		  JOptionPane.showMessageDialog(null,"删除成功！");
	  }else{
		  
		  JOptionPane.showMessageDialog(null,"删除失败！");
	  }
  }
%>
<html>
	<head>
		<link rel="stylesheet" href="../style/erp.css" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>终端客户维护</title>
		<script type="text/javascript">
		  function check(){
			  
			  if(confirm("您确定要删除吗？")){
			     temiClientForm.submit();
			  }
		  }
		</script>
	</head>

	<body class="body1">
		<form id="temiClientForm" name="temiClientForm" method="post"
			action="temi_client_crud.jsp">
			<input type="hidden" name="cmd" value="delete"/>
			<input type="hidden" name="id" value="<%=id %>"/>
			<table width="95%" border="0" cellspacing="0" cellpadding="0"
				height="8">
				<tr>
					<td width="522" class="p1" height="2" nowrap="nowrap">
						<img src="../images/mark_arrow_02.gif" width="14" height="14" />
						&nbsp;
						<b>基础数据管理&gt;&gt;终端客户维护</b>
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
							当前终端客户名称：
						</div>
					</td>
					<td width="410">
						<label>
							<input name="temiaName" type="text" class="text1" id="temiaName"
								readonly="true" value="<%=termi==null?"":termi.getTermiName()%>"/>
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
				<input name="btnModifyTemiClient" type="button" class="button1"
					id="btnModifyTemiClient"
					onClick="self.location='temi_client_modify.jsp?id=<%= id%>'" value="修改终端客户" />
				&nbsp;
				<input name="btnDeleteTemiClient" type="button" class="button1"
					id="btnDeleteTemiClient" value="删除终端客户" onclick="check()"/>
				&nbsp;
				<input name="btnDetailInfo" type="button" class="button1"
					id="btnDetailInfo"
					onClick="self.location='temi_client_detail.jsp?id=<%=id %>'" value="查看详细信息" />
			</p>
		</form>
	</body>
</html>
