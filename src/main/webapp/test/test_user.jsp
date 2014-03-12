<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test_user.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/project2_js/user_opeare.js"></script>
	
	<script type="text/javascript">
		addUser = function(typeName){
			$.post('saveUser.action',
					{userName : $('#stuName').val(), IDNum : $('#stuNum').val(),typeName : typeName},
					function(returnData, status){
						
					});
		} ;
		
		getUsers = function(typeName,htmlList){
			$.post('getUsersList.action',{typeName : typeName},
					function(returnData, status){
						for(var i = 0; i < returnData.users.length; i++){
							addListHtml(returnData.users[i],htmlList) ;
						}
					}) ;
		};
		
		$(function(){
			getUsers('teacher','teacher_opeare');
			getUsers('student','student_opeare');
			getUsers('manager','manager_opeare');
		}) ;
	</script>
	
  </head>
  
  <body>
    <div id="student_opeare">
    	student name : <input type="text" id="stuName" /><br>
    	student number : <input type="text" id="stuNum"/><br>
    	<input type="button" value="add Student" onclick="addUser('student');" />
    	<h3>student List</h3>
    	<table id="studentsList" border="1">
    		<tbody>
    			<tr>
    				<td>ID</td>
    				<td>name</td>
    			</tr>
    		</tbody>
    	</table>
    </div>
    
    <div id="teacher_opeare">
    	student name : <input type="text" id="teaName" /><br>
    	student number : <input type="text" id="teaNum"/><br>
    	<input type="button" value="add Teacher" onclick="addUser('teacher');" />
    	<h3>Teacher List</h3>
    	<table id="teachersList" border="1">
    		<tbody>
    			<tr>
    				<td>ID</td>
    				<td>name</td>
    			</tr>
    		</tbody>
    	</table>
    </div>
    
    <div id="manager_opeare">
    	student name : <input type="text" id="manaName" /><br>
    	student number : <input type="text" id="manaNum"/><br>
    	<input type="button" value="add Manager" onclick="addUser('manager');" />
    	<h3>Manager List</h3>
    	<table id="managersList" border="1">
    		<tbody>
    			<tr>
    				<td>ID</td>
    				<td>name</td>
    			</tr>
    		</tbody>
    	</table>
    </div>
  </body>
</html>
