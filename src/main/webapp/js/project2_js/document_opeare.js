		setDocumentView = function(DivViewer,FilePath){
					$(DivViewer).FlexPaperViewer(
				            { config : {
				                SWFFile : FilePath,
				                Scale : 100,
				                ZoomTransition : 'linear',
				                ZoomTime : 0.5,
				                ZoomInterval : 0.2,
				                FitPageOnLoad : true,
				                FitWidthOnLoad : false,
				                FullScreenAsMaxWindow : false,
				                ProgressiveLoading : false,
				                MinZoomSize : 0.2,
				                MaxZoomSize : 5,
				                SearchMatchAll : false,
				                InitViewMode : 'Portrait',
				                RenderingOrder : 'flash',
				                StartAtPage : '',
				                ViewModeToolsVisible : true,
				                ZoomToolsVisible : true,
				                NavToolsVisible : true,
				                CursorToolsVisible : true,
				                SearchToolsVisible : true,
				                WMode : 'window',
				                localeChain: 'zh_CN'
				            }
				      });
		} ;

  		ajaxUploadFile = function(fileId, url,data){
			$.ajaxFileUpload({
				url : url ,
				type : 'post' ,
				secureuri : false ,
				fileElementId :  fileId,
				dataType : 'json' ,
				data : data ,
				success : function(data,status){
					alert(status) ;
				} ,
				error : function(data, status, e){
					alert(e) ;
				}
			}) ;
		} ;
		
		getSWFFile = function(superString){
			return superString.substring(superString.lastIndexOf('/') + 1 , superString.length) ;
		} ;
		
		addHtmldocumenttest = function(document){
			var TR = "<tr><td>"+document.name+"</td><td>"+document.source_file
			+"</td><td>"+document.pdf_path+"</td><td>"+document.SWF_path
			+"</td><td><input type='button' value='delete' onclick=\"deleteDocument('"+document.id
			+"')\"/></td><td><input type='button' value='conversion' onclick=\"conversion('"+document.id
			+"')\"/></td><td><input type='button' value='show in to the view' onclick=\"showSWF('"
			+document.id+"')\"/></td><td><a href='downloadFile.action?document_id="+document.id
			+"&downloadfile="+document.source_file+"'>download</a></td></tr>" ;
			return TR ;
		} ;
		
		addHtmldocument = function(document){
			var TR = "<tr><td>"+document.name+"</td><td><input type='button' "
			+" value='delete' onclick=\"deleteDocument('"+document.id
			+"')\"/></td>";
			
			if(document.SWF_path==null||document.SWF_path==""){
				TR += "<td><input type='button' value='conversion' onclick=\"conversion('"+document.id
			+"')\"/></td>" ;
			}else{
				TR += "<td><input type='button' value='show in to the view' onclick=\"showSWF('"
			+document.id+"')\"/></td>" ;
				if(document.score != null){
					TR += "<td>"+document.score+"</td>" ;
				}
			}
				TR += "<td><a href='downloadFile.action?document_id="+document.id
			+"&downloadfile="+document.source_file+"'>download</a></td></tr>" ;
			return	TR ;
		};
		
		addHtmlShowdocument = function(document){
			var TR = "<tr><td>"+document.name+"</td><td><input type='button' "
			+" value='delete' onclick=\"deleteDocument('"+document.id
			+"')\"/></td>";
			TR += "<td><input type='button' value='show in to the view' onclick=\"showSWF('"
			+document.id+"')\"/></td>" ;
			if(document.score != null){
				TR += "<td>"+document.score+"</td>" ;
			}
				TR += "<td><a href='downloadFile.action?document_id="+document.id
			+"&downloadfile="+document.source_file+"'>download</a></td></tr>" ;
			return	TR ;
		};
		
		addDocumentStudentTableHtml = function(document){
			var TR = "<tr><td>"+document.name+"</td>"
					+"<td><input type='button' value='Show Document' onclick=\"showSWF('"
					+document.id+"')\"/></td>"
					+"<td><a href='downloadFile.action?document_id="+document.id
					+"&downloadfile="+document.source_file+"'>download</a></td>"
					+"<td><input type='button' value='Your Homeworks of topic' " +
						"onclick=\"getOwnHomeworkByTopic('"+document.id+"')\"/></td></tr>";
			return TR ;
		};
		
		addHomeworkStudentTableHtml = function(document){
			 var TR = "<tr><td>"+document.name+"</td>"
			 	+"<td><input type='button' value='Look Homework' onclick=\"showSWF('"
			 	+document.id+"')\"/></td>";
			 	if(document.score == null){
			 		TR += "<td>no score</td>"+
			 				"<td><input type='button' value='Delete' onclick=\"\"/></td>";
			 	}else{
			 		TR += "<td>"+document.score+"</td>"+
			 				"<td><input type='button' value='Delete' disabled='true'/></td>";
			 	}
			 		TR += "<td><a href='downloadFile.action?document_id="+document.id
			 				+"&downloadfile="+document.source_file+"'>download</a></td></tr>";
			 return TR ;
		 };
		
		addHomeworkHtmlByteacher = function(document){
			var TR = "<tr><td>"+document.name+"</td>" +
					"<td>"+document.user.name+"</td>" +
					"<td>"+document.user.idNum+"</td>" ;
					if(document.score != null){
						TR +="<td>"+document.score+"</td>" ;
					}else{
						TR += "<td style='color : red;'>no score</td>" ;
					}
					TR += "<td><input type='button' value='Look Document' onclick=\"showSWF('"+document.id+"')\"/></td>"
						+"<td><a href='downloadFile.action?document_id="+document.id
						+"&downloadfile="+document.source_file+"'>download</a></td>" +
						"</tr>" ;
			return TR ;
		};
		
		addTopicHtmlByteacher = function(document){
			 var TR = "<tr><td>"+document.name+"</td>"
			 	+"<td><input type='button' value='Look Homework' onclick=\"showSWF('"
			 	+document.id+"')\"/></td>"
			 	+"<td><input type='button' value='Delete'/></td>"
			 	+"<td><input type='button' value='Look homeworks of Topic' onclick=\"findHomeworkListByTopic('"+document.id+"');\"/></td>"
			 	+"<td><a href='downloadFile.action?document_id="+document.id
				+"&downloadfile="+document.source_file+"'>download</a></td></tr>";
			return TR ;
		};
		
			
		