
startupTree = function(divId,data){
	$('#'+divId).dynatree({
		persist : true ,
		onActivate : function(node){
					if(!node.childList){
						$("[title='className']").text(node.data.title);
						getStudents(node.data.title);
						courseByClass(node.data.title);
					}
					
				},
		onclick : function(node,event){
				if( $(".contextMenu:visible").length > 0 ){
					$(".contextMenu").hide() ;
				}
				alert("click") ;
			},
		onkeydown : function(node, event){
			if($(".contextMenu:visible").length > 0)
				return false ;
			
			switch(event.which){
			case 32:
				$(node.span).trigger("mousedown",{
					preventDefault : true ,
					button : 2
				})
				.trigger("mouseup",{
					preventDefault : true,
					pageX : node.span.offsetLeft,
					pageY: node.span.offsetTop,
					button : 2
				});
				return false ;
			
			//Handle Ctrl-C, -X and -V
			case 67 :
				if(event.ctrlKey){ //Ctrl - C
					return false ;
				}
				break ;
			case 86 :
				if(event.ctrlKey){ //Ctrl - V
					return false ;
				}
				break ;
			case 88 : 
				if(event.ctrlKey){//Ctrl - X
					return false;
				}
				break ;
			}
		},
		
		onCreate : function(node,span){
			bindContextMenu(span) ;
		},
		
		onLazyRead : function(node){
			node.appendAjax({
				url : "aa.json"
			});
		},
		
		dnd : {
			preventVoidMoves :  true,
			
			onDragStart : function(node){
				return true ;
			},
			
			onDragEnter : function(node,sourceNode) {
				if(node.parent !== sourceNode)
					return false ;
				return ["before","after"] ;
			},
			
			onDrop : function(node,sourceNode,hitMode, ui , draggable){
				sourceNode.move(node, hitMode) ;
			}
		}
	});
} ;


InstructTree = function(data){
	var department ;
	var major ;
	var clazz ;
	var treeHtml = "<li title='all'>all<ul>" ;
	for(var i = 0; i < data.informations.length; i++){
		department = data.informations[i] ;
		treeHtml += "<li title='"+department.name+"'>"+department.name;
		if(department.informations.length > 0){
			treeHtml += "<ul>";//majors begin
			for(var j = 0; j < department.informations.length; j++){
				major = department.informations[j] ;
				treeHtml += "<li title='"+major.name+"'>"+major.name ;
				if(major.informations.length > 0){
					treeHtml += "<ul>";//clazzs begin
					for(var k = 0; k < major.informations.length;k++){
						clazz = major.informations[k];
						treeHtml += "<li title='"+clazz.name+"'>"+clazz.name ;
					}
					treeHtml += "</ul>"//clazzs end
				}
				treeHtml += "</ul>";//majors end
			}
		}
	}
	treeHtml += "</li></ul>"
	$('#tree ul').append(treeHtml) ;
	startupTree("tree");
};






