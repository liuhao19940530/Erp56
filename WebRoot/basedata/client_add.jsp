<%@page import="com.xunpoit.client.ClientLevel"%>
<%@page import="com.xunpoit.client.AbstractDataDict"%>
<%@page import="com.xunpoit.manager.DataDictManager"%>
<%@page import="com.xunpoit.client.Client"%>
<%@page import="com.xunpoit.manager.ClientManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
//request.getContextPath()获取上下文
String path = request.getContextPath();
//request.getScheme()获取协议名  request.getServerName()获取服务器名  request.getServerPort()获取端口号
//绝对路径    http://localhost:8080/erp16/
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String id = request.getParameter("id");//首先获取的是url带过来的id，后面提交了表单，也可以获取隐藏域中的id

Client client = new Client();

String cid = request.getParameter("clientId");
String msg = "";

ClientManager manager = ClientManager.getInstance();

Client client01 = manager.selectClient(cid);

if(client01 != null){
	
	msg = "此代理商代码已经存在！";

}else{
	//先得到代理商级别
	DataDictManager dataManager = DataDictManager.getInstance();
	
	List<AbstractDataDict> dataDict = dataManager.findAll("C",ClientLevel.class.getName());
	
	pageContext.setAttribute("dataDict",dataDict);
	
	String cmd = request.getParameter("cmd");
	
	if("add".equals(cmd)){
		
		client.setPid(Integer.parseInt(id));
	
		client.setClientId(cid);
	
		String name = request.getParameter("clientName");
	
		client.setName(name);
	
		ClientLevel level = new ClientLevel();
		level.setId(request.getParameter("clientLevel"));
	
		client.setClientLevelId(level);
	
		client.setBankAcctNo(request.getParameter("bankAcctNo"));
	
		client.setContactTel(request.getParameter("contactTel"));
	
		client.setAddress(request.getParameter("address"));
	
		client.setZipCode(request.getParameter("zipCode"));
	
		//is_leaf和is_client是非空字段，此处先设置
		client.setIsLeaf("Y");
			
		client.setIsClient("Y");
		  
		  //调用添加的方法
		  boolean flag = manager.addClientOrNode(client);
		  
		  if(flag){
			  
			  msg = "添加成功！";
			  
			  response.sendRedirect("client_node_crud.jsp?id="+id);
		  }else{
			  
			  msg = "添加失败！";
		  }
	}
}
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>添加代理商</title>
		<link rel="stylesheet" href="../style/erp.css">
		<script src="../script/client_validate.js"></script>
		<script type="text/javascript">
		function check(){
			
		  var clientId = document.getElementById("clientId");
     	   
     	  if(clientId.value==""){
     		   
     		   alert("代理商代码不能为空!");
     		   
     		   return;
     	   }
     	   
     	   var clientName = document.getElementById("clientName");
     	   
     	   if(clientName.value==""){
     		   
     		   alert("代理商名称不能为空!");
     		   return;
     	   }
     	   
     	   var bankAcctNo = document.getElementById("bankAcctNo").value;
     	   
     	   var reg = /^[0-9]{15,20}$/;
     	   
     	   if(!reg.test(bankAcctNo)){
     		   
     		   alert("银行账户必须是15到20位的数字！");
     		   return;
     	   }
     	   
     	   var contactTel = document.getElementById("contactTel").value;
     	   
     	   var reg01 = /^[0-9]{6,11}$/;
     	   
			   if(!reg01.test(contactTel)){
     		   
     		   alert("电话必须是6到11位的数字！");
     		   return;
     	   }
     	   
			   var address = document.getElementById("address");
	   
			   if(address.value==""){
     		   
     		   alert("地址不能为空！");
     		   return;
     	   }
			   
			   var zipCode = document.getElementById("zipCode");
		   	   
			   if(zipCode.value==""){
     		   
     		   alert("邮编不能为空！");
     		   return;
     	   }
			   
     	   form1.submit();
        }
		
		var xmlHttp;
		
		function createXMLHttp(){
			
			if(window.ActiveXObject){
				
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
				
			}else if(window.XMLHttpRequest){
				
				xmlHttp = new XMLHttpRequest();
			}
		}
		
		function validateCid(cid){
			
			//alert(cid);
			createXMLHttp();//实例化xmlHttp
			
			//xmlHttp.open("GET","../servlet/validateClientId?cid="+cid+"&time="+new Date().getTime(),true);
			//xmlHttp.open("GET","<%=basePath%>/servlet/validateClientId?cid="+cid+"&time="+new Date().getTime(),true);
			
			//EL表达式来获取根路径   /erp16/
			xmlHttp.open("GET","${pageContext.request.contextPath}/servlet/validateClientId?cid="+cid+"&time="+new Date().getTime(),true);
			
			xmlHttp.send(null);
			
		    xmlHttp.onreadystatechange = function(){
		    	
		    	if(xmlHttp.readyState==4){
		    		
		    		if(xmlHttp.status == 200){
		    			
		    			var validateStr = document.getElementById("validateStr");
		    			
		    			validateStr.innerHTML = "<font color='red'>"+xmlHttp.responseText+"</font>";
		    		}
		    	}
		    };
		}
		</script>
	</head>

	<body class="body1" action="client_add.jsp">
		<form name="form1">
		    <input type="hidden" name="cmd" value="add"/>
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
					height="8">
					<tr>
						<td width="522" class="p1" height="2" nowrap>
							<img src="../images/mark_arrow_03.gif" width="14" height="14">
							&nbsp;
							<b>基础数据管理&gt;&gt;代理商维护&gt;&gt;添加代理商</b>
							<font color="red"><%=msg %></font>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="22%" height="29">
							<div align="right">
								<font color="#FF0000">*</font>代理商代码:&nbsp;
							</div>
						</td>
						<td width="78%">
							<input name="clientId" type="text" class="text1" id="clientId"
								size="10" maxlength="10" onblur="validateCid(this.value)">
							<span id="validateStr"></span>	
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>代理商名称:&nbsp;
							</div>
						</td>
						<td>
							<input name="clientName" type="text" class="text1"
								id="clientName" size="40" maxlength="40">
						</td>
					</tr>
					<tr>
						<td height="15">
							<div align="right">
								<font color="#FF0000">*</font>代理商类型:&nbsp;
							</div>
						</td>
						<td>
							<select name="clientLevel" class="select1" id="clientLevel">
							    <c:forEach items="${pageScope.dataDict}" var="data">
								<option value="${data.id}">
				                      ${data.name}
								</option>
								</c:forEach>
								
							</select>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								银行帐号:&nbsp;
							</div>
						</td>
						<td>
							<input name="bankAcctNo" type="text" class="text1"
								id="bankAcctNo" size="20" maxlength="20">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								联系电话:&nbsp;
							</div>
						</td>
						<td>
							<input name="contactTel" type="text" class="text1"
								id="contactTel" size="20" maxlength="20">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								地址:&nbsp;
							</div>
						</td>
						<td>
							<input name="address" type="text" class="text1" id="address"
								size="20" maxlength="20">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								邮编:&nbsp;
							</div>
						</td>
						<td>
							<input name="zipCode" type="text" class="text1" id="zipCode"
								size="10" maxlength="10">
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<div align="center">
					<input name="btnAdd" class="button1" type="button" id="btnAdd"
						value="添加" onclick="check()">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="btnBack" class="button1" type="button" id="btnBack"
						value="返回" onclick="history.go(-1)" />
				</div>
			</div>
		</form>
	</body>
</html>
