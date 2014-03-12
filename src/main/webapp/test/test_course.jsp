<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test_course.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="themes/base/jquery.ui.all.css">
	<style>
		body { font-size: 62.5%; }
		label, input { display:block; }
		input.text { margin-bottom:12px; width:95%; padding: .4em; }
		fieldset { padding:0; border:0; margin-top:25px; }
		h1 { font-size: 1.2em; margin: .6em 0; }
		div#users-contain { width: 350px; margin: 20px 0; }
		div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
		div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
		.ui-dialog .ui-state-error { padding: .3em; }
		.validateTips { border: 1px solid transparent; padding: 0.3em; }
	</style>
	<script src="js/jquery.js"></script>
	<script src="js/external/jquery.bgiframe-2.1.2.js"></script>
	<script src="js/ui/jquery.ui.core.js"></script>
	<script src="js/ui/jquery.ui.widget.js"></script>
	<script src="js/ui/jquery.ui.mouse.js"></script>
	<script src="js/ui/jquery.ui.button.js"></script>
	<script src="js/ui/jquery.ui.draggable.js"></script>
	<script src="js/ui/jquery.ui.position.js"></script>
	<script src="js/ui/jquery.ui.resizable.js"></script>
	<script src="js/ui/jquery.ui.dialog.js"></script>
	<script src="js/ui/jquery.ui.effect.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	
		var create_course_name // text of the create-course-form 
				,alter_course_name // text of the alter-course-form
				,validtips //validate of the form
				,old_course_name // temporary value
				,TR // assign one tr element
				,allFields; //all the fields in form
		
		createCourse = function(){
			$.post("courseOperate/createCourse.action",
					{courseName : create_course_name.val(),typeName : $('#create-course-type-choose').val()},
					function(returnData,status){
						if(status == "success"){
							addCourseHtml(returnData.course) ;
							$("#create-course-form").dialog("close") ;
						}
					});
		};
		
		findAllCourse = function(bn){
			$.post("courseOperate/findAllCourse.action",{},
					function(returnData,status){
						$.each(returnData.courses,function(infoIndex,info){
							addCourseHtml(info);
						});
					});
		};
		
		deleteCourse = function(){
			$.post("courseOperate/deleteCourse.action",{courseName : TR.find('td:eq(0)').html()},
					function(returnData,status){
						TR.remove() ;
					});
		} ;
		
		alterCourse = function() {
			$.post("courseOperate/alterCourse.action",
					{courseName : old_course_name,alteredName : alter_course_name.val()},
					function(returnData,status){
						TR.find('td:eq(0)').html(returnData.course.name);
						$('#alter-form').dialog("close");
					});
		};
		
		alterCourseform = function(bn){
			TR = $(bn).parents('tr') ;
			old_course_name = TR.find('td:eq(0)').html() ;//temporary value
			alter_course_name.val(old_course_name);
			$('#alter-course-type-choose').val($(bn).parents('tr').find('td:eq(1)').html());
			$('#alter-form').dialog("open") ;
		};
		
		addCourseHtml = function(course){
			$("#courses tbody").append("<tr>"
				+"<td>"+course.name+"</td>"
				+"<td>"+course.type.name+"</td>"
				+"<td><input type='button' value='Alter' onclick=\"alterCourseform(this)\"/></td>"
				+"<td><input type='button' value='Delete' onclick=\"deleteConfirm(this)\"/></td></tr>"
			);
		};
		
		deleteConfirm = function(bn){
			TR = $(bn).parents('tr') ;
			$( "#delete-confirm" ).dialog("open");
		};
		
		
		$(function(){
			//init the page data
			create_course_name = $('#create-course-name');
			alter_course_name = $('#alter-course-name');
			validtips = $(".validateTips");
			allFields = $([]).addClass(create_course_name).addClass(alter_course_name);
			
			
			findAllCourse() ;
			
			function updateTips(text){
				validtips
					.text(text)
					.addClass("ui-state-highlight");
					setTimeout(function() {
						validtips.removeClass( "ui-state-highlight", 1500 );
					}, 500 );
			}
		
		
			function updateTips(text){
				validtips
					.text(text)
					.addClass("ui-state-highlight");
					setTimeout(function() {
						validtips.removeClass( "ui-state-highlight", 1500 );
					}, 500 );
			}
		
			// Init the create course form
			$("#create-course-form").dialog({
				autoOpen: false,
				height: 300,
				width: 350,
				modal: true,
				buttons:{
					"Create a course" : function(){
						allFields.removeClass( "ui-state-error" );
						if(create_course_name.val() == null || create_course_name.val() == ""){
							create_course_name.addClass("ui-state-error");
							updateTips("name can't be empty!") ;
						}else{
							createCourse() ;
						}
					},
					Cancel: function() {
						$( this ).dialog( "close" );
					}
				},
				close: function() {
					allFields.val( "" ).removeClass( "ui-state-error" );
				}	
			});
			

			
			//Init the alter form
			$('#alter-form').dialog({
				autoOpen: false,
				height: 300,
				width: 350,
				modal: true,
				buttons:{
					"submit the new course" : function(){
						if(alter_course_name.val() == null || alter_course_name.val() == ""
							|| alter_course_name.val() == old_course_name){
							alter_course_name.addClass("ui-state-error");
							updateTips("name can't be empty or without altering") ;
						}else{
							alterCourse() ;
						}
					},
					Cancel: function() {
						$( this ).dialog( "close" );
					}
				},
				close: function() {
					allFields.val( "" ).removeClass( "ui-state-error" );
				}
			});
			
			$( "#delete-confirm" ).dialog({
				autoOpen : false,
				resizable: false,
				height:140,
				modal: true,
				buttons: {
					"Delete Course": function() {
						deleteCourse() ;
						$( this ).dialog( "close" );
					},
						Cancel: function() {
							$( this ).dialog( "close" );
						}
					},
			});
			
			$("#create-course")
				.button()
				.click(function(){
					$("#create-course-form").dialog("open") ;
				});
			

				
			
			
		});

		
	</script>
	

  </head>
  
  <body>
  	
  	<div id="create-course-form" title="Create new course">
  		<p class="validateTips">form field is required.</p>
  		<form>
  			<fieldset>
  				<label for="create-course-name">Name</label>
  				<input type="text" name="course-name" id="create-course-name"class="text ui-widget-content ui-corner-all" />
  				<label for="type">Course type</label>
  				<select id="create-course-type-choose">
  					<option value="obligatory">Obligatory</option>
  					<option value="elective">Elective</option>
  				</select>
  			</fieldset>
  		</form>
  	</div>
  	
  	<div id="alter-form" title="Alter course">
		<p class="validateTips">form field is required</p>
  		<form>
  			<label for="alter-course-name">Name</label>
  			<input type="text" name="course-name" id="alter-course-name"class="text ui-widget-content ui-corner-all" />
  			<select id="alter-course-type-choose">
  					<option value="obligatory">Obligatory</option>
  					<option value="elective">Elective</option>
  			</select>
  		</form>
  	</div>
  	
  	<div id="delete-confirm" title="Delete Course?">
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
			The Course will be deleted. Are you sure?
		</p>
	</div>
  	
    <div id="courses-contain" class="ui-widget">
	<h1>Existing Courses:</h1>
		<table id="courses" class="ui-widget ui-widget-content">
			<thead>
				<tr class="ui-widget-header ">
					<th>Name</th>
					<th>Type</th>
					<th>Alter Name</th>
					<th>Delete Name</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div><br>
	<button id="create-course">Create new course</button>
	
  </body>
</html>
