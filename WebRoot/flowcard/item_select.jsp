<%@page import="com.xunpoit.item.Item"%>
<%@page import="com.xunpoit.entity.PageModel"%>
<%@page import="com.xunpoit.manager.ItemManagerImpl"%>
<%@page import="com.xunpoit.itemmanager.ItemManager"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	String itemNoOrName = request.getParameter("itemNoOrName");
	
	String index = request.getParameter("index");
			
	session.setAttribute("index",index);
	
	int pageSize = 3;
	
	int currentPage = 1;
	
	String currentStr = request.getParameter("currentPage");
	
	if(currentStr!=null&&!"".equals(currentStr)){
		
		currentPage = Integer.valueOf(currentStr);
	}
	
	ItemManager manager = new ItemManagerImpl();
	
	PageModel<Item> pm = manager.queryItems(pageSize, currentPage, itemNoOrName);
	
	session.setAttribute("pm",pm);
	
	session.setAttribute("itemNoOrName",itemNoOrName);
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>请选择物料</title>
		<link rel="stylesheet" href="<%=basePath%>style/erp.css">
		<script src="<%=basePath%>script/client_validate.js"></script>
		<script type="text/javascript">
	function topPage() {
		window.self.location="<%=basePath%>flowcard/item_select.jsp?currentPage=${pm.topPage}&itemNoOrName=${itemNoOrName}&index=${index}";
	}
	
	function previousPage() {
		window.self.location="<%=basePath%>flowcard/item_select.jsp?currentPage=${pm.previousPage}&itemNoOrName=${itemNoOrName}&index=${index}";
	}
	
	function nextPage() {
		window.self.location="<%=basePath%>flowcard/item_select.jsp?currentPage=${pm.nextPage}&itemNoOrName=${itemNoOrName}&index=${index}";
	}
	
	function bottomPage() {

		window.self.location="<%=basePath%>flowcard/item_select.jsp?currentPage=${pm.bottomPage}&itemNoOrName=${itemNoOrName}&index=${index}";
	}
	
	function queryClient() {
	}
	
	function selectOk(s1,s2,s3,s4,s5) {
		
		if(window.opener.document.all.itemNo.length>1){
			
			window.opener.document.all.itemNo[<%=index%>].value=s1;
			
			window.opener.document.all.itemName[<%=index%>].value=s2;
			
			window.opener.document.all.spec[<%=index%>].value=s3;
			
			window.opener.document.all.pattern[<%=index%>].value=s4;
			
			window.opener.document.all.unit[<%=index%>].value=s5;
			
		}else{
			
			window.opener.document.all.itemNo.value=s1;
			
			window.opener.document.all.itemName.value=s2;
			
			window.opener.document.all.spec.value=s3;
			
			window.opener.document.all.pattern.value=s4;
			
			window.opener.document.all.unit.value=s5;
		}
		
		window.close();
	}
	
	function selectOks(){
		
		alert("请双击选择按钮，来完成确定选择的操作！");
	}
	
</script>
	</head>

	<body class="body1">
		<form name="selectItemForm" method="post" action="<%=basePath%>flowcard/item_select.jsp?index=${index}">
			<div align="center">
				<table width="95%" border="0" cellspacing="0" cellpadding="0"
					height="34">
					<tr>
						<td width="522" class="p1" height="34" nowrap>
							<img src="<%=basePath%>images/search.gif" width="32" height="32">
							&nbsp;
							<b>请选择物料</b>
						</td>
					</tr>
				</table>
				<hr width="100%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="17%" height="29">
							<div align="left">
								物料代码/名称:
							</div>
						</td>
						<td width="57%">
							<input name="itemNoOrName" type="text" class="text1"
								id="itemNoOrName" size="50" maxlength="50" value="${itemNoOrName }">
						</td>
						<td width="26%">
							<div align="left">
								<input name="btnQuery" type="submit" class="button1"
									id="btnQuery" value="查询">
							</div>
						</td>
					</tr>
					<tr>
						<td height="16">
							<div align="right"></div>
						</td>
						<td>
							&nbsp;
						</td>
						<td>
							<div align="right"></div>
						</td>
					</tr>
				</table>

			</div>
			<table width="95%" border="0" cellspacing="0" cellpadding="0"
				class="rd1" align="center">
				<tr>
					<td nowrap height="10" class="p2">
						物料信息
					</td>
					<td nowrap height="10" class="p3">
						&nbsp;
					</td>
				</tr>
			</table>
			<table width="95%" border="1" cellspacing="0" cellpadding="0"
				align="center" class="table1">
				<tr>
					<td width="35" class="rd6">
						选择
					</td>
					<td width="170" class="rd6">
						物料代码
					</td>
					<td width="222" class="rd6">
						物料名称
					</td>
					<td width="195" class="rd6">
						物料规格
					</td>
					<td width="293" class="rd6">
						物料型号
					</td>
					<td width="293" class="rd6">
						类别
					</td>
					<td width="293" class="rd6">
						计量单位
					</td>
				</tr>
			  <c:forEach items="${pm.dataList }" var="item">	
				<tr>
					<td width="35" class="rd8">
						<input type="radio" name="selectFlag" value="${item.itemNo }"
							onDblClick="selectOk('${item.itemNo}','${item.itemName }','${item.spec }','${item.pattern }','${item.itemUnitId.name}')">
					</td>
					<td width="170" class="rd8">
						${item.itemNo }
					</td>
					<td width="222" class="rd8">
						${item.itemName }
					</td>
					<td width="195" class="rd8">
						${item.spec }
					</td>
					<td width="293" class="rd8">
						${item.pattern }
					</td>
					<td width="293" class="rd8">
						${item.itemCategoryId.name }
					</td>
					<td width="293" class="rd8">
						${item.itemUnitId.name }
					</td>
				</tr>
			  </c:forEach>	
			</table>
			<table width="95%" height="30" border="0" align="center"
				cellpadding="0" cellspacing="0" class="rd1">
				<tr>
					<td nowrap class="rd19" height="2" width="36%">
						<div align="left">
							<font color="#FFFFFF">&nbsp;共${pm.totalPage }页</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<font color="#FFFFFF">当前第</font>&nbsp
							<font color="#FF0000">${pm.currentPage }</font>&nbsp
							<font color="#FFFFFF">页</font>
						</div>
					</td>
					<td nowrap class="rd19" width="64%">
						<div align="right">
							<input name="btnTopPage" class="button1" type="button"
								id="btnTopPage" value="|&lt;&lt; " title="首页"
								onClick="topPage()">
							<input name="btnPreviousPage" class="button1" type="button"
								id="btnPreviousPage" value=" &lt;  " title="上页"
								onClick="previousPage()">
							<input name="btnNextPage" class="button1" type="button"
								id="btnNextPage" value="  &gt; " title="下页" onClick="nextPage()">
							<input name="btnBottomPage" class="button1" type="button"
								id="btnBottomPage" value=" &gt;&gt;|" title="尾页"
								onClick="bottomPage()">
							<input name="btnOk" class="button1" type="button" id="btnOk"
								value="确定" onClick="selectOks()">
							<input name="btnClose" class="button1" type="button"
								id="btnClose" value="关闭" onClick="window.close()">
						</div>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
