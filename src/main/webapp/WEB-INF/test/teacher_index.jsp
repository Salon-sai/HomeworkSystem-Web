<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Teacher Index</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style>
		body { font-size: 65%; }
		fieldset { padding:0; border:0; margin-top:25px; }
		input.text { margin-bottom:12px; width:95%; padding: .4em; }
		label, input { display:block; }
		h1 { font-size: 1.2em; margin: .6em 0; }
		/* .div-contain { width: 350px; margin: 20px 0; }
		.div-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
		.div-contain table td,.div-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; } */
		.ui-dialog .ui-state-error { padding: .3em; }
		.validateTips { border: 1px solid transparent; padding: 0.3em; }
	</style>
	<link rel="stylesheet" type="text/css" href="themes/base/jquery.ui.all.css">
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jquery-ui.js"></script>

	<script src="js/ui/jquery.ui.core.js"></script>
	<script src="js/ui/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="js/ui/jquery.ui.slider.js"></script>
	<script src="js/ui/jquery.ui.accordion.js"></script>
	<script src="js/ui/jquery.ui.button.js"></script>	
	
	<script src="js/ui/jquery.ui.mouse.js"></script>
	<script src="js/ui/jquery.ui.draggable.js"></script>
	<script src="js/ui/jquery.ui.position.js"></script>
	<script src="js/ui/jquery.ui.resizable.js"></script>
	<script src="js/ui/jquery.ui.dialog.js"></script>
	<script src="js/ui/jquery.ui.effect.js"></script>
	
	<script type="text/javascript" src="js/FlexPaper_js/flexpaper.js"></script>
	<script type="text/javascript" src="js/FlexPaper_js/flexpaper_handlers.js"></script>
	
	<script type="text/javascript" src="js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="js/project2_js/document_opeare.js"></script>
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script type="text/javascript">
		var select_course,
			students_contain,
			select_class,
			uploadDocument,
			tips,
			documentId ;
		
		
		$(function(){
			select_course = $('#courses');
			students_contain = $('#students-contain');
			select_class = $('#classes') ;
			uploadDocument = $('#uploadFile') ;
			tips = $('.validateTips') ;
			
			var icons = {
				header: "ui-icon-circle-arrow-e",
				activeHeader: "ui-icon-circle-arrow-s"
			};
			
			$( "#choose-list" ).accordion({
				icons: icons
			});
			
			select_course.change(function(){
				if($(this).val() != " "){
					getTeachClassByCourse();
					getTopicByCourse() ;
					findHomeworkListByCourse($(this).val()) ;				
				}
			});
			
			select_class.change(function(){
				if($(this).val() != " "){
					getStudents($(this).val()) ;
					findHomeworksListByCourseAndClass($(this).val()) ;
				}
			});
			
			$('#upload-topic').dialog({
				autoOpen: false,
				height: 300,
				width: 350,
				modal: true,
				buttons: {
					"Upload File": function(){
						var validate = true ;
						validate = validate&&checkuploadFileSuffix(uploadDocument,/.*\b\.doc[x]?\b|.*\b\.ppt[x]?$|.*\b\.xls[x]?$/);
						validate = validate&&isSelectedCourse();
						validate = validate&&isSelectedClass() ;
						if(validate){
							ajaxUploadFile('uploadFile','uploadTopic.action',{courseName : select_course.val()}) ;
							$(this).dialog('close');
						} ;
					}
				}
			});
			
			$('#upload-topic-button')
				.button()
				.click(function(){
					$('#upload-topic').dialog('open');
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
			
			$('#delete-confirm').dialog({
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
			
			setDocumentView('#documentView','../SWF/Paper.pdf.swf') ;
			
			$("#score-slider").slider({
				range : "min",
				min : 0,
				max : 100,
				value : 59,
				slide : function(event, ui){
					$("#score").val(ui.value);
				}
			});
			
			$("#score").val($("#score-slider").slider("value")) ;
			
			$('#score').blur(function(){
				$('#score-slider').slider("value",$(this).val()) ;
			});
		});
		
		getCoursesByTeacher = function(){
			$.post('getCourseByTeacher.action',{},
					function(returnData,status){
						$.each(returnData.courses,function(index,course){
							addCoursesHtml(course);
						});
					});
		};
		
		getTeachClassByCourse = function(){
			$.post('getInformationByCourse.action',
					{courseName : select_course.val()},
					function(returnData,status){
						$('#classes').html("<option value=' '></option>");
						$.each(returnData.informations,function(index,information){
							addClassHtml(information);
						});
					});
		};
		
		getStudents = function(clazzName){
			$.post("getStudentByClazz.action",
					{informationName : clazzName},
					function(returnData,status){
						$('#student-contain tbody').html("") ;
						$.each(returnData.users,function(InfoIndex,user){
							addStudentHtml(user) ;
						});
					});
		};
		
		getTopicByCourse = function(){
			$.post('findTopicByCourseAndTeacher.action',
					{courseName : select_course.val()},
					function(returnData,status){
						if(returnData.documents != null){
							$('#topices tbody').html('');
								$.each(returnData.documents,function(index,document){
								$('#topices tbody').append(addTopicHtmlByteacher(document));
							});
						}
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
		
		findHomeworkListByTopic = function(document_id){
			$.post('findHomeworksByTopicAndClass.action',
					{document_id : document_id,informationName : select_class.val()},
					function(returnData,status){
						callBackHomework(returnData) ;
					});
		};
		
		findHomeworksListByCourseAndClass = function(informationName){
			$.post('getHomeworkByInformationAndCourse.action',
					{courseName : select_course.val() ,informationName : informationName},
					function(returnData,status){
						callBackHomework(returnData) ;
					});
		};
		
		findHomeworkListByCourse = function(courseName){
			$.post('getHomeworkdByCourseOfTeacher.action',
					{courseName : courseName},
					function(returnData, status){
						callBackHomework(returnData) ;
					});
		};
		
		submitScore = function(){
 			if(documentId != null){
				$.post('submitScore.action',
						{document_id : documentId, score : $('#score').val()},
						function(returnData,status){
							$("[onclick=\"showSWF('"+documentId+"')\"]").parents('tr').remove();
							$('#students tbody').append(addHomeworkHtmlByteacher(returnData.document));
						});
			} 
		};
		
		AjaxdeleteTopic = function(bn){
			$.post('deleteDocument.action',
					{document_id : documentId},
					function(returnData, status){
						$(bn).parents('tr').remove();
					});
		};
		
		callBackHomework = function(returnData){
			$('#students tbody').html('');
				if(returnData.documents != null){
					$.each(returnData.documents,function(index,document){
						$('#students tbody').append(addHomeworkHtmlByteacher(document)) ;
					});
				}
		};
		
		addCoursesHtml = function(course){
			$('#courses').append("<option value='"+course.name+"'>"+course.name+"</option>");
		};
		
		addClassHtml = function(clazz){
			$('#classes').append("<option value='"+clazz.name+"'>"+clazz.name+"</option>");
		};
		
		addStudentHtml = function(student){
			$('#student-contain tbody').append("<tr>"
				+"<td>"+student.name+"</td>"
				+"<td>"+student.idNum+"</td></tr>"
			);
		} ;
		
		checkuploadFileSuffix = function(object,reg){
			if(!(reg.test(object.val()))){
				updateTips('Does not comply with the format!!') ;
				return false ;
			}
			return true;
		};
		
		isSelectedCourse = function(){
			if(select_course.val() == ' '){
				updateTips('please choose the course!!') ;
				return false ;
			}
			return true ;
		};
		
		isSelectedClass = function(){
			if(select_class.val() == ' '){
				updateTips('please choose the class!!');
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
		



	</script>
	
  </head>
  
  <body onload="getCoursesByTeacher();">
  	<div id="choose-list" style="width: 250;float: left;">
  		<h3>Topic</h3>
  		<div>
  			
  		</div>
  		<h3>Homework</h3>
  		<div>
  			<p>Correct Homework</p>
  			<p>Check Score of Homework</p>
  		</div>
  	</div>
  
    <div id="teacher-information" style="float: left;">
    	<h2>Hello Teacher Name : 
    		<span title="teacherName">
    			${sessionScope.teacher.name }&nbsp;&nbsp;&nbsp;
    		</span>
    	</h2>
    </div>
    
    <div style="float: left;">
    	<h1>Taught Courses : </h1><select id="courses"><option value=' '></option></select>
    </div>
    
    <div style="float: left;">
    	<h1>Class of Courses : </h1><select id="classes"><option value=' '></option></select>
    </div>
    
    <!-- upload document form  -->
    <div id="upload-topic" title="Upload Topic With Course">
    	<p class="validateTips">Upload Topic</p>
    	<label for="file">File Name : </label>
    	<input type="file" name="uploadFile" id="uploadFile"/>
    </div>
    
    <div id="conversion-confirm" title="Please Conversion The Document">
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
			If you see the document in the first time,you should be conversed.
			 Are you sure to converse the document?
		</p>
    </div>
    
    <div id="conversion-loading" title="Loading......">
    	<p>
	    	<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
	    	Loading....
	    </p>
    </div>
    
    <div id="delete-confirm" title="Delete">
   		<p>
   			<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
   			The Document will be deleted.Are you sure ?
   		</p>
    </div>
    
    <div id="homeworks-contain" style="float: left;" class="ui-widget div-contain">
    	<h1>The Homeworks of Students <span title="className"></span></h1>
    	<table id="students" class="ui-widget ui-widget-content">
    		<thead>
    			<tr class="ui-widget-header">
    				<td>Homework Name</td>
    				<td>Name</td>
    				<td>Number</td>
    				<td>Score</td>
    				<td>Look Document</td>
    				<td>Dowload Homework</td>
    			</tr>
    		</thead>
    		<tbody></tbody>
    	</table>
    </div>
  	
  	<div id="document-contain" style="float: left;" class="ui-widget div-contain">
  		<h1>Your Topices List</h1>
  		<table id="topices" class="ui-widget ui-widget-content">
  			<thead>
  				<tr class="ui-widget-header">
  					<td>Name</td>
  					<td>Delete File</td>
  					<td>Show File</td>
  					<td>About homeworks</td>
  					<td>Download File</td>
  				</tr>
  			</thead>
  			<tbody></tbody>
  		</table>
  		<button id="upload-topic-button">Upload Topic</button>
  	</div>
  	
  	<div>
	 	<div id="documentView" class="flexpaper_viewer"
	  			style="width: 800px;height: 500px;float:left"></div>
	  	<div id="score_opeare" style="float:left">
			<p>
				<label for="score">Score:</label>
				<input type="text" id="score" style="border:0;
				color:#f6931f; font-weight:bold" />
			</p>
			<div id="score-slider" style="width:200px"></div>
			<input type="button" value="submit score" onclick="submitScore();"/>
		</div>
  	</div>
  	
  </body>
</html>