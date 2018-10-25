<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>物料维护</title>
		<link rel="stylesheet" href="../style/erp.css">
		<script src="../script/windows.js"></script>
		<script type="text/javascript">
	function addItem() {
		window.self.location = "<%=basePath%>/servlet/ItemAddServlet";
	}
	
	function modifyItem() {
		
		var selectFlag = document.getElementsByName("selectFlag");
		
		var count = 0;
		
		var j = 0;
		
		for(var i=0;i<selectFlag.length;i++){
			
			if(selectFlag[i].checked){
				
				count++;
				
				j = i;
			}
		}
		
		if(count!=1){
           
           alert("只能选择一个物料来修改！");
           
           return;
		}
		
		window.self.location = "<%=basePath%>servlet/ItemModifyUIServlet?itemNo="+selectFlag[j].value;
	}
	
	function deleteItem() {
		
		var selectFlag = document.getElementsByName("selectFlag");
		
		var count = 0;
		
		for(var i=0;i<selectFlag.length;i++){
			
			if(selectFlag[i].checked){
				
				count++;

			}
		}
		
		if(count<1){
           
           alert("必须选中一个或多个物料来删除！");
           
           return;
		}
		
		if(confirm("您确定要删除吗？")){
			
			itemForm.action = "<%=basePath%>servlet/ItemDeleteServlet";
			
			itemForm.submit();
		}		
	}
	
	function topPage() {
		//这里传递参数的时候，需要将itemNoOrName也传递过去，这样模糊查询才会起到作用
	   window.self.location = "<%=basePath%>servlet/ItemFindAllServlet?currentPage=${pageModel.topPage}&clientIdOrName=${itemNoOrName}";
	}
	
	function previousPage() {
	
		 window.self.location = "<%=basePath%>servlet/ItemFindAllServlet?currentPage=${pageModel.previousPage}&clientIdOrName=${itemNoOrName}";
	}
	
	function nextPage() {
	
		 window.self.location = "<%=basePath%>servlet/ItemFindAllServlet?currentPage=${pageModel.nextPage}&clientIdOrName=${itemNoOrName}";
	}
	
	function bottomPage() {
	
		 window.self.location = "<%=basePath%>servlet/ItemFindAllServlet?currentPage=${pageModel.bottomPage}&clientIdOrName=${itemNoOrName}";
	}
	
	function checkAll() {
	
		var ifAll = document.getElementsByName("ifAll");
		
		var selectFlag = document.getElementsByName("selectFlag");
		
		var i = 0;
		
		while(i<selectFlag.length){
			
		    selectFlag[i].checked = ifAll[0].checked;
			
			i++;
		}
	}
	
	function uploadPic4Item() {
		
		var selectFlag = document.getElementsByName("selectFlag");
		
		var count = 0;
		
		var j = 0;
		
		for(var i=0;i<selectFlag.length;i++){
			
			if(selectFlag[i].checked){
				
				count++;
				
				j=i;
			}
		}
		
		if(count!=1){
			
			alert("请选择一个物料来上传图片！");
			return;
		}
		
		window.self.location = "<%=basePath%>servlet/ItemUploadUIServlet?itemNo="+selectFlag[j].value;
	}
</script>
	</head>

	<body class="body1">
		<form name="itemForm" action="<%=basePath%>servlet/ItemFindAllServlet" method="post">
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
							<img src="../images/mark_arrow_02.gif" alt="我" width="14"
								height="14">
							&nbsp;
							<b>基础数据管理&gt;&gt;物料维护</b>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="17%" height="29">
							<div align="left">
								物料代码/名称:
							</div>
						</td>
						<td width="57%">
							<input name="clientIdOrName" type="text" class="text1"
								id="clientId4" size="50" maxlength="50" value="${itemNoOrName}">
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
						<input type="checkbox" name="ifAll" onClick="checkAll()">
					</td>
					<td width="155" class="rd6">
						物料代码
					</td>
					<td width="155" class="rd6">
						物料名称
					</td>
					<td width="155" class="rd6">
						物料规格
					</td>
					<td width="155" class="rd6">
						物料型号
					</td>
					<td width="138" class="rd6">
						类别
					</td>
					<td width="101" class="rd6">
						计量单位
					</td>
				</tr>
                
                <c:forEach items="${pageModel.dataList }" var="item">
                  <tr> 
					<td class="rd8">
						<input type="checkbox" name="selectFlag" class="checkbox1" value="${item.itemNo }">
					</td>
					<td class="rd8">
						<a href="#"
							onClick="window.open('<%=basePath%>servlet/ItemDetailServlet?itemNo=${item.itemNo}', '物料详细信息', 'width=400, height=400, scrollbars=no')">${item.itemNo}</a>
					</td>
					<td class="rd8">
						${item.itemName}
					</td>
					<td class="rd8">
						${item.spec}
					</td>
					<td class="rd8">
						${item.pattern}
					</td>
					<td class="rd8">
						${item.itemCategoryId.name}
					</td>
					<td class="rd8">
						${item.itemUnitId.name}
					</td>
				</tr>
				
			  </c:forEach>	
			</table>
			<table width="95%" height="30" border="0" align="center"
				cellpadding="0" cellspacing="0" class="rd1">
				<tr>
					<td nowrap class="rd19" height="2" width="36%">
						<div align="left">
							<font color="#FFFFFF">&nbsp;共${pageModel.totalPage}页</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<font color="#FFFFFF">当前第</font>&nbsp
							<font color="#FF0000">${pageModel.currentPage}</font>&nbsp
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
							<input name="btnAdd" type="button" class="button1" id="btnAdd"
								value="添加" onClick="addItem()">
							<input name="btnDelete" class="button1" type="button"
								id="btnDelete" value="删除" onClick="deleteItem()">
							<input name="btnModify" class="button1" type="button"
								id="btnModify" value="修改" onClick="modifyItem()">
							<input name="btnUpload" class="button1" type="button"
								id="btnUpload" value="上传图片" onClick="uploadPic4Item()">
						</div>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
