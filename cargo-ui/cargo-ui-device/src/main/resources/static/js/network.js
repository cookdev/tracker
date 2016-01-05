"use strict";

/**
 * Authorization Header 세팅 ( Client Token ) - login 필요없는 요청
 */
comm.callPublicApi = function(option){
	$.extend(option, {
		async: false,
		beforeSend: function(xhr, settings){
			if(comm.localStorage.get('ClientToken') === null){
				if(!comm.ClientCredentials(comm.AUTH.CLIENT_ID, comm.AUTH.CLIENT_SECRET)){
					return false;
				}
			}
			xhr.setRequestHeader("Authorization", "Bearer " + comm.localStorage.get('ClientToken'));
			return true; 
		}
	});
	$.ajax(option);
}

/**
 * Authorization Header 세팅 ( User Token )
 */
comm.callApi = function(option){
	$.extend(option, {
		async: false,
		statusCode: {
			401: function(jqXHR,  textStatus,  errorThrown){
				var errorObj = {};
				if(comm.isUndefined(jqXHR.responseJSON)){
					errorObj = JSON.parse(jqXHR.responseText);
				}else{
					errorObj = jqXHR.responseJSON;
				}
				
				if(errorObj.error === "invalid_token"){
					if(errorObj.error_description === "Token has expired"){
						comm.openModalForErrorMsg("Login session has expired", "Please Login again");
					}else if(errorObj.error_description === "Token was not recognised"){
						comm.openModalForErrorMsg("You are unauthenticated user(Token was not recognised)", "Please Login again");
					}else{
						comm.openModalForErrorMsg(errorObj.error_description, "Please Login again");
					}
				}else{
					comm.openModalForErrorMsg(errorObj.error_description, "Please Login again");
				}
				comm.logout();
			}
		},
		beforeSend: function(xhr, settings){
			if(comm.localStorage.get('UserToken') === null){
				comm.openModalForInformMsg('Login')
				comm.logout();
				return false;
			}
			xhr.setRequestHeader("Authorization", "Bearer " + comm.localStorage.get('UserToken'));
			return true; 
		}
	});
	$.ajax(option);
};

