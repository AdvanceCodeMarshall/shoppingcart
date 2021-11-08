package com.shoppingcart.service.productServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.model.Category;
import com.shoppingcart.model.Products;
import com.shoppingcart.repository.CategoryRepository;
import com.shoppingcart.repository.ProductRepository;

@Service
public class ProductServices {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public List<Products> getAllProducts() {
		return productRepository.findAll();
	}

	public List<Products> getProductsByCategory(Long productId) {
		return productRepository.getByCategoryId(productId);
	}

	public List<Category> getAllCategory() {
		return categoryRepository.findAll();
	}

	public Products getProductsById(long productId) throws Exception {
		return productRepository.findById(productId).orElseThrow(() -> new Exception("Product is not found"));
	}
}
