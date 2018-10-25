<%@page import="com.xunpoit.entity.PageModel"%>
<%@page import="com.xunpoit.fiscal.FiscalYearPeriod"%>
<%@page import="com.xunpoit.fiscal.FiscalManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%

   FiscalManager manager = new FiscalManager();

   int currentPage = 1;
   
   int pageSize = 4;
   
   String currentStr = request.getParameter("currentPage");
   
   if(currentStr != null){
	   
	   currentPage = Integer.valueOf(currentStr);
   }

   PageModel<FiscalYearPeriod> pageModel = manager.queryPeriod(currentPage,pageSize);
  
   List<FiscalYearPeriod> list = pageModel.getDataList();
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>会计核算期间维护</title>
		<link rel="stylesheet" href="../style/erp.css">
		<script type="text/javascript">
		   function check(){
			   
			   var selectFlag = document.getElementsByName("selectFlag");
			   
			   var count = 0;
			   
			   var id = 0;
			   
			   for(var i=0;i<selectFlag.length;i++){
				   
				   if(selectFlag[i].checked){
					   
					   count++;
					   
					   id = i;
				   }
			   }
			   
			   if(count < 1){
				   
				   alert("您没有选中要修改的数据行！");
				   return;
			   }
			   
			   window.self.location="fiscal_year_period_modify.jsp?id="+selectFlag[id].value;
			
		   }
		   
		   function topPage(){
			   
			   window.self.location="fiscal_year_period_maint.jsp?currentPage="+<%=pageModel.getTopPage()%>;
		   }
		   
 		   function previousPage(){
			   
			   window.self.location="fiscal_year_period_maint.jsp?currentPage="+<%=pageModel.getPreviousPage()%>;
		   }
 
 		   function nextPage(){
	   
	   		   window.self.location="fiscal_year_period_maint.jsp?currentPage="+<%=pageModel.getNextPage()%>;
 		   }
 
 		   function bottomPage(){
	   
	  		   window.self.location="fiscal_year_period_maint.jsp?currentPage="+<%=pageModel.getBottomPage()%>;
 		   }
		</script>
	</head>

	<body class="body1">
		<form name="fiscalYearPeriodForm">
			<div align="center">
				<table width="95%" border="0" cellspacing="0" cellpadding="0"
					height="35">
					<tr>
						<td class="p1" height="18" nowrap>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td width="522" class="p1" height="17" nowrap>
							<img src="../images/mark_arrow_02.gif" width="14" height="14">
							&nbsp;
							<b>基础数据管理&gt;&gt;会计核算期间维护</b>
						</td>
					</tr>
				</table>
				<hr width="100%" align="center" size=0>
			</div>
			<table width="95%" height="20" border="0" align="center"
				cellspacing="0" class="rd1" id="toolbar">
				<tr>
					<td width="49%" class="rd19">
						<font color="#FFFFFF">查询列表</font>
					</td>
					<td width="27%" nowrap class="rd16">
						<div align="right"></div>
					</td>
				</tr>
			</table>
			<table width="95%" border="1" cellspacing="0" cellpadding="0"
				align="center" class="table1">
				<tr>
					<td width="79" class="rd6">
						选择
					</td>
					<td width="123" class="rd6">
						核算年
					</td>
					<td width="146" class="rd6">
						核算月
					</td>
					<td width="188" class="rd6">
						开始日期
					</td>
					<td width="204" class="rd6">
						结束日期
					</td>
					<td width="172" class="rd6">
						核算期状态
					</td>
				</tr>
				<%
				if(list != null){
					
				  for(Iterator<FiscalYearPeriod> it = list.iterator();it.hasNext();){
					  
					  FiscalYearPeriod fiscal = it.next();
					  
					
				%>
				<tr>
					<td class="rd8">
						<input type="radio" name="selectFlag" value="<%=fiscal.getId()%>">
					</td>
					<td class="rd8">
						<%=fiscal.getFiscalYear()%>
					</td>
					<td class="rd8">
						<%=fiscal.getFiscalPeriod() %>
					<td class="rd8">
						<%=fiscal.getBeginDate()==null?"":fiscal.getBeginDate()%>
					</td>
					<td class="rd8">
						<%=fiscal.getEndDate()==null?"":fiscal.getEndDate() %>
					</td>
					<td class="rd8">
						<%=fiscal.getPeriodSts()==null?"":fiscal.getPeriodSts()%>
					</td>
				</tr>
			    <%
			     }
				}  
			    %>
			</table>
			<table width="95%" height="30" border="0" align="center"
				cellpadding="0" cellspacing="0" class="rd1">
				<tr>
					<td nowrap class="rd19" height="2">
						<div align="left">
							<font color="#FFFFFF">&nbsp;共<%=pageModel.getTotalPage() %>页</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<font color="#FFFFFF">当前第</font>&nbsp
							<font color="#FF0000"><%=pageModel.getCurrentPage() %></font>&nbsp
							<font color="#FFFFFF">页</font>
						</div>
					</td>
					<td nowrap class="rd19">
						<div align="right">
							<input name="btnTopPage" class="button1" type="button"
								id="btnTopPage" value="|&lt;&lt; " title="首页" onclick="topPage()">
							<input name="btnPreviousPage" class="button1" type="button"
								id="btnPreviousPage" value=" &lt;  " title="上页" onclick="previousPage()">
							<input name="btnNextPage" class="button1" type="button"
								id="btnNextPage" value="  &gt; " title="下页" onclick="nextPage()">
							<input name="btnBottomPage" class="button1" type="button"
								id="btnBottomPage" value=" &gt;&gt;|" title="尾页" onclick="bottomPage()">
							<input name="btnAdd" type="button" class="button1" id="btnAdd"
								value="添加" onClick="self.location='fiscal_year_period_add.jsp'">
							<input name="btnModify" class="button1" type="button"
								id="btnModify" value="修改"
								onClick="check()">
						</div>
					</td>
				</tr>
			</table>
			<p>
				&nbsp;
			</p>
		</form>
	</body>
</html>
