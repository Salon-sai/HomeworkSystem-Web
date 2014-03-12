<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test_login.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="themes/base/jquery.ui.all.css">
	
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jquery-ui.js"></script>
	
	<script type="text/javascript" src="js/ui/jquery.ui.core.js"></script>
	<script type="text/javascript" src="js/ui/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="js/ui/jquery.ui.button.js"></script>	
	
	<script type="text/javascript">
		
		checkLogin = function(){
			var id = $('#ID').val() ;
			var password = $('#password').val() ;
			$.post('checkLogin.action',
					{IDNum : id, password : password},
					function(returnData, status){
						if(returnData.returnMsg == null){
							window.location.href="LoginUser.action?typeName="+returnData.typeName ;
						}else{
							alert(returnData.returnMsg) ;
						}
					});
		} ;
	
	</script>
	
  </head>
  
  <body>
    User ID : <input type="text" id="ID"/><br>
  	User password : <input type="password" id="password"/>
  	<input type="button" value="submit" onclick="checkLogin();" />
  </body>
</html>
