<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.xunpoit.user.User"%>
<%@page import="com.xunpoit.entity.PageModel"%>
<%@page import="com.xunpoit.manager.UserManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% 
   //request.setCharacterEncoding("utf-8");
   //response.setContentType("text/html;charset=utf-8");
   
   UserManager manager = new UserManager();
   
   //设置初始值
   int currentPage = 1;
   
   int pageSize = 4;
   
   String currentPageStr = request.getParameter("currentPage");
   
   if(currentPageStr!=null){
	   
	   currentPage = Integer.valueOf(currentPageStr);
   }
   
   //得到分页模型
   PageModel<User> pm = manager.queryAllUser(currentPage, pageSize);
   
   //删除的时候
   String cmd=request.getParameter("cmd");
 
   boolean flag=false;
   if("delete".equals(cmd)){
	   //我们将复选框的value值也是设置的user.getUserId()
	   String[] selectFlag = request.getParameterValues("selectFlag");
	   
	   //因为可以选中多个，所以需要用for循环一个个删除
	   /*第一种方式
       for(int i=0;i<selectFlag.length;i++){
		   
	      String id = selectFlag[i];
		   
	      flag = manager.deleteUser(id);
		   
       }
	   */
	   
	   //第二种方式
	   //flag = manager.deleteUserByIds(selectFlag);//预处理语句
	   flag = manager.deleteUserByIds01(selectFlag);//拼接字符串
	   
       if(flag){
		   //成功就跳转页面，有刷新作用
	     response.sendRedirect("user_maint.jsp");
		   
	     return;
      }
	
   }
   
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>用户维护</title>
		<link rel="stylesheet" href="../style/erp.css">
		<script type="text/javascript">
	
	function addUser() {
		window.self.location = "user_add.jsp";	
	}
	
	function modifyUser() {
		
		var selectFlag = document.getElementsByName("selectFlag");//获取的是元素
		
		var count=0;
		
		var j = 0;
		
		//用count来计数，如果有一个被选中，就加1
		for(var i=0;i<selectFlag.length;i++){
			
			if(selectFlag[i].checked){
				
				count++;
				
				j=i;
			}
		}
		
		if(count<1){
			
			alert("必须选择一个，才可以修改！");
			return;
		}
		
		if(count>1){
			
			alert("只能选择一个，进行修改！");
			return;
		}
		
		//selectFlag[j]是获取的你选中的那一个对象元素，只有.value才是你设置的userId
		window.self.location = "user_modify.jsp?userId="+selectFlag[j].value;
	}
	
	function deleteUser() {
		var selectFlag = document.getElementsByName("selectFlag");
		
		var count=0;
		
		var j="";
		
		for(var i=0;i<selectFlag.length;i++){
			
			if(selectFlag[i].checked){
				
				count++;
					
				if(selectFlag[i].value=="root"){
					
					j = selectFlag[i].value;
				}
			}   
		
		}
		
		if(count<1){
			alert("至少选择一个，才可以删除！");
			return;
		}
		
		//删除用户的时候，出现提示信息
		if(confirm("您确定要删除选择的用户吗？")){
			
			if(j=="root"){
				
				 alert("root为系统用户，您不能删除！");
				 return;
			  }	
			
			var userForm = document.getElementById("userForm");
			
			userForm.action="user_maint.jsp";
			
			userForm.submit();
		}
	}
		
	function checkAll() {
		
		//点击ifAll复选框，则下面的复选框全部选中
		var ifAll = document.getElementsByName("ifAll");//父复选框
		
		var selectFlag = document.getElementsByName("selectFlag");//子复选框
		
		for(var i=0;i<selectFlag.length;i++){
			
			//getElementsByName()获取的是一组对象，父复选框只有一个
			selectFlag[i].checked = ifAll[0].checked;
		}
	}

	function topPage() {
		
		//指定跳转的页面
		window.self.location = "user_maint.jsp?currentPage=<%=pm.getTopPage()%>";
	}
	
	function previousPage() {
		//指定跳转的页面
		window.self.location = "user_maint.jsp?currentPage=<%=pm.getPreviousPage()%>";
	}	
	
	function nextPage() {
		//指定跳转的页面
		window.self.location = "user_maint.jsp?currentPage=<%=pm.getNextPage()%>";
	}
	
	function bottomPage() {
		//指定跳转的页面
		window.self.location = "user_maint.jsp?currentPage=<%=pm.getBottomPage()%>";
	}

</script>
	</head>

	<body class="body1">
		<form name="userform" id="userform">
		    <input type="hidden" name="cmd" value="delete"/>
			<div align="center">
				<table width="95%" border="0" cellspacing="0" cellpadding="0"
					height="35">
					<tr>
						<td class="p1" height="18" nowrap>&nbsp;
							
						</td>
					</tr>
					<tr>
						<td width="522" class="p1" height="17" nowrap>
							<img src="../images/mark_arrow_02.gif" width="14" height="14">
							&nbsp;
							<b>系统管理&gt;&gt;用户维护</b>
			
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
					<td width="55" class="rd6">
						<input type="checkbox" name="ifAll" onClick="checkAll()">
					</td>
					<td width="119" class="rd6">
						用户代码
					</td>
					<td width="152" class="rd6">
						用户名称
					</td>
					<td width="166" class="rd6">
						联系电话
					</td>
					<td width="150" class="rd6">
						email
					</td>
					<td width="153" class="rd6">
						创建日期
					</td>
				</tr>
				<tr>
				   <%
				   //得到集合对象
				   List<User> dataList = pm.getDataList();
				   
				   for(User user:dataList){//一个个的取出封装好的user对象
					   
					   SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
				   
					   Date date = user.getCreateDate();//有些在数据库中，没有填写时间
				   
					   String time=null;
					   
					   if(date!=null){//等于Null，转换的时候有异常
				         time = sdf.format(date);
					   }		   
				   %>
					<td class="rd8">
						<input type="checkbox" name="selectFlag" class="checkbox1"
							value="<%=user.getUserId()%>">
					</td>
					<td class="rd8">
						<%=user.getUserId()%>
					</td>
					<td class="rd8">
						<%=user.getUserName()%>
					</td>
					<td class="rd8">
						<%=user.getContactTel()==null?"":user.getContactTel()%>
					</td>
					<td class="rd8">
						<%=user.getEmail()==null?"":user.getEmail()%>
					</td>
					<td class="rd8">
						<%=time==null?"":time%>
					</td>
				</tr>
			  <%
				}
			  %>
				
			</table>
			<table width="95%" height="30" border="0" align="center"
				cellpadding="0" cellspacing="0" class="rd1">
				<tr>
					<td nowrap class="rd19" height="2">
						<div align="left">
							<font color="blue">&nbsp;共<%=pm.getTotalPage() %>页</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<font color="#FFFFFF">当前第</font>&nbsp
							<font color="#FF0000"><%=pm.getCurrentPage() %></font>&nbsp
							<font color="#FFFFFF">页</font>
						</div>
					</td>
					<td nowrap class="rd19">
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
							<input name="btnAdd" type="button" class="button1" id="btnAdd"
								value="添加" onClick="addUser()">
							<input name="btnDelete" class="button1" type="button"
								id="btnDelete" value="删除" onClick="deleteUser()">
							<input name="btnModify" class="button1" type="button"
								id="btnModify" value="修改" onClick="modifyUser()">
						</div>
					</td>
				</tr>
			</table>
			<p>&nbsp;
				
			</p>
		</form>
	</body>
</html>
