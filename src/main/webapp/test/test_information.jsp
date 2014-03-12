<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test_information.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/project2_js/information_opeare.js"></script>
	
	<script type="text/javascript">
		addType = function(){
			$.post("saveType.action",
					{typeName : $('#type_opeare').find('#addTypeName').val()},
					function(returnData){
						//add html
						addTypehtml(returnData.type) ;
					});
		};
		
		getTypes = function(){
			$.post("getTypeList.action",{},
					function(returnData){
						for(var i = 0; i < returnData.types.length;i++){
							addTypehtml(returnData.types[i]) ;
						}
					});
		};
		
		deleteType = function(button){
			var TR = $(button).parents('tr');
			$.post("deleteTypeByName.action",
					{typeName : TR.find('td:eq(0)').html()},
					function(returnData){
						//add html
						TR.remove() ;
					});
		};
		
		alterType = function(){
			$.post("");
		};
		
		
		addTypehtml = function(type){
			var typeTR = "<tr><td>"+type.name+"</td><td><input type='button' value='alter'/></td>"
					+"<td><input type='button' value='delete' onclick='deleteType(this);'/></td></tr>" ;
			$('#typeList tbody').append(typeTR) ;
		};
		
		removeTypeHtml = function(){
			
		};
		
		saveteacher = function(){
			$.post('saveUser.action',
					{},
					function(returnData,status){
						
					});
		} ;
		
		getTypes();getInformations('department','');
	</script>
	
  </head>
  
  <body onload="">
    <div id="type_opeare" style="float:left">
    	add Type name : <input type="text" id="addTypeName" />
    	<input type="button" value="add type" onclick="addType();"/><br>
    	
    	<h3>type list</h3>
    	<table id="typeList" border="1">
    		<tbody>
	    		<tr>
	    			<td>type name</td>
	    			<td>alter name</td>
	    			<td>delete</td>
	    		</tr>
    		</tbody>
    	</table>
    </div>
    ---------------------------------------------------------------------------------------
    <br><br>
    <div id="department_opeare" style="float:left">
    	add Department name : <input type="text" id="addDepartmentName" />
    	<input type="button" value="add department" onclick="saveDepartment();" /><br>
    	<h3>department list</h3>
    	<table id="departmentList" border="1">
    		<tbody>
    			<tr>
    				<td>department name</td>
    				<td>alter name</td>
    				<td>delete</td>
    				<td>more detail</td>
    			</tr>
    		</tbody>
    	</table>
    </div>
    ----------------------------------------------------------------------------------
    <br><br>
    <div id="major_opeare" style="float:left">
    	department name : <h5></h5>
    	add major name : <input type="text" id="addMajorName" />
    	<input type="button" value="add major" onclick="saveMajor();" />
    	<h3>major list</h3>
    	<table id="majorList" border="1">
    		<tbody>
    			<tr>
    				<td>major name</td>
    				<td>alter name</td>
    				<td>delete</td>
    				<td>more detail</td>
    			</tr>
    		</tbody>
    	</table>
    </div>
    ---------------------------------------------------------------------------------<br><br>
    <div id="student_opeare">
    	class name : <h5></h5>
    	add student name : <input type="text" id="addStudentName" />
    	<input type="button" value="add student" onclick="saveStudent();" />
    	<h3>student list</h3>
    	<table id="studentList" border="1">
    		<tbody>
    			<tr>
    				<td>student name</td>
    				<td>student number</td>
    				<td>alter name</td>
    				<td>delete</td>
    				<td>more detail</td>
    			</tr>
    		</tbody>
    	</table>
    </div>
    ---------------------------------------------------------------------------------<br>
   <div id="class_opeare">
    	Major name : <h5></h5>
    	add Major name : <input type="text" id="addClassName" />
    	<input type="button" value="add class" onclick="saveClass();" />
    	<h3>class list</h3>
    	<table id="classList" border="1">
    		<tbody>
    			<tr>
    				<td>class name</td>
    				<td>alter name</td>
    				<td>delete</td>
    				<td>more detail</td>
    			</tr>
    		</tbody>
    	</table>
    </div>
    -----------------------------------------------------------------------------------<br>
    <div id="teacher_opeare">
    	<input type="text" value="create teacher" onclick="saveteacher();" />
    </div>
  </body>
</html>
