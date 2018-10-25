<%@page import="javax.swing.JOptionPane"%>
<%@page import="com.xunpoit.termi.Termi"%>
<%@page import="com.xunpoit.termi.TermiManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
   String id = request.getParameter("id");

   TermiManager manager = TermiManager.getInstance();

   Termi termiStr = manager.queryName(Integer.parseInt(id));
   
   String cmd = request.getParameter("cmd");
   
   String msg = "";
   
   if("modify".equals(cmd)){
	   
	   String nameStr = request.getParameter("name");
	   
	   Termi termi = new Termi();
	   
	   termi.setId(Integer.parseInt(id));
	   
	   termi.setTermiName(nameStr);
	   //调用TermiManager中，更新的方法
	   boolean flag = manager.modifyName(termi);
	   
	   if(flag){
		   
		   JOptionPane.showMessageDialog(null,"修改成功！");
		    
	       //response.sendRedirect("temi_client_node_crud.jsp");
	       
	   }else{
		   
		   msg = "修改失败！";
	   }
	   
   }
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="../style/erp.css" />
		<script src="../script/client_validate.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>添加区域节点</title>
        <script type="text/javascript">
           function check(){
        	   
        	   var name = document.getElementById("name");
        	   
        	   if(name.value==""){
        		   
        		   alert("当前区域名不能为空！");
        		   
        		   return;
        	   }
        	   
        	   regionForm.submit();
           }
        </script>
	</head>

	<body class="body1">
		<form id="regionForm" name="regionForm" method="post" action="temi_client_node_modify.jsp">
		    <input type="hidden" name="cmd" value="modify"/>
		    <input type="hidden" name="id" value="<%=id %>"/>
			<table width="95%" border="0" cellspacing="0" cellpadding="0"
				height="8">
				<tr>
					<td width="522" class="p1" height="2" nowrap="nowrap">
						<img src="../images/mark_arrow_03.gif" width="14" height="14" />
						&nbsp;
						<b>基础数据管理&gt;&gt;终端客户维护&gt;&gt;添加区域节点</b>
						<font color="red"><%=msg %></font>
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
						</div>
					</td>
					<td width="410">
						<label>
							<input name="name" type="text" class="text1" id="name" value="<%=termiStr==null?"":termiStr.getTermiName() %>"/>
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
					value="修改" onclick="check()"/>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="btnBack" class="button1" type="button" id="btnBack"
					value="返回" onclick="history.go(-1)" />
			</p>
		</form>
	</body>
</html>
