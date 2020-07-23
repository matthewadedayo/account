package com.account.dto;

import lombok.Data;


@Data
public class PasswordResetDto {
	
	private String password;
        
	private String resetCode;
}
