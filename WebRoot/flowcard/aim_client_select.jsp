<%@page import="com.xunpoit.entity.AimClient"%>
<%@page import="java.util.List"%>

<%@page import="com.xunpoit.entity.PageModel"%>
<%@page import="com.xunpoit.manager.ClientManager"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%
   String path = request.getContextPath();
   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
   
   String aimClientIdOrName = request.getParameter("aimClientIdOrName");
   
   String index = request.getParameter("index");
   
   session.setAttribute("index",index);
   
   session.setAttribute("aimClientIdOrName", aimClientIdOrName);   
   
   int pageSize = 3;
   
   int currentPage = 1;
   
   String num = application.getInitParameter("pageSize");
   
   if(num!=null&&!"".equals(num)){
	   
	   pageSize = Integer.valueOf(num);
   }
   
   String currentStr = request.getParameter("currentPage");
   
   if(currentStr!=null&&!"".equals(currentStr)){
	   
	   currentPage = Integer.parseInt(currentStr);
   }
   
   ClientManager manager = ClientManager.getInstance();
   
   PageModel<AimClient> pm = manager.findAllAimClient(pageSize, currentPage, aimClientIdOrName); 
   
   List<AimClient> list = pm.getDataList();
   
   //获取的是需方的哪一个被选中
   String[] aid = request.getParameterValues("selectFlag");
   
   if(aid!=null){
	   
	   for(int i=0;i<aid.length;i++){
		   AimClient aims = manager.getAimClientById(Integer.parseInt(aid[0]));
		   
		   session.setAttribute("aims", aims);
	   }	   
   }
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>请选择需方客户</title>
		<link rel="stylesheet" href="<%=basePath%>style/erp.css">
		<script src="<%=basePath%>script/client_validate.js"></script>
		<script type="text/javascript">
	function topPage() {
		window.self.location = "<%=basePath%>flowcard/aim_client_select.jsp?currentPage=<%=pm.getTopPage()%>&aimClientIdOrName=${aimClientIdOrName}&index=${index}";
	}
	
	function previousPage() {
		window.self.location = "<%=basePath%>flowcard/aim_client_select.jsp?currentPage=<%=pm.getPreviousPage()%>&aimClientIdOrName=${aimClientIdOrName}&index=${index}";
	}
	
	function nextPage() {
		
		window.self.location = "<%=basePath%>flowcard/aim_client_select.jsp?currentPage=<%=pm.getNextPage()%>&aimClientIdOrName=${aimClientIdOrName}&index=${index}";
	}
	
	function bottomPage() {

		window.self.location = "<%=basePath%>flowcard/aim_client_select.jsp?currentPage=<%=pm.getBottomPage()%>&aimClientIdOrName=${aimClientIdOrName}&index=${index}";
	}
	
	function queryClient() {
	}
	
	function selectOk(s1,s2,s3) {
		//会加入多行，所以这里要判断一下，只有当length大于1时，才用到索引Index
		if(window.opener.document.all.aid.length>1){
			
		   window.opener.document.all.aid[<%=index%>].value=s1;
		   
		   window.opener.document.all.aimId[<%=index%>].value=s2;
		   
		   window.opener.document.all.aimName[<%=index%>].value=s3;
		   
		}else{
			
			window.opener.document.all.aid.value=s1;
			
			window.opener.document.all.aimId.value=s2;
			
			window.opener.document.all.aimName.value=s3;
		}
		
		window.close();
	}

</script>

	</head>

	<body class="body1">
		<form name="aimClientForm" method="post" action="<%=basePath%>flowcard/aim_client_select.jsp?index=${index}">
			<div align="center">
				<table width="95%" border="0" cellspacing="0" cellpadding="0"
					height="34">
					<tr>
						<td width="522" class="p1" height="34" nowrap>
							<img src="<%=basePath%>images/search.gif" width="32" height="32">
							&nbsp;
							<b>请选择需方客户</b>
						</td>
					</tr>
				</table>
				<hr width="100%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="17%" height="29">
							<div align="left">
								需方客户代码/名称:
							</div>
						</td>
						<td width="57%">
							<input name="aimClientIdOrName" type="text" class="text1"
								id="aimClientIdOrName" size="50" maxlength="50" value="${aimClientIdOrName }">
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
						代理商信息
					</td>
					<td nowrap height="10" class="p3">
						&nbsp;
					</td>
				</tr>
			</table>
			<table width="95%" border="1" cellspacing="0" cellpadding="0"
				align="center" class="table1">
				<tr>
					<td class="rd6">
						选择
					</td>
					<td class="rd6">
						需方客户代码
					</td>
					<td class="rd6">
						需方客户名称
					</td>
					<td class="rd6">
						客户类型
					</td>
				</tr>
				<tr>
				 <%
				    for(AimClient aim:list){
				 
				 %>
					<td class="rd8">
						<input type="radio" name="selectFlag" value="<%=aim.getId() %>"
							onDblClick="selectOk('<%=aim.getId()%>','<%=aim.getClientTermiId()%>','<%=aim.getName()%>')">
					</td>
					<td class="rd8">
						<%=aim.getClientTermiId() %>
					</td>
					<td class="rd8">
						<%=aim.getName() %>
					</td>
					<td class="rd8">
						<%=aim.getLevelName() %>
					</td>
				</tr>
			   <%
			    }
			   %>	
				
			</table>
			<table width="95%" height="30" border="0" align="center"
				cellpadding="0" cellspacing="0" class="rd1">
				<tr>
					<td nowrap class="rd19" height="2" width="36%">
						<div align="left">
							<font color="#FFFFFF">&nbsp;共<%=pm.getTotalPage() %>页</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<font color="#FFFFFF">当前第</font>&nbsp
							<font color="#FF0000"><%=pm.getCurrentPage()%></font>&nbsp
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
							<input name="btnOk" class="button1" type="submit" id="btnOk"
								value="确定" onClick="selectOk('${aims.id}','${aims.clientTermiId }','${aims.name }')">
							<input name="btnClose" class="button1" type="button"
								id="btnClose" value="关闭" onClick="window.close()">
						</div>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
