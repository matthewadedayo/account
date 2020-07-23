package com.account.dto;

import lombok.Data;

@Data
public class UpdateUserDto {
	
        private String userName;
	
	private String email;
	
	private String phoneNumber;
	
	private String role;

	
}
