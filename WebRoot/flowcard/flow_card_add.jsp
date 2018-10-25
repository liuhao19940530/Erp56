<%@page import="com.xunpoit.entity.AimClient"%>
<%@page import="com.xunpoit.client.Client"%>
<%@page import="com.xunpoit.manager.ClientManager"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8" errorPage="../errorPage.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>添加流向单维护</title>
		<link rel="stylesheet" href="<%=basePath%>style/erp.css">
		<link href="<%=basePath%>style/JSCalendar.css" rel=stylesheet type=text/css>
		<script src="<%=basePath%>script/JSCalendar.js"></script>
		<script src="<%=basePath%>script/client_validate.js"></script>
		<script language="javascript">
    var rowIndex = 0;
    
    
    function selectAimClient(index) {
		window.open('<%=basePath%>flowcard/aim_client_select.jsp?index=' + index, '请选择需方客户', 'width=700, height=400, scrollbars=no');
    }   
     
    function selectItem(index) {
		window.open('<%=basePath%>flowcard/item_select.jsp?index=' + index, '请选择物料', 'width=700, height=400, scrollbars=no');
    } 
     
    function addOneLineOnClick() {
		var row=tblFlowCardDetail.insertRow(tblFlowCardDetail.rows.length);
		var col = row.insertCell(0);
		col.innerHTML = "<input type='hidden' name='aid' id='aid'/><input readonly=\"true\" maxLength=6 size=6 name='aimId'><input type=button  value =...   name=btnSelectAimClient index=\""+ rowIndex +"\" onclick=\"selectAimClient(this.index)\">";
		col = row.insertCell(1);
		col.innerHTML = "<tr><input id=aimName name=aimName size=25 maxlength=25 readonly=\"true\">";
		col = row.insertCell(2);
		col.innerHTML = "<input readonly=\"true\" maxLength=6 size=6 name=itemNo id=itemNo><input type=button  value =...   name=btnSelectItem index=\""+ rowIndex +"\" onclick=\"selectItem(this.index)\">";
		col = row.insertCell(3);
		col.innerHTML = "<input id=itemName name=itemName size=25 maxlength=25 readonly=\"true\">";		
		col = row.insertCell(4);
		col.innerHTML = "<input id=spec name=spec size=10 maxlength=10 readonly=\"true\">";
		col = row.insertCell(5);
		col.innerHTML = "<input id=pattern name=pattern size=10 maxlength=10 readonly=\"true\">";
		col = row.insertCell(6);
		col.innerHTML = "<input id=unit name=unit size=4 maxlength=4 readonly=\"true\">";
		col = row.insertCell(7);
		col.innerHTML = "<input id=qty name=qty size=6 maxlength=6>";
		col = row.insertCell(8);
		col.innerHTML = "<input type='button' value='删除' id=btnDeleteLine name=btnDeleteLine onclick=\"return DeleteRow('row" + rowIndex + "')\"></tr>";
		row.setAttribute("id", "row" + rowIndex);
		row.setAttribute("name", "row" + rowIndex);
		rowIndex++;
	}
	
	function DeleteRow(rowTag){
 	    var i = tblFlowCardDetail.rows(rowTag).rowIndex;
 		var j;
		for(j=i;j<=rowIndex;j++) {	
			tblFlowCardDetail.rows(j).cells(0).all("btnSelectAimClient").index--;
			tblFlowCardDetail.rows(j).cells(2).all("btnSelectItem").index--;	
		}
       	tblFlowCardDetail.deleteRow(i);
		rowIndex--;
	}

	function addFlowCard() {
	
		var clientId = document.getElementsByName("clientId");
		
		if(clientId[0].value==""){
			
			alert("供方代理商代码不能为空！");
			return;
		}
		
		var aimId = document.getElementsByName("aimId");
		
		if(aimId.length<1){
			
			alert("请选择需方后保存！");
			return;
		}
		
		for(var i=0;i<aimId.length;i++){
			
			if(aimId[i].value==""){
				
				alert("需方代码不能为空！");
				return;
				
			}
			
			if(aimId[i].value==clientId[0].value){
				
				alert("供方代码与需方代码不能相同！");
				return;
			}
			
		}
		
		var itemNo = document.getElementsByName("itemNo");

		for(var i=0;i<itemNo.length;i++){
			
			if(itemNo[i].value==""){
				
				alert("物料代码不能为空！");
				return;
			}
			
		}
		
		var qty = document.getElementsByName("qty");
		
		for(var i=0;i<qty.length;i++){
			
			var reg = /^[0-9]{1,10}$/;
			
			if(!reg.test(qty[i].value)){
				
				alert("操作数量必须是数字！");
				return;
			}
		}
		
		if(confirm("您确定要保存吗？")){
		   flowCardAddForm.submit();
		}   
	}
	
	function goBack() {
		window.self.location="<%=basePath%>servlet/FlowCardServlet?cmd=all";
	}	

