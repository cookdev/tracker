
var Logout = (function(){
	
	var logout = function(){
		comm.logout();
	}
	
	return {
		init : function(){
		
			$("#btnLogOut").on('click', function(){
				logout();
		    });
		}
	};
})();

$(document).ready(function(){
	Logout.init();
});