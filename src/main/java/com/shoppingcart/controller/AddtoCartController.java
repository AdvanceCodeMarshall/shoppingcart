package com.shoppingcart.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.beans.response.ApiResponse;
import com.shoppingcart.jwtconfig.ShoppingCartConfig;
import com.shoppingcart.model.AddToCart;
import com.shoppingcart.service.cartService.CartService;

@RestController
@RequestMapping("api/addtocart")
public class AddtoCartController {
	
	@Autowired
	private CartService cartService;
	
	@PostMapping(value = "/addcartwithproduct")
	public ResponseEntity<?> addCartWithProduct(@RequestBody HashMap<String, String> addCartRequest) {

		try {
			String keys[] = { "productId", "userId", "qty", "price" };
			if (ShoppingCartConfig.validationWithHashMap(keys, addCartRequest)) {

			}
			Long productId = Long.parseLong(addCartRequest.get("productId"));
			Long userId = Long.parseLong(addCartRequest.get("userId"));
			Integer qty = Integer.parseInt(addCartRequest.get("qty"));
			Double price = Double.parseDouble(addCartRequest.get("price"));
			List<AddToCart> obj = cartService.addToaddCartByUserIdAndProductId(productId, userId, qty, price);
			return ResponseEntity.ok(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
		}
	}
	
	@PostMapping("/updateqtyforproduct")
	public ResponseEntity<?> updateQtyForProduct(@RequestBody HashMap<String, String> updateCartRequest) {
		try {
			String keys[] = { "cartId", "userId", "qty", "price" };
			if (ShoppingCartConfig.validationWithHashMap(keys, updateCartRequest)) {

			}
			Long cartId = Long.parseLong(updateCartRequest.get("cartId"));
			Long userId = Long.parseLong(updateCartRequest.get("userId"));
			Integer qty = Integer.parseInt(updateCartRequest.get("qty"));
			Double price = Double.parseDouble(updateCartRequest.get("price"));
			cartService.updateQtyByCartId(cartId, qty, price);
			List<AddToCart> obj = cartService.getCartByUserId(userId);
			return ResponseEntity.ok(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
		}
	}
	
	@PostMapping("/removeproductfromcart")
  	public ResponseEntity<?> removeCartwithProductId(@RequestBody HashMap<String,String> removeCartRequest) {
		try {
			String keys[] = {"userId","cartId"};
			if(ShoppingCartConfig.validationWithHashMap(keys, removeCartRequest)) {
				
			}
			Long cartId = Long.parseLong(removeCartRequest.get("cartId"));
			Long userId = Long.parseLong(removeCartRequest.get("userId"));
			List<AddToCart> obj = cartService.removeCartByUserId(cartId,userId);
			return ResponseEntity.ok(obj);
		}catch(Exception e) {
				return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
		}		
   }
	
	@PostMapping("/getcartsbyuserid")
  	public ResponseEntity<?> getCartsByUserId(@RequestBody HashMap<String,String> getCartRequest) {
		try {
			String keys[] = {"userId"};
			if(ShoppingCartConfig.validationWithHashMap(keys, getCartRequest)) {
			}
			Long userId = Long.parseLong(getCartRequest.get("userId"));
			List<AddToCart> obj = cartService.getCartByUserId(userId);
			return ResponseEntity.ok(obj);
		}catch(Exception e) {
				return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
		}	
   }
}
