<%@page import="java.util.List"%>
<%@page import="com.xunpoit.client.Client"%>
<%@page import="com.xunpoit.entity.PageModel"%>
<%@page import="com.xunpoit.manager.ClientManager"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
    //获取模糊查询的条件
    String clientIdOrName = request.getParameter("clientIdOrName");

    int pageSize = 3;

    int currentPage = 1;
    
    //获取传递过来的当期页参数
    String currentStr = request.getParameter("currentPage");

    if(currentStr != null && !"".equals(currentStr)){
    	
    	currentPage = Integer.parseInt(currentStr);
    }
    
    //获取ClientManager中的所有供方的方法
    ClientManager manager = ClientManager.getInstance();

    //得到方法
    PageModel<Client> pm = manager.findAllClient(pageSize,currentPage,clientIdOrName);
    
    //el表达式获取的都是4大范围之内的对象，或者当前对象，或者param,paramValues中，所以这里要保存在范围之中
    session.setAttribute("pm",pm);
    
    session.setAttribute("clientIdOrName",clientIdOrName);
    
  //获取的是需方的哪一个被选中
    String[] cid = request.getParameterValues("selectFlag");
    
    if(cid!=null){
 	   
 	   for(int i=0;i<cid.length;i++){
 		   
 		   Client client = manager.getClientOrNodeById(Integer.parseInt(cid[i]));
 		   
	       session.setAttribute("client", client);
 		 
 	   }	   
    }
   
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>请选择代理商</title>
		<link rel="stylesheet" href="<%=basePath%>style/erp.css">
		<script src="<%=basePath%>script/client_validate.js"></script>
		<script type="text/javascript">
	function topPage() {
		
		window.self.location = "<%=basePath%>flowcard/client_select.jsp?currentPage=${pm.topPage}&clientIdOrName=${clientIdOrName}";
	}
	
	function previousPage() {
		
		window.self.location = "<%=basePath%>flowcard/client_select.jsp?currentPage=${pm.previousPage}&clientIdOrName=${clientIdOrName}";
	}
	
	function nextPage() {
		
		window.self.location = "<%=basePath%>flowcard/client_select.jsp?currentPage=${pm.nextPage}&clientIdOrName=${clientIdOrName}";
	}
	
	function bottomPage() {

		window.self.location = "<%=basePath%>flowcard/client_select.jsp?currentPage=${pm.bottomPage}&clientIdOrName=${clientIdOrName}";
	}
	
	function queryClient() {
	}
	
	function selectOk(s1,s2,s3) {
		//js中，window事件可以将当前页面的cid,clientId,clientName赋值回，打开本页面的页面，也就是flow_card_add.jsp页面
		window.opener.document.all.cid.value=s1;
		
		window.opener.document.all.clientId.value=s2;
		
		window.opener.document.all.clientName.value=s3;
		
		window.close();//关闭窗口
	}
	
	function init(){
		
		var clientIdOrName = document.getElementsByName("clientIdOrName");
		
		clientIdOrName[0].focus();
	}
	
</script>
	</head>

	<body class="body1" onload="init()">
		<form name="clientForm" method="post" action="<%=basePath%>flowcard/client_select.jsp">
			<div align="center">
				<table width="95%" border="0" cellspacing="0" cellpadding="0"
					height="34">
					<tr>
						<td width="522" class="p1" height="34" nowrap>
							<img src="<%=basePath%>images/search.gif" width="32" height="32">
							&nbsp;
							<b>请选择代理商</b>
						</td>
					</tr>
				</table>
				<hr width="100%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="17%" height="29">
							<div align="left">
								代理商代码/名称:
							</div>
						</td>
						<td width="57%">
							<input name="clientIdOrName" type="text" class="text1"
								id="clientIdOrName" size="50" maxlength="50" value="${clientIdOrName }">
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
						代理商代码
					</td>
					<td class="rd6">
						代理商名称
					</td>
					<td class="rd6">
						代理商类型
					</td>
				</tr>
				<tr>
				  <c:forEach items="${pm.dataList }" var="client">
				   
					<td class="rd8">
						<input type="radio" name="selectFlag" value="${client.id }"
							onDblClick="selectOk('${client.id}','${client.clientId}','${client.name}')">
					</td>
				
					<td class="rd8">
						${client.clientId}
					</td>
					<td class="rd8">
						${client.name }
					</td>
					<td class="rd8">
						${client.clientLevelId.name}
					</td>
				</tr>
			   </c:forEach>	
			</table>
			<table width="95%" height="30" border="0" align="center"
				cellpadding="0" cellspacing="0" class="rd1">
				<tr>
					<td nowrap class="rd19" height="2" width="36%">
						<div align="left">
							<font color="#FFFFFF">&nbsp;共${pm.totalPage }页</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<font color="#FFFFFF">当前第</font>&nbsp
							<font color="#FF0000">${pm.currentPage }</font>&nbsp
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
								value="确定" onClick="selectOk('${client.id}','${client.clientId }','${client.name }')">

							<input name="btnClose" class="button1" type="button"
								id="btnClose" value="关闭" onClick="window.close()">
						</div>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
