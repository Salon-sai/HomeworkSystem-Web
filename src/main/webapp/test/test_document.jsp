<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test_document.jsp' starting page</title>
    
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
	<script type="text/javascript" src="js/ui/jquery.ui.mouse.js"></script>
	<script type="text/javascript" src="js/ui/jquery.ui.slider.js"></script>
	
	<script type="text/javascript" src="js/FlexPaper_js/flexpaper.js"></script>
	<script type="text/javascript" src="js/FlexPaper_js/flexpaper_handlers.js"></script>
	<script type="text/javascript" src="js/ajaxfileupload.js"></script>
	
	<script type="text/javascript" src="js/project2_js/document_opeare.js"></script>
	
  	<script type="text/javascript">

		showSWF = function(document_id){
			$.post('getDocumentView.action',{document_id : document_id},
					function(returnData,status){
						if(status == 'success'){
							setDocumentView('#documentView',returnData.SWF_path) ;
							$('#score_opeare').find(':hidden').val(returnData.document_id) ;
							if(null != returnData.score){
								$('#score').val(returnData.score) ;
								$('#score-slider').slider("value",returnData.score) ;
							}
						}
					});
		};
		
		uploadfile = function(){
			ajaxUploadFile('uploadFile','uploadDocument.action') ;
		};
  		
  		getconverteddocuments = function(){
  			$.post('getconvertedDocuements.action',{},
  					function(returnData,status){
  						if(status=='success'){
  							for(var i=0;i < returnData.convertedDocuments.length;i++){
  								$('#converteddocumentList').append(addHtmldocument(returnData.convertedDocuments[i]));
  							}
  						}
  					});
  		};
  		
  		getnotconvertdocuments = function(){
  			$.post('getnotconvertdocuments.action',{},
  					function(returnData,status){
  						if(status == 'success'){
  							for(var i = 0; i < returnData.notConvertDocuments.length;i++){
  								$('#noconvertdocumentList').append(addHtmldocument(returnData.notConvertDocuments[i])) ;
  							}
  						}
  					});
  		};
	
		deleteDocument = function(document_id){
			$.post('deleteDocument.action',{document_id : document_id},
					function(returnData, status){
						if(status=='success'){
							alert(status) ;
						}
					});
		};
		
		conversion = function(document_id){
			$.post('conversionDocument.action',{document_id : document_id},
					function(returnData,status){
						if(status == 'success'){
							alert(status) ;
						}
					});
		};
		
		submitScore = function(){
			$.post('submitScore.action',
					{document_id : $('#documentId').val(), score : $('#score').val()},
					function(returnData,status){
						alert(status) ;
					});
		};
		
		scoredDocument = function(){
			$.post('getScoredHomework.action',{},
					function(returnData,status){
						  	for(var i = 0; i < returnData.scoredDocuments.length;i++){
  								$('#scoredDocumentsList').append(addHtmldocument(returnData.scoredDocuments[i])) ;
  							}
					}) ;
		};
		
		$(function(){
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
			
			getconverteddocuments();
			getnotconvertdocuments();
			scoredDocument();
		}) ;
		

		
  </script>
  </head>
  
  <body onload="">
   		<div id="documentView" class="flexpaper_viewer"
   			style="width: 800px;height: 500px;float:left">
   		</div>
   		<div id="score_opeare" style="float:left">
   			<p>
	  			<label for="score">Score:</label>
	  			<input type="text" id="score" style="border:0;
	  			color:#f6931f; font-weight:bold" />
	  		</p>
	  		<input type="hidden" id="documentId"/>
	  		<div id="score-slider" style="width:200px"></div>
	  		<input type="button" value="submit score" onclick="submitScore();"/>
   		</div>
   		
   		upload File : <input type="file" id="uploadFile" name="uploadFile"/>
   		<input type="button" value="upload" onclick="uploadfile();"/>
   		<br><br>
   		<div id="documentopeare">
   			<h3>Converted Document</h3>
   			<table id="converteddocumentList" border="1">
   				<tbody>
	   				<tr>
	   					<td>document name</td>
	   					<!--  
	   					<td>document source path</td>
	   					<td>document pdf path</td>
	   					<td>document swf path</td>
	   					 -->
	   					<td>delete File</td>
	   					<td>show the office file</td>
	   					<td>download file</td>
	   				</tr>
   				</tbody>
   			</table>
   			
   			<h3>need to convert document</h3>
   			<table id="noconvertdocumentList" border="1">
   				<tbody>
   					<tr>
   						<td>document name</td>
   						<!--  
	   					<td>document source path</td>
	   					<td>document pdf path</td>
	   					<td>document swf path</td>
	   					-->
	   					<td>delete File</td>
	   					<td>conversion</td>
	   					<td>download file</td>
   					</tr>
   				</tbody>
   			</table>
   			
   			<h3>score document</h3>
   			<table id="scoredDocumentsList" border="1">
   				<tbody>
   					<tr>
   						<td>document name</td>
   						<td>delete File</td>
   						<td>show the office file</td>
   						<td>File score</td>
   						<td>download File</td>
   					</tr>
   				</tbody>
   			</table>
   		</div>
   		
   		
		
  </body>
</html>
