var CLASS = 'class' ;
var MAJOR = 'major' ;


addInformation = function(informationName, TypeName,superInformationName){
		$.post("saveInformation.action",
				{InformationName : informationName, TypeName : TypeName,
				superInformationName : superInformationName},
				function(returnData,status){
					//add html
					if(TypeName == 'department'){
						$('#departmentList tbody').append(addInformationHtml(returnData.information,MAJOR)) ;
					}else if(TypeName == 'major'){
						$('#majorList tbody').append(addInformationHtml(returnData.information,CLASS)) ;
					}else if(TypeName == 'class'){
						$('#classList tbody').append(addInformationHtml(returnData.information)) ;
					}
				});
	} ;
	
	
	
	getInformations = function(InformationType,superInformationName){
		$.post("getInformations.action",
				{TypeName : InformationType,superInformationName : superInformationName},
				function(returnData,status){
					if(status == "success"){
						if(InformationType == "department"){
							for(var i= 0; i < returnData.informations.length; i++){
								$('#departmentList tbody').append(
										addInformationHtml(returnData.informations[i],'major')) ;
							}
						}else if(InformationType == "major"){
							$('#major_opeare h5').append(superInformationName) ;
							for(var i=0;i < returnData.informations.length;i++){
								$('#majorList tbody').append(
										addInformationHtml(returnData.informations[i],'class')) ;
							}
						}else if(InformationType == "class"){
							$('#class_opeare h5').append(superInformationName);
							for(var i = 0;i < returnData.informations.length;i++){
								$('#classList tbody').append(
										addInformationHtml(returnData.informations[i],'student'));
							}
						}
					}
				});
	};
	
	deleteInformation = function(button){
		var TR = $(button).parents('tr') ;
		$.post("deleteInformationByName.action",
				{informationName : TR.find('td:eq(0) a').html()},
				function(returnData,status){
					if(status == 'success'){
						TR.remove() ;
					}
				});
	}
	
	saveDepartment = function(){
		addInformation($('#addDepartmentName').val(),'department',null);
	};
	
	saveMajor = function(){
		addInformation($('#addMajorName').val(),'major',$('#major_opeare h5').html());
	};
	
	saveClass = function(){
		addInformation($('#addClassName').val(),'class',$('#class_opeare h5').html());
	};
	
	addInformationHtml = function(information,NextType){
		var TR = "<tr><td><a href='javascript:void(0);' onclick=\"getInformations('"
				+NextType+"','"+information.name+"')\">"+information.name
				+"</a></td><td><input type='button' "
				+" value='alter'/></td><td><input type='button' "
				+" value='delete' onclick='deleteInformation(this)'/></td>"
				+"<td><a href='javascript:void(0);'>more detail</a></td></tr>" ;
		return TR ;
	}
	
	
	
	