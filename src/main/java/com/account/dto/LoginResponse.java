package com.account.dto;

import com.food.FoodModel.Account.User;
import lombok.Data;


@Data
public class LoginResponse {
	
	private String access_token;
	private String token_type;
	private String refresh_token;
	private Integer expires_in;
	private String scope;
	private User user;
	
}
