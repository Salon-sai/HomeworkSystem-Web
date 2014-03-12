addNode = function(){
	
} ;

deleteNode = function(){
	
};

editNode = function(){
	
};

bindContextMenu = function(span){
	$(span).contextMenu({menu : 'menu'},function(action,el,pos){
		var node = $.ui.dynatree.getNode(el);
		switch(action){
			case "add" :
				addNode() ;
				break ;
			case "delete" :
				deleteNode() ;
				break ;
			case "edit" :
				editNode() ;
				break;
		}
	});
} ;