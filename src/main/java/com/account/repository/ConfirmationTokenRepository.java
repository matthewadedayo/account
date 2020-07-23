package com.account.repository;

import com.food.FoodModel.Account.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author buffermedia
 */
public interface ConfirmationTokenRepository extends CrudRepository <ConfirmationToken, String> {
    
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}