</script>
	</head>

	<body class="body1">
		<div align="center">
			<form name="flowCardAddForm" method="post" action="<%=basePath%>servlet/FlowCardServlet?cmd=add">
				<table width="95%" border="0" cellspacing="2" cellpadding="2">
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
				</table>
				<table width="95%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="522" class="p1" height="2" nowrap>
							<img src="<%=basePath%>images/mark_arrow_03.gif" width="14" height="14">
							&nbsp;
							<b>代理商库存管理&gt;&gt;流向单维护&gt;&gt;添加</b>
							
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="15%" height="29">
							<div align="right">
								<font color="#FF0000">*</font>供方代理商代码:&nbsp;
							</div>
						</td>
						<td width="16%">
						    <input type="hidden" name="cid"/>
							<input name="clientId" type="text" class="text1" id="clientId"
								size="10" maxlength="10" readonly="true">
							<input name="btnSelectClient" type="button" id="btnSelectClient"
								value="..." class="button1"
								onClick="window.open('<%=basePath%>flowcard/client_select.jsp', '选择代理商', 'width=700, height=400, scrollbars=no')">
						</td>
						<td width="16%">
							<div align="right">
								供方代理商名称:&nbsp;
							</div>
						</td>
						<td width="29%">
							<input name="clientName" type="text" class="text1"
								id="clientName" size="40" maxlength="40" readonly="true"/>
						</td>
						<td width="7%">
							&nbsp;
						</td>
						<td width="17%">
							<label></label>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0"
					name="tblFlowCardDetail" id="tblFlowCardDetail">
					<tr>
						<td nowrap>
							<div align="left">
								<font color="#FF0000">*</font>需方客户代码
							</div>
						</td>
						<td nowrap>
							<div align="left">
								需方客户名称
							</div>
						</td>
						<td nowrap>
							<div align="left">
								<font color="#FF0000">*</font>物料代码
							</div>
						</td>
						<td nowrap>
							<div align="left">
								物料名称
							</div>
						</td>
						<td nowrap>
							规格
						</td>
						<td nowrap>
							型号
						</td>
						<td nowrap>
							计量单位
						</td>
						<td nowrap>
							<font color="#FF0000">*</font>操作数量
						</td>
						<td nowrap>
							<div align="left">
								删除
							</div>
						</td>
					</tr>
				</table>
				<p>
					<input name="btnAddLine" type="button" id="btnAddLine"
						onClick="return addOneLineOnClick()" value="加入一行">
					<input name="btnSave" type="button" id="btnSave" value="保存"
						onClick="addFlowCard()">
					<input name="btnBack" type="button" id="btnBack" onClick="goBack()"
						value="返回">
				</p>
				<p>
					&nbsp;
				</p>
				<p>
					&nbsp;
				</p>
			</form>
			<p>
				&nbsp;
			</p>
		</div>
	</body>
</html>
