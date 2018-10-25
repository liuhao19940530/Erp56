<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>物料维护</title>
		<link rel="stylesheet" href="../style/erp.css">
		<script src="../script/client_validate.js"></script>
		<script language="javascript"></script>
		<script type="text/javascript">
		   function check(){
			   
			   var fileName = document.getElementsByName("fileName");
			   
			   if(fileName[0].value==""){
				   
				   alert("上传图片不能为空！");
				   return false;
			   }
			   
			   itemForm.action="<%=basePath %>servlet/ItemUploadServlet";
			   
			   return true;
		   }
		</script>
	</head>
  <!-- 
      如果表单中包含了file表单元素，即涉及到图片或文件上传
      必须   ①method方式是post
       ②form的enctype属性值必须为multipart/form-data
   -->
	<body class="body1">
		<form name="itemForm" target="_self" id="itemForm" onsubmit="return check()" method="post" enctype="multipart/form-data">
		   <input type="hidden" name="itemNo" value="${item.itemNo }"/>
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
							<b>基础数据管理&gt;&gt;物料维护&gt;&gt;上传物料图片</b>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td height="29">
							<div align="right">
								物料代码:&nbsp;
							</div>
						</td>
						<td>
							${item.itemNo }
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								物料名称:&nbsp;
							</div>
						</td>
						<td>
							${item.itemName }
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								物料规格:&nbsp;
							</div>
						</td>
						<td>
						    ${item.spec }
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								物料型号:&nbsp;
							</div>
						</td>
						<td>
							${item.pattern }
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								类别:&nbsp;
							</div>
						</td>
						<td>
							${item.itemCategoryId.name }
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								计量单位:&nbsp;
							</div>
						</td>
						<td>
							${item.itemUnitId.name }
						</td>
					</tr>
					<tr>
						<td height="74">
							<div align="right">
								图片:&nbsp;
							</div>
						</td>
						<td>
							<img src="<%=basePath%>upload/${item.fileName}" width="85" height="49">
						</td>
					</tr>
					<tr>
						<td width="22%" height="29">
							<div align="right">
								<font color="#FF0000">*</font>选择图片:&nbsp;
							</div>
						</td>
						<td width="78%">
							<input name="fileName" type="file" class="text1" size="40" maxlength="40">
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<div align="center">
					<input name="btn_upload" class="button1" type="submit"
						id="btn_upload" value="上传">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="btnBack" class="button1" type="button" id="btnBack"
						value="返回" onClick="history.go(-1)">
				</div>
			</div>
		</form>
	</body>
</html>
