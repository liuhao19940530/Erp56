<%@page import="com.xunpoit.flowcard.FlowCardDetail"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>流向单明细信息</title>
		<link rel="stylesheet" href="../style/erp.css">

	</head>

	<body class="body1">
		<div align="center">
			<form name="flowCardForm" id="flowCardForm">
				<table width="95%" border="0" cellspacing="2" cellpadding="2">
					<tr>
						<td>&nbsp;
							
					  </td>
					</tr>
				</table>
				<table width="95%" border="0" cellspacing="0" cellpadding="0"
					height="8">
					<tr>
						<td width="522" class="p1" height="2" nowrap>
							<img src="../images/mark_arrow_03.gif" width="14" height="14">
							&nbsp;
							<b>流向单详细信息</b>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="8%" nowrap="nowrap">
							<div align="right">
								&nbsp;&nbsp;&nbsp;流向单号:&nbsp;
							</div>
						</td>
						<td width="7%" nowrap="nowrap">
							${master.flowCardId}
						</td>
						<td width="13%" nowrap="nowrap">
							<div align="right">
								&nbsp;&nbsp;&nbsp;供方代理商代码:&nbsp;
							</div>
						</td>
						<td width="6%">
							${master.clientId.clientId}
						</td>
						<td width="15%" nowrap="nowrap">
							<div align="right">
								<div align="right">
									&nbsp;&nbsp;&nbsp;供方代理商名称:&nbsp;
								</div>
							</div>
						</td>
						<td width="18%" nowrap="nowrap">
							${master.clientId.name}
						</td>
						<td width="10%" nowrap="nowrap">
							&nbsp;&nbsp;&nbsp;录入日期:&nbsp;						</td>
						<td width="23%" nowrap="nowrap">
							${master.optDate}
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="1" cellspacing="0" cellpadding="0"
					align="center" class="table1">
					<tr>
						<td width="89" class="rd6">
							需方客户代码
						</td>
						<td width="116" class="rd6">
							需方客户名称
						</td>
						<td width="74" class="rd6">
							物料代码
						</td>
						<td width="136" class="rd6">
							物料名称
						</td>
						<td width="120" class="rd6">
							规格
						</td>
						<td width="103" class="rd6">
							型号
						</td>
						<td width="68" class="rd6">
							计量单位
						</td>
						<td width="69" class="rd6">
							操作数量
						</td>
					</tr>
				    <%
				       List<FlowCardDetail> list = (List<FlowCardDetail>)request.getAttribute("list");
				    
				       for(FlowCardDetail detail:list){
				    %>
					  <tr>
						<td class="rd8">
							<%=detail.getAimClientId().getClientTermiId() %>
						</td>
						<td class="rd8">
							<%=detail.getAimClientId().getName() %>
						</td>
						<td class="rd8">
							<%=detail.getItemNo().getItemNo() %>
						</td>
						<td class="rd8">
							<%=detail.getItemNo().getItemName() %>
						</td>
						<td class="rd8">
							<%=detail.getItemNo().getSpec()%>
						</td>
						<td class="rd8">
							<%=detail.getItemNo().getPattern() %>
						</td>
						<td class="rd8">
							<%=detail.getItemNo().getItemUnitId().getName() %>
						</td>
						<td class="rd8">
							<%=detail.getOptQty() %>
						</td>
					  </tr>
					 <%
				      }
					 %>
				</table>
				<p>
					<input name="btnClose" type="button" id="btnClose"
						onClick="window.close()" value="关闭">
				</p>
			</form>
		</div>
	</body>
</html>
