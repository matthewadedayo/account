package com.account.repository;

import com.food.FoodModel.Account.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	
	public User findById(long id);

	public User findByEmail(String email);
	
	//public User findByPhoneNumberOrEmailAddress(String email, String phoneNumber);
	
	public User findByPasswordResetCode(String passwordResetCode);
     

}
