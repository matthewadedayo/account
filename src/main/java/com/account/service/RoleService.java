package com.account.service;

import com.account.dto.ServerResponse;
import com.food.FoodModel.Account.Role;
import java.util.List;

import org.springframework.stereotype.Service;



@Service
public interface RoleService {
	
	   public Role findById(long id);

	    public Role findByName(String name);

	    public List<Role> getRoles();
	    
	    public ServerResponse findAllRole();

}
