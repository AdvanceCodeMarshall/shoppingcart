package com.shoppingcart.service.cartService.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.model.AddToCart;
import com.shoppingcart.model.CheckoutCart;
import com.shoppingcart.model.Products;
import com.shoppingcart.repository.AddToCartRepository;
import com.shoppingcart.repository.CheckoutCartRepository;
import com.shoppingcart.service.cartService.CartService;
import com.shoppingcart.service.productServices.ProductServices;

@Service
public class CartServiceImpl implements CartService {
	
	private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);
	 
	@Autowired
	private AddToCartRepository addToCartRepository;
	
	@Autowired
	private ProductServices productServices;
	
	@Autowired
	private CheckoutCartRepository checkoutCartRepository;
	
	@Override
	public List<AddToCart> addToaddCartByUserIdAndProductId(Long productId, Long userId, Integer qty, Double price)
			throws Exception {
		try {
			if (addToCartRepository.getCartByProductIdAndUserId(productId, userId).isPresent()) {
				throw new Exception("Product is already exist");
			}
			AddToCart addToCart = new AddToCart();
			addToCart.setQty(qty);
			addToCart.setUserId(userId);
			
			Products product = productServices.getProductsById(productId);
			addToCart.setProduct(product);
			//TODO price has to check with qty
			addToCart.setPrice(price);
			addToCartRepository.save(addToCart);
			return this.getCartByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(""+ e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<AddToCart> getCartByUserId(Long userId) {
		return addToCartRepository.getCartByUserId(userId);
	}

	@Override
	public void updateQtyByCartId(Long cartId, Integer qty, Double price) throws Exception {
		addToCartRepository.updateQtyByCartId(cartId,price,qty);
	}
	
	@Override
	public List<AddToCart> removeCartByUserId(Long cartId, Long userId) {
		addToCartRepository.deleteCartByIdAndUserId(userId,cartId);
		return this.getCartByUserId(userId);
	}

	@Override
	public List<AddToCart> removeAllCartByUserId(Long userId) {
		addToCartRepository.deleteAllCartByUserId(userId);
		return null;
	}
	
	@Override
	public Boolean checkTotalAmountAgainstCart(Double totalAmount,Long userId) {
		double total_amount =addToCartRepository.getTotalAmountByUserId(userId);
		if(total_amount == totalAmount) {
			return true;
		}
		System.out.print("Error from request "+total_amount +" --db-- "+ totalAmount);
		return false;
	}
	
	@Override
	public List<CheckoutCart> saveProductsForCheckout(List<CheckoutCart> checkoutCart) throws Exception {
		try {
			Long userId = checkoutCart.get(0).getUserId();
			if(checkoutCart.size() >0) {
				checkoutCartRepository.saveAll(checkoutCart);
				this.removeAllCartByUserId(userId);
				return this.getAllCheckoutByUserId(userId);
			}	
			else {
				throw new Exception("Should not be empty");
			}
		}catch(Exception e) {
			throw new Exception("Error while checkout "+e.getMessage());
		}
		
	}

	@Override
	public List<CheckoutCart> getAllCheckoutByUserId(Long userId) {
		return checkoutCartRepository.getByuserId(userId);
	}
	
}
