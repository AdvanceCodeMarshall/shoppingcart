package com.shoppingcart.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.beans.response.ApiResponse;
import com.shoppingcart.jwtconfig.ShoppingCartConfig;
import com.shoppingcart.model.AddToCart;
import com.shoppingcart.model.CheckoutCart;
import com.shoppingcart.service.cartService.CartService;
import com.shoppingcart.service.productServices.ProductServices;

@RestController
@RequestMapping("api/order")
public class OrderController {

	@Autowired
	CartService cartService;
	
	@Autowired
	ProductServices productServices;
	
	@PostMapping("/checkoutcart")
	public ResponseEntity<?> checkoutOrder(@RequestBody HashMap<String, String> addCartRequest){
		try {
			String keys[] = {"userId","totalPrice","paymentType","deliveryAddress"};
			if(ShoppingCartConfig.validationWithHashMap(keys, addCartRequest)) {
				
				
			}
			Long userId = Long.parseLong(addCartRequest.get("userId"));
			Double totalAmt = Double.parseDouble(addCartRequest.get("totalPrice"));
			if (cartService.checkTotalAmountAgainstCart(totalAmt, userId)) {
				List<AddToCart> cartItems = cartService.getCartByUserId(userId);
				List<CheckoutCart> checkoutCarts = new ArrayList<CheckoutCart>();
				for (AddToCart addCart : cartItems) {
					String orderId = ""+getOrderId();
					CheckoutCart cart = new CheckoutCart();
					cart.setUserId(userId);
					cart.setOrderId(orderId);
					cart.setPaymentType(addCartRequest.get("paymentType"));
					cart.setPrice(totalAmt);
					cart.setProduct(addCart.getProduct());
					cart.setQty(addCart.getQty());
					cart.setDeliveryAddress(addCartRequest.get("deliveryAddress"));
					checkoutCarts.add(cart);
					
				}
				cartService.saveProductsForCheckout(checkoutCarts);
				return ResponseEntity.ok(new ApiResponse("Order placed successfully", ""));
			}else {
				throw new Exception("Total amount is mismatch");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
		}
	}
	
	@GetMapping("orders/{userid}")
	public ResponseEntity<?> getOrdersByUserId(@PathVariable(name = "userid") Long userId) {
		try {
			List<CheckoutCart> checkoutCarts = cartService.getAllCheckoutByUserId(userId);
			return ResponseEntity.ok(checkoutCarts);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
		}
	
	}
	
	public int getOrderId() {
	    Random r = new Random( System.currentTimeMillis() );
	    return 10000 + r.nextInt(20000);
	}
}
