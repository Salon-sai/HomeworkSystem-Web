<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Student Index</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style>
		body { font-size: 65%; }
		fieldset { padding:0; border:0; margin-top:25px; }
		label, input { display:block; }
		h1 { font-size: 1.2em; margin: .6em 0; }
		.ui-dialog .ui-state-error { padding: .3em; }
		.validateTips { border: 1px solid transparent; padding: 0.3em; }
		.ui-menu { width: 150px; height: 150px}
	</style>
	<link rel="stylesheet" type="text/css" href="themes/base/jquery.ui.all.css">
	
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jquery-ui.js"></script>

	<script src="js/ui/jquery.ui.core.js"></script>
	<script src="js/ui/jquery.ui.widget.js"></script>
	<script src="js/ui/jquery.ui.accordion.js"></script>
	<script src="js/ui/jquery.ui.button.js"></script>
	
	<script src="js/ui/jquery.ui.mouse.js"></script>
	<script src="js/ui/jquery.ui.draggable.js"></script>
	<script src="js/ui/jquery.ui.position.js"></script>
	<script src="js/ui/jquery.ui.resizable.js"></script>
	<script src="js/ui/jquery.ui.dialog.js"></script>
	<script src="js/ui/jquery.ui.effect.js"></script>
	
	<script src="js/ui/jquery.ui.position.js"></script>
	<script src="js/ui/jquery.ui.menu.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script type="text/javascript" src="js/FlexPaper_js/flexpaper.js"></script>
	<script type="text/javascript" src="js/FlexPaper_js/flexpaper_handlers.js"></script>
	<script type="text/javascript" src="js/ajaxfileupload.js"></script>
	
	<script type="text/javascript" src="js/project2_js/document_opeare.js"></script>
	
	<script type="text/javascript">
		var user_course,
			course_teacher,
			documentId ;
			
		
		$(function() {
			user_course = $('#user-courses') ;
			course_teacher = $('#course-teachers');
			
			$('#upload-homework-form')
				.button()
				.click(function(){
					$('#upload-dialog').dialog('open');
				});
				
			$( "#menu" ).menu();
			
			user_course.change(function(){
				course_teacher.html("<option value=' '></option>");
				$('#upload-homework-course').val('');
				if($(this).val() != ' '){
					getTeacherByCourse($(this).val());
					$('#upload-homework-course').val($(this).val());
					getHomeworkByCourse($(this).val());
				}
			});
			
			course_teacher.change(function(){
				$('#topics tbody').html("");
				$('#upload-homework-topic').html('');
				if($(this).val() != ' '){
					getTopicByTeacherandCourse($(this).val());
				}
			});
			
			$('#conversion-confirm').dialog({
				autoOpen : false,
				resizable : false,
				height : 140,
				modal : true,
				buttons : {
					"Conversion" : function(){
						Conversion(documentId);
					},
					Cancel : function(){
						$(this).dialog('close');
					}
				}
			});
			
			$('#conversion-loading').dialog({
				autoOpen : false,
				resizable : false,
				height : 140,
				modal : true,
				open: function(){$("#conversion-loading .ui-dialog-titlebar-close").hide();},
				buttions : {}
			}).ajaxStart(function(){
				$('#conversion-loading').dialog('open');
			}).ajaxStop(function(){
				$('#conversion-loading').dialog('close');
			});
			
			$('#upload-dialog').dialog({
				autoOpen : false,
				resizable : false,
				height: 250,
				width: 350,
				modal : true,
				buttons : {
					"Upload" : function(){
						var upload_homework_topic = $('#upload-homework-topic').val();
						var courseName = $('#upload-homework-course').val();
						var validate = (upload_homework_topic != null) && (upload_homework_topic != '') ;
						validate = validate&&(courseName != ' ')&&(courseName != null)&&(courseName != ''); 
						if(validate){
							ajaxUploadFile('uploadFile','uploadHomework.action'
								,{courseName : courseName,document_id: upload_homework_topic});
							$(this).dialog('close');
						}
					},
					Cancel: function() {
						$( this ).dialog( "close" );
					}
				}				
			});
			
			setDocumentView('#documentView','');
			getCourses();
		});
		
		getCourses = function(){
			$.post('courseOperate/getCourseByStudent.action',{},
					function(returnData, status){
						$.each(returnData.courses,function(index,course){
							user_course.append(addSelectHtml(course)) ;
						});
					});
		};
		
		getTeacherByCourse = function(courseName){
			$.post('userOperate/getTeachersByCourse.action',
					{courseName : courseName},
					function(returnData,status){
						$.each(returnData.users,function(index,teacher){
							course_teacher.append(addSelectHtml(teacher));
						});
					});
		};
		
		getTopicByTeacherandCourse = function(teacherName){
			$.post('findTopicByCourseAndTeacher.action',
					{teacherName : teacherName,courseName : user_course.val()},
					function(returnData,status){
						if(returnData.documents != null) {
							$.each(returnData.documents,function(index,document){
								$('#topics tbody').append(addDocumentStudentTableHtml(document)) ;
								$('#upload-homework-topic').append(addSelectWithIdHtml(document));
							});
						}
					});
		};
		
		getHomeworkByCourse = function(courseName){
			$.post('gethomeworksByCourseAndStudent.action',
					{courseName : courseName},
					function(returnData,status){
						$('#document tbody').html('');
						$.each(returnData.documents,function(index,document){
							$('#homeworks tbody').append(addHomeworkStudentTableHtml(document));
						});
					});
		};
		
		getOwnHomeworkByTopic = function(document_id){
			$.post('findHomeworksByTopicAndStudent.action',
					{document_id : document_id},
					function(returnData, status){
						$('#homeworks tbody').html('');
						$.each(returnData.documents,function(index,document){
							$('#homeworks tbody').append(addHomeworkStudentTableHtml(document));
						});
					});
		};
		
		showSWF = function(document_id){
			documentId = document_id ;
			$.post('getDocumentView.action',
				{document_id : document_id},
				function(returnData,status){
					if(returnData.SWF_path == null){
						$('#conversion-confirm').dialog('open');
					}else{
						setDocumentView('#documentView',returnData.SWF_path);
						documentId = null ;
					}
				});
		};
		
		Conversion = function(document_id){
 			$.post('conversionDocument.action',
					{document_id : document_id},
					function(returnData,status){
						if(status == 'success'){
							setDocumentView('#documentView',returnData.SWF_path);
							$('#conversion-confirm').dialog('close');
						}
					}); 
		};
		
		addSelectHtml = function(option){
			return "<option value='"+option.name+"'>"+option.name+"</option>";
		} ;
		
		addSelectWithIdHtml = function(option){
			return "<option value='"+option.id+"'>"+option.name+"</option>";
		};
		
	</script>
	
	
  </head>
  
  <body onload="">
	<ul id="menu" style="float: left;">
		<li><a >Upload Or Look Topic</a></li>
		<li><a >My Homework</a></li>
	</ul>
  	<div id="student-information">
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
  	
   	<div id="documentView" class="flexpaper_viewer"
		style="width: 800px;height: 500px;float:left">
	</div>
	<br>
	<div id="courses" style="float: left;">
		<h3 style="">Your Courses : </h3><select id="user-courses"><option value=' '></option></select>
	</div><br><br>
	<div id="topic" style="">
		<h3 style="">Your Teacher : </h3><select id="course-teachers"><option value=' '></option></select>
	</div><br><br>
	
	<!-- dialog html -->
    <div id="conversion-confirm" title="Please Conversion The Document">
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
			If you see the document in the first time,you should be conversed.
			 Are you sure to converse the document?
		</p>
    </div>
    
    <div id="conversion-loading" title="Loading......">
    	<p>
	    	<span class="ui-icon ui-icon-alert"></span>
	    	Loading....
	    </p>
    </div>
	
	<div id="upload-dialog" title="Upload Homework">
		<p class="validateTips">Upload Dialog	</p>
		<form>
			<fieldset>
				<label for="Course">Choose Course</label>
				<input type="text" id="upload-homework-course" disabled="disabled"/>
				<label for="topic">Choose Topic</label>
				<select id="upload-homework-topic"><option value=' '></option></select>
				<label for="upload-file">Upload File</label>
				<input type="file" name="uploadFile" id="uploadFile"/>
			</fieldset>
		</form>
	</div>
	
	<!-- dialog html end -->
	
	<div id="topics-contain" class="ui-widget">
		<h1>Course of Topics : <span title="CourseName"></span></h1>
  		<table id="topics" class="ui-widget ui-widget-content">
  			<thead>
  				<tr class="ui-widget-header ">
  					<td>Topic Name</td>
  					<td>Look Topic</td>
  					<td>Download Topic</td>
  					<td>Look My homework</td>
  				</tr>
  			</thead>
  			<tbody></tbody>
  		</table>
	</div>
	
	<button id="upload-homework-form">Upload Homework</button>	
	
	<div id="homeworks-contain" class="ui-widget">
		<h1>Your Homeworks of Topics :<span title="CourseName"></span></h1>
  		<table id="homeworks" class="ui-widget ui-widget-content">
  			<thead>
  				<tr class="ui-widget-header ">
  					<td>Homework Name</td>
  					<td>Look Homework</td>
  					<td>Score</td>
  					<td>Delete Homework</td>
  					<td>Download Homework</td>
  				</tr>
  			</thead>
  			<tbody></tbody>
  		</table>
	</div>
  </body>
</html>
