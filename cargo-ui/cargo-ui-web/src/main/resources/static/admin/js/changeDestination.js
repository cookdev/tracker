adminPage.ChangeDestination = (function() {

	var ENV =  {
		id: "",
		sourceUrl: "",
		action: "",
		method: ""
	};
	
	var getCargoDetail = function(id){
		
		comm.callApi({
			url: ENV.sourceUrl,
			method: "GET",
			dataType: "json",
			contentType: "application/json",
			success: function(data, textStatus, jqXHR){
				gridView.init(data);
				formView.init();
			},
			complete : function(text, xhr){
			}
		});
	};
	
	var gridView = {
		_cargoDetail: {},
		
		ID: {
			FROM: "#frmChangeDestination",
			SEL_LOCATIONS: "#locations",
			trackingId: ""
		},
		
		init: function(details){
			_cargoDetail = details;
			this.makeGrid();
		},
		
		makeGrid: function(){
			var cargoDetail = _cargoDetail;
			
			$(this.ID.FROM).append('<span class="label">Change destination for cargo '+ENV.id+'</span>');
			$(this.ID.FROM).append('<table>'
				+ '<tbody>'
				+  '<tr>'
				+   '<td>Current destination</td>'
				+   '<td>'+cargoDetail.finalDestination+'</td>'
				+  '</tr>'
				+  '<tr>'
				+   '<td>New destination</td>'
				+   '<td>'
				+    '<select id="locations" name="destinationUnlocode"></select>'
				+   '</td>'
				+  '</tr>'
				+  '</tbody>'
				+  '<tfoot>'
				+  '<tr>'
				+   '<td></td>'
				+   '<td><input type="submit" id="btnChangeDestination" class="button small" style="margin-bottom:0px;" value="Change destination"/></td>'
				+  '</tr>'
				+  '</tfoot>'
				+'</table>');
			
			comm.appendSelectLocations($(this.ID.SEL_LOCATIONS));
		},
		
		getNewDestination: function(){
			return {"trackingId": ENV.id, "bookingId": ENV.id, "destinationUnlocode": $(this.ID.SEL_LOCATIONS).val()};
		}
	};
	
	var formView = {
		_gridView: gridView,
			
		ID: {
			FORM: "#frmChangeDestination",
			BTN_SUBMIT: "#btnChangeDestination"
		},
		
		init: function(){
			_this = this;
		
			$(this.ID.FORM).submit(function(e){
				e.preventDefault();
				_this.doChangeDestination(_this._gridView.getNewDestination());
			});
			
		},
		
		doChangeDestination : function(data){
			comm.callApi({
				url: ENV.action,
				method: ENV.method,
				data: JSON.stringify(data),
				dataType: "text",
				contentType: "application/json",
				success: function(data, textStatus, jqXHR){
					location.hash = location.hash.split('/change-destination')[0];
				},
				error:function( jqXHR,  textStatus,  errorThrown){
					console.log(textStatus);
				},
				complete : function(text, xhr){
				}
			});
		}
			
	};
	
	var bookingInit = function(id){
		ENV = {
			id: id,
			sourceUrl: comm.server.url+"/booking/bookings/"+id,
			action: comm.server.url+"/booking/bookings/"+id+"/change-destination",
			method: "PUT"
		}
	};
	
	var trackingInit = function(id){
		// TODO : Admin changeDestination 담당 서비스 정의. booking or traker
		ENV = {
			id: id,
			sourceUrl: comm.server.url+"/tracker/cargos/"+id,
			action: comm.server.url+"/tracker/cargos/"+id+"/change-destination",
			method: "POST"
		}
	};
	
	
	return function(status, id){

		if(!comm.initPage()){
	    	return;
	    }
	    
	    template.RenderOne({
	        target: "#body",
	        tagName: "div",
	        className: "admin-change-destination",
	        id: "bodyAdminChangeDestination",
	        position: "new",
	        template: comm.getHtml("admin/change-destination.html"),
	        data: undefined,
	        events: {
	        },

	        afterRender: function() {
	        	if(status === "not-accepted"){
	        		bookingInit(id);
	        	}else{
	        		trackingInit(id);
	        	}
	        	getCargoDetail(id);
	        } 
	    });
	};
})();