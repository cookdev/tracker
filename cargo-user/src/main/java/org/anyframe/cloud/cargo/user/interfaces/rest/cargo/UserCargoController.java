package org.anyframe.cloud.cargo.user.interfaces.rest.cargo;

import org.anyframe.cloud.cargo.user.interfaces.facade.dto.UserCargo;
import org.anyframe.cloud.cargo.user.interfaces.facade.UserServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/cargo")
public class UserCargoController {

	@Autowired
	private UserServiceFacade userServiceFacade;
	
	@RequestMapping(value = {"/accepted"}, method = {RequestMethod.POST})
	@ResponseStatus(HttpStatus.CREATED)
	public void cargoAccepted(@RequestBody UserCargo userCargo){
		
	}
	
	@RequestMapping(value = {"/routed"}, method = {RequestMethod.POST})
	@ResponseStatus(HttpStatus.CREATED)
	public void cargoRouted(@RequestBody UserCargo userCargo){
		
	}
	
	@RequestMapping(value = {"/misrouted"}, method = {RequestMethod.POST})
	@ResponseStatus(HttpStatus.CREATED)
	public void cargoMisrouted(@RequestBody UserCargo userCargo){
		
	}
	
	@RequestMapping(value = {"/completed"}, method = {RequestMethod.POST})
	@ResponseStatus(HttpStatus.CREATED)
	public void cargoCompleted(@RequestBody UserCargo userCargo){
		
	}

}
