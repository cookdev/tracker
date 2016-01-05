var IncidentLogger = (function() {

	var openModalForErrorMsg = function(errorMsg, followup){
		if($('#loggerErrMsgModal').size() === 0){
			$('body').append('<div id="loggerErrMsgModal" class="reveal-modal tiny" data-reveal aria-labelledby="modalTitle" aria-hidden="true" role="dialog">'
					+ '<h3 id="modalTitle">Sorry. Request is failed.</h3>'
					+  '<p class="lead">'+errorMsg+'</p>'
					+  '<a class="close-reveal-modal" aria-label="Close">&#215;</a>'
					+ '</div>');
		}else{
			$('#loggerErrMsgModal .lead').text(errorMsg);
		}
		
		$('#loggerErrMsgModal').foundation('reveal', 'open');
	};
	
	var formView = {
			
		ID: {
			FORM: "#incidentLogForm"
		},
		
		URL: {
			LOG_POST: {method: "POST", url: comm.server.url+"/tracker/handling/reports"}
		},
		
		init: function(){
			_this = this;
		
			$(this.ID.FORM).submit(function(e){
				e.preventDefault();
				_this.doLogging(_this.getNewDestination());
			});
		},
		
		getNewDestination: function(){
			return {"completionTime": $("#input-completionTime").val(),
				"trackingId": $("#input-trackingID").val(),
				"eventType" : $("#select-EventType").val(),
				"unLocode" : $("#input-unLocCode").val(),
				"voyageNumber": $("#input-voyageNumber").val()
				};
		},
	
		
		doLogging: function(data){
			_this = formView;
			_msg = openModalForErrorMsg;
			
			if (!this.validate()) {
		    	alert(validationMessage);
		    	return;
		    }
			
			comm.callApi({
				url: _this.URL.LOG_POST.url,
				method: _this.URL.LOG_POST.method,
				contentType: 'application/json; charset=UTF-8',
				data: JSON.stringify(data),
				success: function(data, textStatus, jqXHR){
					var form = document.forms[0];
					for (var i = 0, ii = form.length; i < ii; ++i) {
						var input = form[i];
						if (input.name) {
							input.value = "";
						}
					}
					comm.openModalForSuccessMsg("");
				},
				error: function(jqXHR, textStatus, errorThrown ){
					//debugger
					_msg(errorThrown, "해결방안이 나와야하는데 오류정의를 먼저해야지요");
				},
				complete: function(jqXHR, textStatus){
				}

			});
		},
		
		validate: function(){
			var valid = true;
			var form = $(_this.ID.FORM);
			var eventType = form.find('select[name="eventType"]').val();
			var voyageNumber = form.find('input[name="voyageNumber"]').val();
			
			validationMessage = "";
			
			if (eventType == "LOAD" || eventType == "UNLOAD") {
				if (!voyageNumber) {
					valid = false;
					validationMessage += "Voyage required.";
				}
			}
			else {
				form.find('input[name="voyageNumber"]').val(null);
			}
			
			return valid;
		}
	};
	
	
	return {
		init: function(){
			formView.init();
		}
	}
})();

$(document).ready(function(){
	IncidentLogger.init();
});