package com.account.controller;

import com.account.utility.ServerResponseType;
import com.account.dto.PasswordResetDto;
import com.account.dto.ResendUserPassDto;
import com.account.dto.ServerResponse;
import com.account.dto.SignInDto;
import com.account.dto.SignUpDto;
import com.account.dto.UpdateUserDto;
import com.food.FoodModel.Account.ConfirmationToken;
import com.food.FoodModel.Account.User;
import com.account.repository.ConfirmationTokenRepository;
import com.account.repository.UserRepository;
import com.account.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Api(tags = "User Account Management", description = "Endpoint")
@RequestMapping(value = "/users", produces = "application/json")
@RestController
public class UserController {

	@Autowired
	UserService userService;
        
        @Autowired
	UserRepository userRepository;
        
        @Autowired
        private ConfirmationTokenRepository confirmationTokenRepository;
	
	private HttpHeaders responseHeaders = new HttpHeaders();
	
	 
	              /*****************************************
                         *  REGISTER A USER ACCOUNT
                  *****************************************/

	@ApiOperation(value = "Register a user account", response = ServerResponse.class)
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
//	@PreAuthorize("hasAuthority('SUPER_ADMIN')")
	public ResponseEntity<?> create(@RequestBody SignUpDto request){
//	public ServerResponse create(@RequestHeader("Authorization") String authorization, @RequestBody SignUpDto request){
	
		log.info("Starting to create user account at controller");
		
		ServerResponse response = new ServerResponse();
		
		log.info(request.getEmail());
		try {
			response = userService.create(request);
                        
                        log.info("User service called");
		 
		} catch (Exception e) {
			response.setData("An error occured" + e.getMessage());
            response.setMessage("Failed to register user");
            response.setSuccess(false);
            response.setStatus(ServerResponseType.FAILED);
            
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
	//	return response;
	}
	
	
	
                         /*****************************************  
                            * VERIFY AND ACTIVATE USER ACCOUNT
                          *****************************************/
	

	/*@ApiOperation(value = "Verify and Activate user account", response = ServerResponse.class)
	@RequestMapping(value = "/verification", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> ActivateUser(@RequestBody ActivateUserRequest request){
		
		 @RequestParam("token")String confirmationToken)
		ServerResponse response = new ServerResponse();
		
		try {
			
			response = usersService.userActivation(request);
		 
		} catch (Exception e) {
			response.setData("An error occured" + e.getMessage());
            response.setMessage("Failed to verify and activate user");
            response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);
            
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
	}*/
        
        @ApiOperation(value = "Verify and Activate user account", response = ServerResponse.class)
	@RequestMapping(value = "/activation", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> ActivateUser(@RequestParam("token")String confirmationToken){
		
      ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
		ServerResponse response = new ServerResponse();
		
		
			
			 if(token != null)
                          {
                           User user = userService.findByEmail(token.getUser().getEmail());
                             user.setEnabled(true);
                             userRepository.save(user);
            
                       }
                      else  {
			//response.setData("An error occured" + e.getMessage());
            response.setMessage("Failed to verify and activate user");
            response.setSuccess(false);
            response.setStatus(ServerResponseType.FAILED);
            	
		
	}
	return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
       }	
	                    /*****************************************
                            * RESEND USER ACTIVATION CODE
                         *****************************************/
	
	/*@ApiOperation(value = "Resend user Activation code", response = ServerResponse.class)
	@RequestMapping(value = "/resend-activation", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> ResendUserActivation(@RequestBody ResendUserActivationCodeDto request){
		
		
		ServerResponse response = new ServerResponse();
		
		try {
			
			response = usersService.reSendUserActivation(request);
		 
		} catch (Exception e) {
			response.setData("An error occured" + e.getMessage());
            response.setMessage("Failed to resend user activation code");
            response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);
            
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
	}*/
	
	
	                      /*****************************************
                               * PASSWORD RECOVERY CODE
                           *****************************************/

	@ApiOperation(value = "Send password recovery code to the user", response = ServerResponse.class)
	@RequestMapping(value = "password/recovery", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> PasswordRestRequest(@RequestBody ResendUserPassDto request){
		
		
		ServerResponse response = new ServerResponse();
		
		try {
			
			response = userService.reSendUserPassword(request);
		 
		} catch (Exception e) {
			response.setData("An error occured" + e.getMessage());
            response.setMessage("Failed to send user password recovery code");
            response.setSuccess(false);
            response.setStatus(ServerResponseType.FAILED);
            
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
	}
	
	
	                     /*****************************************
                                * RESET USER PASSWORD
                          *****************************************/
	
	@ApiOperation(value = "Change user password", response = ServerResponse.class)
	@RequestMapping(value = "password/change", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> PasswordReset(@RequestBody PasswordResetDto request){
		
		
		ServerResponse response = new ServerResponse();
		
		try {
			
			response = userService.passwordReset(request);
		 
		} catch (Exception e) {
			response.setData("Failed to reset user password" + e.getMessage());
            response.setMessage("Failed to reset user password");
            response.setSuccess(false);
            response.setStatus(ServerResponseType.FAILED);
            
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
	}
	
	
	                     /*****************************************
                                 USER LOGIN REQUEST
                          *****************************************/
	
	@ApiOperation(value = "Login user account", response = ServerResponse.class)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> Login(@RequestBody SignInDto request){
		
		ServerResponse response = new ServerResponse();
		
		try {
			
			response = userService.login(request);
		
		} catch (Exception e) {
			e.printStackTrace();
			response.setData("An error occured in user account verification" + e.getMessage());
			response.setMessage("An error occured in user account verification");
	        response.setSuccess(false);
            response.setStatus(ServerResponseType.FAILED);
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));

	}

	
	                       /*****************************************
                                          GET ALL USERS 
                            *****************************************/

	@ApiOperation(value = "Get all user accounts", response = ServerResponse.class)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
//    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> viewAll(@RequestHeader("Authorization") String authorization){
		
		ServerResponse response = new ServerResponse();
		
		try {
			
			response = userService.viewAll();
		
		} catch (Exception e) {
			e.printStackTrace();
			response.setData("An error occured while fetching users accounts" + e.getMessage());
			response.setMessage("An error occured while fetching users accounts");
	        response.setSuccess(false);
            response.setStatus(ServerResponseType.FAILED);
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));

	}
	
	
	                       /*****************************************
                                     UPDATE USER DETAILS 
                            *****************************************/
	
	@ApiOperation(value = "Update a user account", response = ServerResponse.class)
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	@ResponseBody
//	@PreAuthorize("hasAuthority('UPDATE')")
	public ResponseEntity<?> update(@RequestBody UpdateUserDto request){
		
		ServerResponse response = new ServerResponse();
		
		try {
			response = userService.updateUser(request);
			
		} catch (Exception e) {
			response.setData("An error occured => " + e.getMessage());
			response.setMessage("Failed to update user details");
			response.setSuccess(false);
            response.setStatus(ServerResponseType.FAILED);
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
	}
	
	
                             /*****************************************
                                       DELETE USER ACCOUNT
                             *****************************************/
	
	@ApiOperation(value = "Delete a user account", response = ServerResponse.class)
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
//	@PreAuthorize("hasAuthority('DELETE')")
	public ResponseEntity<?> delete(@RequestHeader("Authorization") String authorization, @PathVariable long id){
		
		ServerResponse response = new ServerResponse();
		
		try {
			response = userService.delete(id);
			
		} catch (Exception e) {
			response.setData("An error occured => " + e.getMessage());
			response.setMessage("Failed to delete user");
			response.setSuccess(false);
            response.setStatus(ServerResponseType.FAILED);
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
	}
	
	
        @RequestMapping(value = "/{id}", method = RequestMethod.GET)
        public User getUser(@PathVariable (value = "id") Long id){
            User user = userService.findById(id);
             log.info("User Available");
                return user;
    }
	
}
