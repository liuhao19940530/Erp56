<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>修改物料</title>
		<link rel="stylesheet" href="../style/erp.css">
		<script src="../script/client_validate.js"></script>
		<script type="text/javascript">
		   function check(){
			   
			   var itemName = document.getElementById("itemName");
			   
			   if(itemName.value==""){
				   
				   alert("物料名称不能为空！");
				   
				   return false;
			   }
			   
			   itemForm.action="<%=basePath%>servlet/ItemModifyServlet";
			   
			   return true;
			   
		   }
		</script>
	</head>

	<body class="body1">
		<form name="itemForm" target="_self" id="itemForm" onsubmit="return check()">
			<div align="center">
				<table width="95%" height="21" border="0" cellpadding="2"
					cellspacing="2">
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
				</table>
				<table width="95%" border="0" cellspacing="0" cellpadding="0"
					height="25">
					<tr>
						<td width="522" class="p1" height="25" nowrap>
							<img src="../images/mark_arrow_03.gif" width="14" height="14">
							&nbsp;
							<b>基础数据管理&gt;&gt;物料维护&gt;&gt;修改</b>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="22%" height="29">
							<div align="right">
								物料代码:&nbsp;
							</div>
						</td>
						<td width="78%">
							<input name="itemNo" type="text" class="text1" id="itemNo"
								size="10" maxlength="10" readonly="true" value="${item.itemNo}">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>物料名称:&nbsp;
							</div>
						</td>
						<td>
							<input name="itemName" type="text" class="text1" id="itemName"
								size="20" maxlength="20" value="${item.itemName}">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								物料规格:&nbsp;
							</div>
						</td>
						<td>
							<label>
								<input name="spec" type="text" class="text1" id="spec"
									size="20" maxlength="20" value="${item.spec}">
							</label>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								物料型号:&nbsp;
							</div>
						</td>
						<td>
							<input name="pattern" type="text" class="text1" id="pattern"
								size="20" maxlength="20" value="${item.pattern}">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>类别:&nbsp;
							</div>
						</td>
						<td>
							<select name="category" class="select1" id="category">
						      <c:forEach items="${categoryList}" var="category">
						       <c:if test="${item.itemCategoryId.id==category.id}">
								<option value="${category.id}" selected>
								   ${category.name}
								</option>
							   </c:if>
							   <c:if test="${item.itemCategoryId.id!=category.id}">
								<option value="${category.id}">
								   ${category.name}
								</option>
							   </c:if>
							  </c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>计量单位:&nbsp;
							</div>
						</td>
						<td>
							<select name="unit" class="select1" id="unit">
						      <c:forEach items="${unitList }" var="unit">
						       <c:if test="${item.itemUnitId.id==unit.id}">
						        <c:set var="selected" value="selected"/>
								<option value="${unit.id }" selected="${selected}">
									${unit.name }
								</option>
							   </c:if>	
							   <c:if test="${item.itemUnitId.id!=unit.id}">
								<option value="${unit.id }">
									${unit.name }
								</option>
							   </c:if>	
							   
							  </c:forEach>
							</select>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<div align="center">
					<input name="btnModify" class="button1" type="submit"
						id="btnModify" value="修改">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="btnModify" class="button1" type="button"
						id="btnModify" value="返回" onClick="history.go(-1)">
				</div>
			</div>
		</form>
	</body>
</html>
