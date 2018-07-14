<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'home.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
   <!--  <div style="width:100%; height:100%;  text-align: center ;background-color:#E7F0FF;">
    	<img width="300px" height="200px" alt="" src="img/yqy.png">
    </div> -->
    <div style="width:100%; height:100%; position: relative;background-color:#ECECEC;">
		<img alt="" src="img/yqy.png" style="width: 300px; height: 330px;position: absolute; left:40%; top: 40%; margin-left: -60px;margin-top: -20px;" />
    </div>
  </body>
</html>
