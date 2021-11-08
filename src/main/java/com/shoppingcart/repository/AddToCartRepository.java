package com.shoppingcart.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shoppingcart.model.AddToCart;

@Repository
public interface AddToCartRepository extends JpaRepository<AddToCart, Long>{

	@Query("Select sum(addCart.price) FROM AddToCart addCart WHERE addCart.userId=:userId")
	double getTotalAmountByUserId(@Param("userId")Long userId);
	
	@Query("Select addCart FROM AddToCart addCart WHERE addCart.product.id= :productId and addCart.userId=:userId")
	Optional<AddToCart> getCartByProductIdAndUserId(@Param("productId") Long productId,@Param("userId") Long userId);

	@Query("Select addCart FROM AddToCart addCart WHERE addCart.userId=:userId")
	List<AddToCart> getCartByUserId(@Param("userId")Long userId);
	
	@Modifying
    @Transactional
	@Query("DELETE FROM AddToCart addCart WHERE addCart.id =:cartId and addCart.userId=:userId")
	void deleteCartByIdAndUserId(@Param("userId")Long userId,@Param("cartId")Long cartId);
	
	@Modifying
    @Transactional
	@Query("DELETE FROM AddToCart addCart WHERE addCart.userId=:userId")
	void deleteAllCartByUserId(@Param("userId")Long userId);
	
	@Modifying
    @Transactional
	@Query("DELETE FROM AddToCart addCart WHERE addCart.userId=:userId")
	void deleteAllCartUserId(@Param("userId")Long userId);
	
	@Modifying
    @Transactional
	@Query("update AddToCart addCart set addCart.qty=:qty,addCart.price=:price WHERE addCart.id=:cartId")
	void updateQtyByCartId(@Param("cartId")Long cartId,@Param("price")double price,@Param("qty")Integer qty);
}
