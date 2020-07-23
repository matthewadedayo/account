package com.account.repository;

import com.food.FoodModel.Account.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{
	
	public Role findById(long id);

	public Role findByName(String name);
	

}
