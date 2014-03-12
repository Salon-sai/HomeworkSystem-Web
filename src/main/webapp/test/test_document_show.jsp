<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test_document_show.jsp' starting page</title>
    
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
	<script type="text/javascript" src="js/FlexPaper_js/flexpaper.js"></script>
	<script type="text/javascript" src="js/FlexPaper_js/flexpaper_handlers.js"></script>
	<script type="text/javascript" src="js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="js/project2_js/document_opeare.js"></script>
	
	<script type="text/javascript">
  		Lookhomework = function(button){
				$.post("getDocumentView.action",
						{document_id : document_id},
						function(returnData, status){
							if(returnData.homework != null)
								setDocumentView("#documentView",
										"../SWF/" + getSWFFile(returnData.homework.SWF_path)) ;
						});
		};
		
		getconvertedList = function(){
			$.post("getconvertedDocuements.action",{},
						function(returnData,status){
							for(var i = 0; returnData.conversedDocuments.length;i++){
								$('#documentView').append(
									addHtmldocument(returnData.convertedDocuments[i]));
							}
						});
		};
	</script>
	
  </head>
  
  <body>
        <div id="documentView" class="flexpaper_viewer"
   			style="width: 800px;height: 500px"></div>
   			
   		<div id="convertedDocuments_op">
   		</div>
  </body>
</html>
