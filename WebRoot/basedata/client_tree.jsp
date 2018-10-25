<%@page import="com.xunpoit.manager.ClientManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="../style/erp.css">
		<style type="text/css">
<!--
a:link {
	text-decoration: none;
	color: #000000;
	font-size: 9pt;
	font-family: 宋体;
}
a:visited {
	text-decoration: none;
	color: #000000;
	font-size: 9pt;
	font-family: 宋体;
	
}
a:hover {
	text-decoration: none;
	color: #000000;
	font-size: 9pt;
	font-family: 宋体;

}
a:active {
	text-decoration: none;
	color: #000000;
	font-size: 9pt;
	font-family: 宋体;
}
-->
</style>

		<script language="JavaScript">
			<!--
			function display(id) {
				 //eval() 函数可计算某个字符串，并执行其中的 JavaScript 代码。
				 eval("var div=div"+id);
				 eval("var img=img"+id);
				 eval("var im=im"+id);
				 div.style.display=div.style.display=="block"?"none":"block";
				 img.src=div.style.display=="block"?"../images/minus.gif":"../images/plus.gif";
				 im.src=div.style.display=="block"?"../images/openfold.gif":"../images/closedfold.gif";
				 img.alt=div.style.display=="block"?"关闭":"展开";
			}
			//-->
		</script>
	</head>
	<body class="body1">
		<table>
			<tr>
				<td valign="top" nowrap="nowrap">
					<%=
					  ClientManager.getInstance().getClientTree() 
					%>
				</td>
			</tr>
		</table>
	</body>
</html>

