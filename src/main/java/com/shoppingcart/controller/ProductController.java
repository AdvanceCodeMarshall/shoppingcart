package com.shoppingcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.model.Products;
import com.shoppingcart.service.productServices.ProductServices;


@RestController
@RequestMapping("api/product")
public class ProductController {

	@Autowired
	private ProductServices productServices;

	@GetMapping(value = "/allproducts")
	public ResponseEntity<List<Products>> getAllProducts() {
		List<Products> products = productServices.getAllProducts();
		return new ResponseEntity<List<Products>>(products,HttpStatus.OK);
	}
}
