package com.account.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@SuppressWarnings("serial")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignInDto implements Serializable{
	
	   @JsonIgnore
	    	
            private String grant_type;
           
	    private String username;
	    
	    private String password; 
            
            
            
}
