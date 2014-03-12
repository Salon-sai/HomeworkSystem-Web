<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'student_course.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <div id="student-information" >
	  	<h1 style="float: left;">Hello student : 
	  		<span title="studentName">
	  			${sessionScope.student.name }&nbsp;&nbsp;&nbsp;
	  		</span>
	  	</h1>
	  	<h1 style="float: left;">Your Department :  
	  		<span title="ClassName">
	  			${sessionScope.student.information.information.information.name }&nbsp;&nbsp;&nbsp;
	  		</span>
	  	</h1>
	  	<h1 style="float: left;">Your Major : 
	  		<span title="ClassName">
	  			${sessionScope.student.information.information.name }&nbsp;&nbsp;&nbsp;
	  		</span>
	  	</h1>
	    <h1 style="float: left;">Your Class : 
	    	<span title="ClassName">
	    		${sessionScope.student.information.name }
	    	</span>
	    </h1>
  	</div>
  	
  </body>
</html>
