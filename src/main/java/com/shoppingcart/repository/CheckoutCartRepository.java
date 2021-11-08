package com.shoppingcart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shoppingcart.model.CheckoutCart;

@Repository
public interface CheckoutCartRepository extends JpaRepository<CheckoutCart, Long> {
	
	@Query("Select checkCart  FROM CheckoutCart checkCart WHERE checkCart.userId=:userId")
	List<CheckoutCart> getByuserId(@Param("userId")Long userId);
}
