package com.account.service;

import com.account.dto.PasswordResetDto;
import com.account.dto.ResendUserPassDto;
import com.account.dto.ServerResponse;
import com.account.dto.SignInDto;
import com.account.dto.SignUpDto;
import com.account.dto.UpdateUserDto;
import com.food.FoodModel.Account.User;
import java.util.Collection;

import org.springframework.stereotype.Service;




@Service
public interface UserService {
	
	public Collection<User> findAll();
	
	public User findById(Long id);
	
	public User findByEmail(String email);
	
	//public User findByEmailOrPhone(String email, String phoneNumber);

	public ServerResponse create(SignUpDto request);
	
	public ServerResponse reSendUserPassword(ResendUserPassDto request);
	
	public ServerResponse passwordReset(PasswordResetDto request);
	
	public ServerResponse updateUser(UpdateUserDto request);
	
	public ServerResponse login(SignInDto request);
	
	public ServerResponse viewAll();
	
	public ServerResponse delete(long id);
	

}
