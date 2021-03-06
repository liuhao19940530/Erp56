<%@page import="com.xunpoit.termi.Termi"%>
<%@page import="com.xunpoit.termi.TermiManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
  String id = request.getParameter("id");

  TermiManager manager = TermiManager.getInstance();
  
  Termi termi = manager.queryName(Integer.parseInt(id));
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>添加代理商库存数量</title>
		<link rel="stylesheet" href="../style/erp.css">

	</head>

	<body class="body1">
		<form name="aimForm" target="_self" id="aimForm">
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
							<b>基础数据管理&gt;&gt;代理商维护&gt;&gt;终端客户详细信息</b>
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
							<%=termi.getTermiClientId()==null?"":termi.getTermiClientId() %>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								终端客户名称:&nbsp;
							</div>
						</td>
						<td>
							<%=termi.getTermiName() %>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								终端客户类型:&nbsp;
							</div>
						</td>
						<td>
							<%=termi.getTermiLevel().getName()==null?"":termi.getTermiLevel().getName() %>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								联系人:&nbsp;
							</div>
						</td>
						<td>
							<%=termi.getContactMan()==null?"":termi.getContactMan() %>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								联系电话:&nbsp;
							</div>
						</td>
						<td>
							<%=termi.getContactTel()==null?"":termi.getContactTel() %>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								联系地址:&nbsp;
							</div>
						</td>
						<td>
							<%=termi.getAddress()==null?"":termi.getAddress() %>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								邮编:&nbsp;
							</div>
						</td>
						<td>
							<%=termi.getZipCode()==null?"":termi.getZipCode() %>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<div align="center">
					<input name="btnBack" class="button1" type="button" id="btnBack"
						value="返回" onClick="history.go(-1)">
				</div>
			</div>
		</form>
	</body>
</html>
