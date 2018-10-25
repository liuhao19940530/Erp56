<%@page import="com.xunpoit.fiscal.FiscalManager"%>
<%@page import="com.xunpoit.fiscal.FiscalYearPeriod"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
  String cmd = request.getParameter("cmd");

  String msg = "";
  
  if("add".equals(cmd)){
	  
	  FiscalYearPeriod fiscal = new FiscalYearPeriod();
	  
	  fiscal.setFiscalYear(Integer.parseInt(request.getParameter("fiscalYear")));
	  
	  fiscal.setFiscalPeriod(Integer.parseInt(request.getParameter("fiscalPeriod")));
	  
	  fiscal.setBeginDate(request.getParameter("beginDate"));
	  
	  fiscal.setEndDate(request.getParameter("endDate"));
	  
	  fiscal.setPeriodSts(request.getParameter("situation"));
	  
	  //调用添加的方法
	  FiscalManager manager = new FiscalManager();
	  
	  boolean flag = manager.addPeriod(fiscal);
	  
	  if(flag){
		  
		  msg = "添加成功！";
		  
		  response.sendRedirect("fiscal_year_period_maint.jsp");
		  
	  }else{
		  
		  msg = "添加失败！";
	  }
	  
  }
		  
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>添加会计核算期间</title>
		<link rel="stylesheet" href="../style/erp.css">
		<link href="../style/JSCalendar.css" rel=stylesheet type=text/css>
		<script src="../script/JSCalendar.js"></script>
		<script src="../script/client_validate.js"></script>
		<script type="text/javascript">
		   function check(){
			   
			   var fiscalYear = document.getElementById("fiscalYear");
			   
			   if(fiscalYear.value==""){
				   
				   alert("核算年不能为空！");
				   return;
			   }
			   
 			   var fiscalPeriod = document.getElementById("fiscalPeriod");
			   
			   if(fiscalPeriod.value==""){
				   
				   alert("核算月不能为空！");
				   return;
			   }
			   
			   var periodSts = document.getElementById("periodSts");
			   
			   if(periodSts.checked){
				   
				   fiscalYearPeriodForm.action="fiscal_year_period_add.jsp?situation=Y";
				   fiscalYearPeriodForm.submit();
			   }else{
				   
				   fiscalYearPeriodForm.action="fiscal_year_period_add.jsp?situation=N";
				   fiscalYearPeriodForm.submit();
			   }
			
		   }
		</script>
	</head>

	<body class="body1">
		<form name="fiscalYearPeriodForm" target="_self"
			id="fiscalYearPeriodForm" method="post">
			<input type="hidden" name="cmd" value="add"/>
			<div align="center">
				<table width="95%" border="0" cellspacing="2" cellpadding="2">
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
							<b>基础数据管理&gt;&gt;会计核算期间维护&gt;&gt;添加</b>
							<font color="red"><%=msg %></font>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="22%" height="29">
							<div align="right">
								<font color="#FF0000">*</font>核算年:&nbsp;
							</div>
						</td>
						<td width="78%">
							<input name="fiscalYear" type="text" class="text1"
								id="fiscalYear" size="10" maxlength="10">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>核算月:&nbsp;
							</div>
						</td>
						<td>
							<input name="fiscalPeriod" type="text" class="text1"
								id="fiscalPeriod" size="10" maxlength="10">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>开始日期:&nbsp;
							</div>
						</td>
						<td>
							<label>
								<input type="text" name="beginDate" size="10" maxlength="10"
									value="2001-01-01" readonly="true" onClick=JSCalendar(this)>
							</label>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>结束日期:&nbsp;
							</div>
						</td>
						<td>
							<input name="endDate" type="text" id="endDate"
								onClick=JSCalendar(this) value="2001-01-01" size="10"
								maxlength="10" readonly="true">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>是否可用:&nbsp;
							</div>
						</td>
						<td>
							<input name="periodSts" type="checkbox" class="checkbox1"
								id="periodSts" value="checkbox">
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<div align="center">
					<input name="btnAdd" class="button1" type="button" id="btnAdd"
						value="添加" onclick="check()">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="btnBack" class="button1" type="button" id="btnBack"
						value="返回" onClick="history.go(-1)">
				</div>
			</div>
		</form>
	</body>
</html>
