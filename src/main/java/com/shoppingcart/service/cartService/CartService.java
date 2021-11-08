package com.shoppingcart.service.cartService;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shoppingcart.model.AddToCart;
import com.shoppingcart.model.CheckoutCart;

@Service
public interface CartService {
	
	List<AddToCart> addToaddCartByUserIdAndProductId(Long productId, Long userId, Integer qty, Double price)
			throws Exception;

	void updateQtyByCartId(Long cartId, Integer qty, Double price) throws Exception;

	List<AddToCart> getCartByUserId(Long userId);

	List<AddToCart> removeCartByUserId(Long cartId, Long userId);

	List<AddToCart> removeAllCartByUserId(Long userId);
	
	Boolean checkTotalAmountAgainstCart(Double totalAmount,Long userId);
	
	List<CheckoutCart> getAllCheckoutByUserId(Long userId);
	
	List<CheckoutCart> saveProductsForCheckout(List<CheckoutCart> checkoutcart)  throws Exception;
}
