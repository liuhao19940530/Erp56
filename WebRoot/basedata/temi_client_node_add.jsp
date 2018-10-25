<%@page import="com.xunpoit.client.TermiClientLevel"%>
<%@page import="javax.swing.JOptionPane"%>
<%@page import="com.xunpoit.termi.TermiManager"%>
<%@page import="com.xunpoit.termi.Termi"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
  String id = request.getParameter("id");

  String cmd = request.getParameter("cmd");
  
  if("add".equals(cmd)){
	  
	  Termi termi = new Termi();
	  
	  termi.setPid(Integer.parseInt(id));
	  
	  termi.setTermiName(request.getParameter("name"));
	  
	  TermiClientLevel level = new TermiClientLevel();
	  
	  termi.setTermiLevel(level);
	  
	  //根据区域的要求，来设置is_leaf和is_termi_client字段
	  termi.setIsLeaf("Y");
	  
	  termi.setIsTermiClient("N");
	  
	  TermiManager manager = TermiManager.getInstance();
	  
	  boolean flag = manager.addTermi(termi);
	  
	  if(flag){
		  
		  JOptionPane.showMessageDialog(null,"添加成功！");
	  }else{
		  
		  JOptionPane.showMessageDialog(null,"添加失败！");
	  }
  }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link rel="stylesheet" href="../style/erp.css" />
		<script src="../script/client_validate.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>添加区域节点</title>
		<script type="text/javascript">
		  function check(){
			  
			  var name = document.getElementById("name").value;
			  
			  if(name==""){
				  
				  alert("请输入您要添加的区域名称！");
				  
				  return;
			  }
			  
			  regionForm.submit();
		  }
		</script>
	</head>

	<body class="body1">
		<form id="regionForm" name="regionForm" method="post" action="temi_client_node_add.jsp">
		    <input type="hidden" name="cmd" value="add"/>
		    <input type="hidden" name="id" value="<%=id %>"/>
			<table width="95%" border="0" cellspacing="0" cellpadding="0"
				height="8">
				<tr>
					<td width="522" class="p1" height="2" nowrap="nowrap">
						<img src="../images/mark_arrow_03.gif" width="14" height="14" />
						&nbsp;
						<b>基础数据管理&gt;&gt;终端客户维护&gt;&gt;添加区域节点</b>
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
							<input name="name" type="text" class="text1" id="name" />
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
				<input name="btnAdd" class="button1" type="button" id="btnAdd"
					value="添加" onclick="check()"/>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="btnBack" class="button1" type="button" id="btnBack"
					value="返回" onclick="history.go(-1)" />
			</p>
		</form>
	</body>
</html>
