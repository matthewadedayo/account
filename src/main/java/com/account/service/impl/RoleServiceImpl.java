package com.account.service.impl;

import com.account.utility.ServerResponseType;
import com.account.dto.ServerResponse;
import com.food.FoodModel.Account.Role;
import com.account.repository.RoleRepository;
import com.account.service.RoleService;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Transactional
@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	@Override
	public Role findById(long id) {
		
		try {
			return roleRepository.findById(id);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Role findByName(String name) {
		
		try {
			return roleRepository.findByName(name);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return null;
	}

	@Override
	public List<Role> getRoles() {
		
		try {
			return (List<Role>) roleRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return null;
	}

	@Override
	public ServerResponse findAllRole() {
		
		ServerResponse response  = new ServerResponse();
		
		try {
			List<Role> role = getRoles();
			
			response.setData(role);
			response.setStatus(ServerResponseType.OK);
			
			
		} catch (Exception e) {
			
			response.setSuccess(false);
			response.setStatus(ServerResponseType.FAILED);
			response.setData("Failed to get list of roles" + e.getMessage());
			response.setMessage("Failed to get list of roles");
			e.printStackTrace();
			
		}
		return response;
	}

	
	
	

}
