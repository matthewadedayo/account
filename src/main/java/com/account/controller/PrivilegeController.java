package com.account.controller;

import com.account.utility.ServerResponseType;
import com.account.dto.ServerResponse;
import com.account.service.PrivilegeService;
import com.food.FoodModel.Account.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;





@Api(tags = "Privilege Management", description = "Endpoint")
@RequestMapping(value = "/privilege", produces = "application/json")
@Controller
public class PrivilegeController {

	@Autowired
	PrivilegeService privilegeService;
	
	
	
	@ApiOperation(value = "Get all user privileges", response = ServerResponse.class)
    @RequestMapping(value = "/get-all-privileges", method = RequestMethod.GET)
    @ResponseBody
//    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ServerResponse getAllPrivileges(@RequestHeader("Authorization") String authorization){
		
		ServerResponse response = new ServerResponse();
		
		try {
			
			response = privilegeService.getPrivileges();
		
		} catch (Exception e) {
			response.setData("An error occured while fetching privileges" + e.getMessage());
			response.setMessage("An error occured while fetching users privileges");
	        response.setSuccess(false);
            response.setStatus(ServerResponseType.FAILED);
		}
		return response;
		
		//return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));

	}
	
	
	
	/*@ApiOperation(value = "Get privilege by role name", response = ServerResponse.class)
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	@ResponseBody
//	@PreAuthorize("hasAuthority('UPDATE') or hasAuthority('CREATE')")
	public ServerResponse getPrivilegeByName(@RequestHeader("Authorization") String authorization, String name){
		
		ServerResponse response = new ServerResponse();
		
		
		try {
			response = privilegeService.getPrivilegesByRole(name);
			
		} catch (Exception e) {
			response.setSuccess(false);
			response.setData("An error occured => " + e.getMessage());
            response.setStatus(ServerResponseType.FAILED);
		}
		return response;
		
		//return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
	}*/
	
	
	
}
