package com.account.service.impl;


import com.account.utility.AppData;
import com.account.utility.ServerResponseType;
import com.account.dto.PasswordResetDto;
import com.account.dto.ResendUserPassDto;
import com.account.dto.ServerResponse;
import com.account.dto.SignInDto;
import com.account.dto.SignUpDto;
import com.account.dto.UpdateUserDto;
import com.account.mail.EmailService;
import com.account.mail.Mail;
import com.food.FoodModel.Account.ConfirmationToken;
import com.food.FoodModel.Account.Role;
import com.food.FoodModel.Account.User;
import com.account.repository.ConfirmationTokenRepository;
import com.account.repository.RoleRepository;
import com.account.repository.UserRepository;
import com.account.service.UserService;
import com.account.utility.Utility;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Transactional
@Service
@Slf4j
public class UserServiceImpl implements UserService{
	
      
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	AppData appData;
	
        @Autowired
        EmailService mailService;  
        
        @Autowired
        private ConfirmationTokenRepository confirmationTokenRepository;
		

    
    Utility utility = new Utility();
	
	@Override
	public Collection<User> findAll() {
		
		try {
			return (Collection<User>) userRepository.findAll();
					
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return null;
	}

	@Override
	public User findById(Long id) {
		
		Optional<User> user = userRepository.findById(id);
                        if (user.isPresent())
                             return user.get();
                        else
                          return new User();

	}

	@Override
	public User findByEmail(String email) {
		
		try {
			return userRepository.findByEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	/*@Override
	public User findByEmailOrPhone(String email, String phoneNumber) {
		
		try {
			return userRepository.findByPhoneNumberOrEmailAddress(email, phoneNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}*/

	
	@Override
	public ServerResponse create(SignUpDto request) {
	
       ServerResponse response = new ServerResponse();
		
		User user = null;
		
                String email = request.getEmail() != null ? request.getEmail() : request.getEmail();
		String userName = request.getUserName() != null ? request.getUserName() : request.getUserName();
		String password = request.getPassword() != null ? request.getPassword() : request.getPassword();
                
                
		if (email == null && !Utility.isValidEmail(email)) {
			
			response.setData("");
                        response.setMessage("Please enter valid email address");
                        response.setSuccess(false);
                        response.setStatus(ServerResponseType.FAILED);

                        return response;
                        }
		
                
                if (userName == null || !userName.isEmpty()){
		} else {
                    response.setData("");
                    response.setMessage("Please enter your username");
                    response.setSuccess(false);
                    response.setStatus(ServerResponseType.FAILED);
                    
                    return response;
            }
                
                if (password == null || !password.isEmpty()){
		} else {
                    response.setData("");
                    response.setMessage("Please enter your password");
                    response.setSuccess(false);
                    response.setStatus(ServerResponseType.FAILED);
                    
                    return response;
               }
		
		try {
			
			User requestUserEmail = userRepository.findByEmail(email);
					
			
			if (requestUserEmail != null) {
				response.setData("");
                response.setMessage("Email already exist");
                response.setSuccess(false);
                response.setStatus(ServerResponseType.FAILED);

                return response;
			}
			
		/*User requestUser = userRepository.findByPhoneNumberOrEmailAddress(phoneNumber, email);
			
			if (requestUser != null) {
				response.setData("");
                response.setMessage("User email or phone number already exist");
                response.setSuccess(false);
                response.setStatus(ServerResponseType.FAILED);

                return response;
			}*/
			
			user = new User();		
                        
                        //Assigning Role to the new user
                        
                        Role role = roleRepository.findByName("user");
			
			Set<Role> userRoles = new HashSet<>();
			userRoles.add(role);
                        
                        user.setUsername(userName);
			user.setEmail(email);
                        user.setPassword(passwordEncoder.encode(password));
                        user.setRoles(userRoles);
			user.setAccountNonExpired(true);
                        user.setAccountNonLocked(true);
                        user.setEnabled(false);
                        user.setCredentialsNonExpired(true);
                        user.setPasswordResetCode("");
                        
			userRepository.save(user);
                        log.info("User persisted");
                        
                        ConfirmationToken confirmationToken = new ConfirmationToken(user);
            
            Mail mail = new Mail();
            mail.setFrom("dayoanifannu@gmail.com");
            mail.setTo(user.getEmail());
            mail.setSubject("User Account Registration");

            Map<String, Object> model = new HashMap<String, Object>();
            {
            model.put("salutation", "Dear " + request.getEmail());
            }
          model.put("message", "Welcome to Dayo.com Thank you for creating an account. Please confirm your registration by clicking on the link below.");
          model.put("link", "http://localhost:9292/verification?token="+confirmationToken.getConfirmationToken());
          
          mailService.sendSimpleMessage(mail);

            
	        response.setData(user);
                response.setMessage("User successfully created");
                response.setSuccess(true);
                response.setStatus(ServerResponseType.OK);
            

		} catch (Exception e) {
		  response.setData("");
                response.setMessage("Failed to create user account");
                response.setSuccess(false);
                response.setStatus(ServerResponseType.FAILED);

                log.error("An error occured while creating recipient account");
                e.printStackTrace();
		}
		return response;
		
	}


     @Override
      public ServerResponse reSendUserPassword(ResendUserPassDto request) {
    	 
    	 ServerResponse response = new ServerResponse();
 		
 		try {
 			String email = request.getEmail() != null ? request.getEmail() : request.getEmail();

 			User user = userRepository.findByEmail(email);
 			
 			if (user == null) {
 				
 				response.setData("");
                                response.setMessage("User not found");
                                response.setSuccess(false);
                                response.setStatus(ServerResponseType.FAILED);
 				
 				return response;
 			}
 	        
 			String passwordResetCode = String.valueOf(Utility.generateActivationCode());

 			user.setPasswordResetCode(passwordResetCode);
 			
 			response.setData("password reset code sent successfully");
                        response.setMessage("password reset code sent successfully");
                        response.setSuccess(true);
                        response.setData(passwordResetCode);
                        response.setStatus(ServerResponseType.OK);

                        } catch (Exception e) {

                                response.setData("");
                        response.setMessage("Failed to send user password reset code");
                        response.setSuccess(false);
                        response.setStatus(ServerResponseType.FAILED);
 	          
 			e.printStackTrace();
 		}
 		return response;
         }

     @Override
      public ServerResponse passwordReset(PasswordResetDto request) {
    	 
    	 ServerResponse response = new ServerResponse();
 		
 		try {
 			String passwordResetCode = request.getResetCode() != null ? request.getResetCode() : request.getResetCode();
 			String password = request.getPassword() != null ? request.getPassword() : request.getPassword();

 			User user = userRepository.findByPasswordResetCode(passwordResetCode);

 			if (user == null) {
 				
 				response.setData("");
 		        response.setMessage("Invalid password reset Code");
 		        response.setSuccess(false);
 		        response.setStatus(ServerResponseType.FAILED);
 				
 				return response;
 			}
 			
 			user.setPassword(passwordEncoder.encode(password));
 			user.setPasswordResetCode(null);
 			
 			response.setData("");
                        response.setMessage("User password successfully changed");
                        response.setSuccess(true);
                        response.setData(password);
                        response.setStatus(ServerResponseType.OK);

                        } catch (Exception e) {

                                response.setData("");
                        response.setMessage("Failed to reset user password");
                        response.setSuccess(false);
                        response.setStatus(ServerResponseType.FAILED);
 	          
 			e.printStackTrace();
 		}
 		return response;
        }

     /*@Override
     public ServerResponse login(SignInDto request) {
    	 
    	 ServerResponse response = new ServerResponse();
 		try {
 			
 			log.info(request.getUsername());
 			
 			//convert client id and client secret to base64 token 
 			String authorization = Utility.getCredentials(appData.CLIENT_ID, appData.CLIENT_SECRET);
 			log.info(authorization);
 			request.setGrant_type(appData.GRANT_TYPE);
 			
 			//send login request
 			response = Utility.loginHttpRequest( appData.APP_LOGIN_URL, request, authorization);
 			
 		} catch (Exception e) {
 			response.setData("Something went wrong !!!" + e.getMessage());
 			response.setMessage("User authentication failed");
 			response.setSuccess(false);
 			response.setStatus(ServerResponseType.FAILED);
 			
 			return response;
 		}
 		
 		return response;
      }*/
     
     
	   @Override
	    public ServerResponse viewAll() {
		
		ServerResponse response = new ServerResponse();
		
		Collection<User> users = null;
		
		try {
			
			users = findAll();
			
			if(users == null) {
				response.setData("No user available");	
				response.setStatus(ServerResponseType.NO_CONTENT);
				
				return response;
			}
			
			response.setData(users);
			response.setStatus(ServerResponseType.OK);
			response.setSuccess(true);
			response.setMessage("Get users successfully");
			
		} catch (Exception e) {
			
			response.setData("Failed to fetch users" + e.getMessage());
			response.setSuccess(false);
			response.setMessage("Failed to fetch users");
			response.setStatus(ServerResponseType.FAILED);
			e.printStackTrace();
		}
		
		return response;
	}

	   
	@Override
	public ServerResponse updateUser(UpdateUserDto request) {
		
		ServerResponse response = new ServerResponse();
		User user = null;
		
		String email = request.getEmail() != null ? request.getEmail() : request.getEmail();
		//String username = request.getUserName() != null ? request.getUserName() : request.getUserName();
		String phoneNumber = request.getPhoneNumber() != null ? request.getPhoneNumber() : request.getPhoneNumber();;
		
		
		if (email != null && !Utility.isValidEmail(email)) {
			
			response.setData("");
            response.setMessage("Email address not found");
            response.setSuccess(false);
            response.setStatus(ServerResponseType.FAILED);

            return response;
		}
		
		if (phoneNumber == null || !Utility.isValidPhone(phoneNumber)) {
			response.setData("");
            response.setMessage("phone number not found");
            response.setSuccess(false);
            response.setStatus(ServerResponseType.FAILED);

            return response;
		}
		
		try {
			 
			User userUpdate = findByEmail(request.getEmail());
			
			if(userUpdate == null) {
				response.setData("User not found");
				response.setStatus(ServerResponseType.FAILED);
				response.setSuccess(false);
				
				return response;
			}
			
			  user = userRepository.findByEmail(email);
			
			 
			  
			   if (email != null)
				   user.setEmail(email);
			   
			   userRepository.save(user);
			   
			   response.setData(user);
			   response.setStatus(ServerResponseType.UPDATED);
			   response.setSuccess(true);
			   response.setMessage("User successfully updated");
			
		} catch (Exception e) {
			response.setData("Failed to update user details" + e.getMessage());
			response.setMessage("Failed to update user details");
			response.setSuccess(false);
			response.setStatus(ServerResponseType.FAILED);
			e.printStackTrace();
			
		}
		
		return response;
	}
        
        @Transactional
	@Override
	public ServerResponse delete(long id) {
	
      ServerResponse response = new ServerResponse();
		
		if (id == 0) {
			response.setData("User Id cannot be null");
			response.setStatus(ServerResponseType.FAILED);
			response.setSuccess(false);
				
			return response;
		}
		
		try {
			
			User user = userRepository.findById(id);
			
			
			if (user == null) {
				response.setData("User not found");
				response.setStatus(ServerResponseType.FAILED);
				response.setSuccess(false);
				
				return response;
			}
			user.getRoles().removeAll(user.getRoles());
			userRepository.delete(user);
			
			 response.setStatus(ServerResponseType.DELETED);
			 response.setData("User account has been successfully deleted");
			 response.setSuccess(true);

	        } catch (Exception e) {
	        	response.setStatus(ServerResponseType.FAILED);
	        	response.setData("Failed to delete user account");
	        	response.setSuccess(false);
	            e.printStackTrace();
	        }
		
		return response;
		
	}

    @Override
    public ServerResponse login(SignInDto request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
		
	
}
