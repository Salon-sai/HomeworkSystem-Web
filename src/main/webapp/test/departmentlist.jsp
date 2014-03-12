<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'departmentlist.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jquery-ui.js"></script>
	
	<script type="text/javascript">
	
		$(function(){
		/*
			$('#Add_department').hover(function(){
				$("#add_department_form").show(2000);
			},function(){
				$("#add_department_form").hide(2000);
			});
		*/	
			function deleteDepartment(){
				var object = $(this) ;
				$.post("manager/DeleteDepartment",
						{department_id:object.prev().val()},
						function(returnData, status){
							object.parent().remove() ;
						});
				
			}
			/*
				question : If there is no department in the html, how to do it ? 2013.5.28
			*/
			function adddepartment(){
				$.post("manager/AddDepartment",
						{name:$("#add_department_name").val()},
						function(returnData, status){
							var department_name = "<div title='department_name'>"
								+ "Department Name: </br><a href='manager/FindByDepartment?department_id="+returnData.department_id+"'>"+ returnData.name
								+"</a><input type='hidden' value='"+returnData.department_id+"' />"
								+ "<input type='button' name='deleteDepartment' value='deleteDepartment'></br></br></div>" ;
								$("[title='department_name']:last").after(department_name) ;
								$("[title='department_name']:last > [name='deleteDepartment']").click(deleteDepartment);
						});
			}
		
			$("[name='deleteDepartment']").click(deleteDepartment);
			
			$("#adddepartment").click(adddepartment);
			
		});
		
		function deleteDepartment(id){
			var action = "DeleteDepartment?department_id=";
			window.location.href = action + id ;
		}
		
		
		
	</script>
	
  </head>
  
  <body>
  
  
    <s:iterator value="departments">
    		<div title="department_name">
    		Department Name: <br/>
    			<s:a href="manager/FindByDepartment?department_id=%{id}"><s:property value="name"/></s:a>
    			<s:hidden value="%{id}"></s:hidden>
    			<input type="button" value="deleteDepartment" name="deleteDepartment"/><br/><br/>
    		</div>
    </s:iterator>
    
    <div id="Add_department" style="width:250px">
    	<div id="add_department_titile">Add Department</div>
    	<div id="add_department_form">
    		<form id="AddDepartment" style="width:250px">
    			<input type="text" id="add_department_name"/>
    			<input type="button" id="adddepartment" value="add department"/>
    		</form>
    	</div>
    </div>
    
    
  </body>
</html>
