var Login = (function(){
	
	var ENV = {
		FORM_ID : "#formLogin"
	};
	
	var login = function(){
		
		var formdata = {
			password: $(ENV.FORM_ID).find("[name=password]").val()
		}
		formdata.password = sha256_digest(formdata.password);
		
		var loginName = $(ENV.FORM_ID).find("[name=login]").val();
		formdata.loginName = loginName;
		
		comm.UserCredentials(loginName, formdata.password);
		
	}
	
	return {
		init : function(){
            $("#btnLogIn").on("click", function(){
            	login();
            });
        	$("#formLogin").on('keydown', function(e){
        		if(e.which === 13){
        			if($('#formLogin [name="login"]').val() === ""){
        				comm.openModalForInformMsg('Please Input your name');
        				$('#formLogin [name="login"]').focus();
        			}else if($('#formLogin [name="password"]').val() === ""){
        				comm.openModalForInformMsg('Please Input password')
        				$('#formLogin [name="password"]').focus();
        			}else{
        				//debugger;
        				$("#btnLogIn").trigger('click');
        			}
        		}
        	});
		}
	};
})();

$(document).ready(function(){
	Login.init();
});