
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.xunpoit.fiscal.FiscalYearPeriod"%>
<%@page import="com.xunpoit.fiscal.FiscalManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
  String id = request.getParameter("id");
 
  FiscalManager manager = new FiscalManager();
  
  FiscalYearPeriod fiscal =  manager.queryById(Integer.valueOf(id));
  
  String cmd = request.getParameter("cmd");
  String msg = "";
  
  if("modify".equals(cmd)){
	  
	  FiscalYearPeriod fiscalPeriod = new FiscalYearPeriod();
	  
	  fiscalPeriod.setId(Integer.parseInt(id));
	  
	  fiscalPeriod.setFiscalYear(Integer.valueOf(request.getParameter("fiscalYear")));
	  
	  fiscalPeriod.setFiscalPeriod(Integer.valueOf(request.getParameter("fiscalPeriod")));
	  
	  fiscalPeriod.setBeginDate(request.getParameter("beginDate"));
	  
	  fiscalPeriod.setEndDate(request.getParameter("endDate"));
	
	  String ch = request.getParameter("use");
	  
	  if("Y".equals(ch)){
		  
		  fiscalPeriod.setPeriodSts("Y");
		  
	  }else{
		  
		  fiscalPeriod.setPeriodSts("N");
	  }
	  
	  boolean flag = manager.modifyById(fiscalPeriod);
	  
	  if(flag){
		  
		  msg = "修改成功！";
	  }else{
		  
		  msg = "修改失败！";
	  }
  }
  
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>修改会计核算期间</title>
		<link rel="stylesheet" href="../style/erp.css">
		<link href="../style/JSCalendar.css" rel=stylesheet type=text/css>
		<script src="../script/JSCalendar.js"></script>
		<script src="../script/client_validate.js"></script>
		<script type="text/javascript">
		   function check(){
			   
			   var periodSts = document.getElementById("periodSts");

			   if(periodSts.checked){
			       //alert(1);
				   fiscalYearPeriodForm.action ="fiscal_year_period_modify.jsp?use=Y";
				   fiscalYearPeriodForm.submit();
			   }else{
				  // alert(0);
				   fiscalYearPeriodForm.action ="fiscal_year_period_modify.jsp?use=N";
				   fiscalYearPeriodForm.submit();
			   }	   
		   }	   
		</script>
	</head>

	<body class="body1">
		<form name="fiscalYearPeriodForm" target="_self"
			id="fiscalYearPeriodForm" method="post">
			<input type="hidden" name="cmd" value="modify"/>
			<input type="hidden" name="id" value="<%=id %>"/>
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
							<b>基础数据管理&gt;&gt;会计核算期间维护&gt;&gt;修改</b>
							<font color="red"><%=msg %></font>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="22%" height="29">
							<div align="right">
								核算年:&nbsp;
							</div>
						</td>
						<td width="78%">
							<input name="fiscalYear" type="text" class="text1" value="<%=fiscal.getFiscalYear() %>"
								id="fiscalYear" size="10" maxlength="10" readonly="true">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								核算月:&nbsp;
							</div>
						</td>
						<td>
							<input name="fiscalPeriod" type="text" class="text1" value="<%=fiscal.getFiscalPeriod() %>"
								id="fiscalPeriod" size="10" maxlength="10" readonly="true">
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
									value="<%=fiscal.getBeginDate()==null?"":fiscal.getBeginDate()%>" readonly="true" onClick=JSCalendar(this)>
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
								onClick=JSCalendar(this) value="<%=fiscal.getEndDate()==null?"":fiscal.getEndDate()%>" size="10"
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
						    <%
						       String checked="";
						       if("Y".equals(fiscal.getPeriodSts())){
						    	   
						    	   checked="checked='checked'";
						       }
						    %>
							<input name="periodSts" type="checkbox" class="checkbox1" <%=checked %>
								id="periodSts" value="<%=fiscal.getPeriodSts()%>">
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<div align="center">
					<input name="btnModify" class="button1" type="button"
						id="btnModify" value="修改" onclick="check()">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="btnBack" class="button1" type="button" id="btnBack"
						value="返回" onClick="history.go(-1)">
				</div>
			</div>
		</form>
	</body>
</html>
