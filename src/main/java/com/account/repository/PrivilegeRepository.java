package com.account.repository;

import com.food.FoodModel.Account.Privilege;
import com.food.FoodModel.Account.Role;
import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Adedayo
 */

@Repository
public interface PrivilegeRepository extends CrudRepository<Privilege, Long>{
	
	public Privilege findById(long id);

	public Privilege findByName(String name);
	
	//public  Collection<Privilege> findAllByRoles(Role role);
	
	//public Collection<Privilege> findByRoles_name(String name);
	

}
