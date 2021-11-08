package com.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoppingcart.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
