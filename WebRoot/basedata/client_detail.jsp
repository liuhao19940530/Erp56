<%@page import="com.xunpoit.client.Client"%>

<%@page import="com.xunpoit.manager.ClientManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
  String id = request.getParameter("id");

  ClientManager manager = ClientManager.getInstance();
  
  Client client = null;
  
  try{
     client = manager.getClientOrNodeById(Integer.parseInt(id));
  }catch(Exception e){
	  
	  e.printStackTrace();
  }
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>代理商详细信息</title>
		<link rel="stylesheet" href="../style/erp.css">
	</head>

	<body class="body1">
		<form name="clientForm" target="_self" id="clientForm">
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
							<b>基础数据管理&gt;&gt;代理商维护&gt;&gt;代理商详细信息</b>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" height="267" border="0" cellpadding="0"
					cellspacing="0">
					<tr>
						<td width="22%">
							<div align="right">
								代理商代码:&nbsp;
							</div>
						</td>
						<td width="78%">
							<%=client.getClientId()==null?"":client.getClientId() %>
						</td>
					</tr>
					<tr>
						<td>
							<div align="right">
								代理商名称:&nbsp;
							</div>
						</td>
						<td>
							<%=client.getName() %>
						</td>
					</tr>
					<tr>
						<td>
							<div align="right">
								代理商类型:&nbsp;
							</div>
						</td>
						<td>
							<%=client.getClientLevelId().getName()==null?"":client.getClientLevelId().getName() %>
						</td>
					</tr>
					<tr>
						<td>
							<div align="right">
								银行帐号:&nbsp;
							</div>
						</td>
						<td>
							<%=client.getBankAcctNo()==null?"":client.getBankAcctNo() %>
						</td>
					</tr>
					<tr>
						<td>
							<div align="right">
								联系电话:&nbsp;
							</div>
						</td>
						<td>
							<%=client.getContactTel()==null?"":client.getContactTel() %>
						</td>
					</tr>
					<tr>
						<td>
							<div align="right">
								地址:&nbsp;
							</div>
						</td>
						<td>
							<%=client.getAddress()==null?"":client.getAddress() %>
						</td>
					</tr>
					<tr>
						<td>
							<div align="right">
								邮编:&nbsp;
							</div>
						</td>
						<td>
							<%=client.getZipCode()==null?"":client.getZipCode() %>
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
