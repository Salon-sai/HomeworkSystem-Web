<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test_InformationTree.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="themes/base/jquery.ui.all.css">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style>
		body { font-size: 62.5%; }
		label, input { display:block; }
		input.text { margin-bottom:12px; width:95%; padding: .4em; }
		fieldset { padding:0; border:0; margin-top:25px; }
		h1 { font-size: 1.2em; margin: .6em 0; }
		div#students-contain { width: 350px; margin: 20px 0; }
		div#students-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
		div#students-contain table td, div#students-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
		.ui-dialog .ui-state-error { padding: .3em; }
		.validateTips { border: 1px solid transparent; padding: 0.3em; }
	</style>
	<link rel="stylesheet" type="text/css" href="themes/base/jquery-ui.css">
	
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jquery-ui.js"></script>
	
	<script type="text/javascript" src="js/jquery.cookie.js"></script>
	
	<link rel="stylesheet" type="text/css" href="js/ui-tree/skin-vista/ui.dynatree.css">	
	<script type="text/javascript" src="js/ui-tree/jquery.dynatree.js"></script>

	<script type="text/javascript" src="js/ui-tree/contextmenu/jquery.contextMenu-custom.js"></script>
	<link href="js/ui-tree/contextmenu/jquery.contextMenu.css" rel="stylesheet" type="text/css" >
	
	<script type="text/javascript" src="js/ui/jquery.ui.position.js"></script>
	
	<script type="text/javascript" src="js/project2_js/Menu.js"></script>
	<script type="text/javascript" src="js/project2_js/treeList.js"></script>
	
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
	
	<script type="text/javascript">
		var create_student_name,
				create_student_number,
				allFields,
				tips,
				not_choose_course,
				create_teacher_number,
				create_teacher_name; // text of the create-teacher-form		
		
		// load the html begin
		$(function(){
			create_student_name=$('#create-student-name');
			create_student_number = $('#create-student-number') ;
			allFields = $([]).add(create_student_name).add(create_student_number) ;
			tips = $( ".validateTips" ) ;
			not_choose_course = $('#not-choose-course') ;
			create_teacher_name = $('#create-teacher-name');
			create_teacher_number = $('#create-teacher-number');
			
			
			getAllDepartments();
			
			$('#create-student')
				.button()
				.click(function(){
					$("#create-form").dialog("open") ;
				});
				
			$('#add-course')
				.button()
				.click(function(){
						addCourseToClass() ;
				});
			
			$('#create-form').dialog({
				autoOpen: false,
				height: 300,
				width: 350,
				modal: true,
				buttons: {
					"Create Student" : function(){
						var validate = true;
						validate = validate & checkNumber(create_student_number,/\d{9}/);
						if(validate && create_student_name.val() != null && create_student_name.val() != ""){
							if($("[title='className']").text() == null || $("[title='className']").text() ==""){
								updateTips("please choose the class") ;
							}else{
								createStudent() ;
								$( this ).dialog( "close" );
							}
						}else{
							updateTips('student name should not be empty!!');
							create_student_name.addClass("ui-state-error");
						}
					},
					Cancel: function() {
						$( this ).dialog( "close" );
					}
				},
				close: function() {
					allFileds.val( "" ).removeClass( "ui-state-error" );
				}
			});
			
			// Init the create teacher form
			$("#create-teacher-form").dialog({
				autoOpen: false,
				height: 300,
				width: 350,
				modal: true,
				buttons:{
					"Create a teacher" : function(){
						var validate = true;
						allFields.removeClass( "ui-state-error" );
						validate = validate & checkNumber(create_teacher_number,/\d{9}/);
						if(create_teacher_name.val() == null || create_teacher_name.val() == ""){
							create_teacher_name.addClass("ui-state-error");
							updateTips("name can't be empty!") ;
						}else{
							createTeacher() ;
							$( this ).dialog( "close" );
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
			
			$('#create-teacher')
				.button()
				.click(function(){
					$("#create-teacher-form").dialog("open");
				});
			
		});
		
		//load the html end
		
		getAllDepartments = function(){
			$.post("informationOperate/getDepartments.action",{},
					function(returnData,stauts){
						InstructTree(returnData) ;
					});
		};
		
		createStudent = function() {
			$.post("userOperate/saveUser.action",
					{userName : create_student_name.val(),IDNum : create_student_number.val(),
					informationName : $("[title='className']").eq(0).text(),typeName : 'student'},
					function(returnData,status){
						if(status == "success"){
							addStudentHtml(returnData.user) ;
						}
					});
		} ;
		
		getStudents = function(clazzName){
			$.post("userOperate/getStudentByClazz.action",
					{informationName : clazzName},
					function(returnData,status){
						$('#student-contain tbody').html("") ;
						$.each(returnData.users,function(InfoIndex,user){
							addStudentHtml(user) ;
						});
					});
		};
		
		checkNumber = function(object,reg){
			if(object.val()==null || object.val()==""){//wheather number is empty or not
				object.addClass("ui-state-error") ;
				updateTips("number can not be empty") ;
				return false;
			}
			if(object.val().length != 9 || !(reg.test(object.val()))){
				updateTips("the length of number should be 9") ;
				object.addClass("ui-state-error");
				return false ;
			}
			return true ;
		};
		
		function updateTips( t ) {
			tips
				.text( t )
				.addClass( "ui-state-highlight" );
			setTimeout(function() {
				tips.removeClass( "ui-state-highlight", 1500 );
			}, 500 );
		}
		
		courseByClass = function(clazzName){
			$.post("courseOperate/notChooseCourse.action",
				{InformationName : clazzName},
				function(returnData,status){
					$('#not-choose-course').html('');
					$('#course-contain tbody').html('');
					$.each(returnData.courses,function(InfoIndex,course){
						addNotchooseCourseHtml(course);
					});
					$.each(returnData.chosenCourses,function(InfoIndex,course){
						addchosenCourse(course) ;
					});
				});
		};
		
		addCourseToClass = function(){
			$.post("courseOperate/addCourseToClass.action",
				{InformationName : $("[title='className']").eq(0).text(),courseName : not_choose_course.val()},
				function(returnData, status){
					removeNotchooseCourseHtml(returnData.course.name);
					addchosenCourse(returnData.course) ;
				});
		};
		
		//post teacher 
		createTeacher = function(){
			$.post('userOperate/saveUser.action',
				{userName : create_teacher_name.val(),IDNum : create_teacher_number.val(),typeName : 'teacher'},
				function(returnData, status){
					addTeacherHtml(returnData.user) ;
				});
		} ;
		
		addStudentHtml = function(student){
			$('#student-contain tbody').append("<tr>"
				+"<td>"+student.name+"</td>"
				+"<td>"+student.idNum+"</td>"
				+"<td><input type='button' value='Alter Student' onclick='alterStudent(this)'/></td>"
				+"<td><input type='button' value='Delete Student' onclick='deleteStudent(this)'/></td></tr>"
			);
		} ;
		
		addTeacherHtml = function(teacher){
			$('#teachers-contain tbody').append("<tr>"
				+"<td>"+teacher.name+"</td>"
				+"<td>"+teacher.idNum+"</td>"
				+"<td><input type='button' value='Alter Teacher' onclick='alterTeacher(this)'/></td>"
				+"<td><input type='button' value='Delete Teacher' onclikc='deleteTeacher(this)'/></td></tr>");
		};
		
		addNotchooseCourseHtml = function(course){
			$('#not-choose-course').append("<option value='"+course.name+"'>"+course.name+"</option>");
		} ;
		
		addchosenCourse = function(course){
			$('#course-contain tbody').append("<tr>"
				+"<td>"+course.name+"</td>"
				+"<td><input type='button' value='Delete' onclick=''/></td></tr>");
		} ;
		
		removeNotchooseCourseHtml = function(courseName){
			$.each($('#not-choose-course option'),function(index,option){
				if($(option).text() == courseName)
					$(option).remove() ;
			});
		};
		
	</script>
	
  </head>
  
  <body>
	    <ul id="menu" class="contextMenu">
	    	<li class="edit"><a href="#edit">Edit</a></li>
	    	<li class="delete"><a href="#delete">Delete</a></li>
	    	<li class="paste"><a href="#add">Add</a></li>
	    	<li class="quit separator"><a href="#quit">Quit</a></li>
	    </ul>
	    
	    <div id="tree" style="width: 200px;height: 300px">
	    <ul></ul>
	    </div>
    
		<div id="student-contain" class="ui-widget">
			<h1>Existing Student In : <span title="className"></span></h1>
			<table id="students" class="ui-widget ui-widget-content">
				<thead>
					<tr class="ui-widget-header">
						<th>Name</th>
						<th>Number</th>
						<th>Alter Name</th>
						<th>Delete Name</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
			<br>
			<button id="create-student">Create new Student</button>
		</div>
	  	
	  	<div id="course-contain" class="ui-widget">
	  		<h1>Existing Course In : <span title="className"></span></h1>
	  		<table id="courses" class="ui-widget ui-widget-content">
	  			<thead>
	  				<tr class="ui-widget-header">
	  					<th>Name</th>
	  					<th>Delete</th>
	  				</tr>
	  			</thead>
	  			<tbody></tbody>
	  		</table>
	  	</div>
	  	
  	
	 	<div id="create-form" title="Create A Student">
	 		<p class="validateTips">form All field are required.</p>
	 		<form>
	 			<fieldset>
	 				<label for="create-course-name">Name</label>
	 				<input type="text" name="course-name" id="create-student-name"class="text ui-widget-content ui-corner-all" />
	 				<label for="type">Number</label>
	 				<input type="text" name="course-number" id="create-student-number"class="text ui-widget-content ui-corner-all" />
	 			</fieldset>
	 		</form>
	 	</div>
	  	
	  	<div id="create-teacher-form" title="Create A Teacher">
	  		<p class="validateTips">form field is required.</p>
	  		<form>
	  			<fieldset>
	  				<label for="create-teacher-name">Name</label>
	  				<input type="text" name="teacher-name" id="create-teacher-name"class="text ui-widget-content ui-corner-all" />
	  				<label for="create-teacher-name">Number</label>
	  				<input type="text" name="teacher-number" id="create-teacher-number"class="text ui-widget-content ui-corner-all" />
	  			</fieldset>
	  		</form>
	  	</div>
	  	
	  	<select id="not-choose-course"></select><button id="add-course">Add Course to Class</button>
	  
	  	<div id="teachers-contain" class="ui-widget">
			<h1>Existing Teacher : </h1>
			<table id="teachers" class="ui-widget ui-widget-content">
				<thead>
					<tr class="ui-widget-header">
						<th>Name</th>
						<th>Alter Name</th>
						<th>Delete Name</th>
					</tr>
				</thead>
			</table>
		</div>
		<button id="create-teacher">Create Teacher</button>
	  
	  </body>
</html>
