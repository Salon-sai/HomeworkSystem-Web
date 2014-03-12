	addListHtml = function(user,userList){
		$("#"+userList+" tbody").append("<tr><td>"+user.idNum+"</td><td>"+
				user.name+"</td></tr>");
	} ;