<%@page import="javax.swing.JOptionPane"%>
<%@page import="com.xunpoit.termi.Termi"%>
<%@page import="com.xunpoit.termi.TermiManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
  String id = request.getParameter("id");

  //根据id来查询当前区域的名称
  TermiManager manager = TermiManager.getInstance();
  
  Termi termi = null;
  
  try{
     termi = manager.queryName(Integer.valueOf(id));
  }catch(Exception e){
	  
	  e.printStackTrace();
  }   
  
  String cmd = request.getParameter("cmd");
  
  if("delete".equals(cmd)){
	  
	  boolean flag = manager.removeTermi(Integer.parseInt(id));
	  
	  if(flag){
		  
		  JOptionPane.showMessageDialog(null,"删除成功！");
	  }else{
		  
		  JOptionPane.showMessageDialog(null,"删除失败！");
	  }
	  
	  
  }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link rel="stylesheet" href="../style/erp.css" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>终端客户维护</title>
		<script type="text/javascript">
		   function check(){
			   
			   if(confirm("您确定要删除吗？")){
				   regionForm.action="temi_client_node_crud.jsp";
				   
				   regionForm.submit();
			   }	   
		   }
		</script>
	</head>

	<body class="body1">
		<form id="regionForm" name="regionForm" method="post">
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
							当前区域名称：
						</div>
					</td>
					<td width="410">
						<label>
							<input name="name" type="text" class="text1" id="name" size="40"
								maxlength="40" readonly="true" value="<%=termi==null?"":termi.getTermiName() %>" />
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
				<input name="btnAddRegion" type="button" class="button1"
					id="btnAddRegion" onClick="self.location='temi_client_node_add.jsp?id=<%=id %>'"
					value="添加区域" />
				&nbsp;
				<input name="btnDeleteRegion" type="button" class="button1"
					id="btnDeleteRegion" value="删除区域" onclick="check()"/>
				&nbsp;
				<input name="btnModifyRegion" type="button" class="button1"
					id="btnModifyRegion"
					onClick="self.location='temi_client_node_modify.jsp?id=<%=id %>'" value="修改区域" />
				&nbsp;
				<input name="btnAddTemiClient" type="button" class="button1"
					id="btnAddTemiClient"
					onClick="self.location='temi_client_add.jsp?id=<%=id %>'" value="添加终端客户" />
			</p>
		</form>
	</body>
</html>
