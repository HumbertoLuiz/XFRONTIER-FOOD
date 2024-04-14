package com.br.nofrontier.food.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.nofrontier.food.domain.model.Product;
import com.br.nofrontier.food.domain.model.ProductImage;
import com.br.nofrontier.food.domain.model.Restaurant;

import jakarta.transaction.Transactional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryQueries {


	@Query("from Product p where p.id = :id and p.restaurants = :restaurantId")
	Optional<Product> findById(@Param("restaurantId") Long restaurantId,
							   @Param("id") Long id);
	
	List<Product> findAllByRestaurants(Restaurant restaurants);

	@Query("from Product p where p.active = true and p.restaurants = :restaurants")
	List<Product> findActivesByRestaurants(Restaurant restaurants);

//	@Query("SELECT i FROM ProductImage i join i.products p WHERE p.restaurant.id = :restaurantId and i.product.id = :productId")
//	Optional<ProductImage> findImageById(Long restaurantId, Long productId);
	
	@Query("SELECT pi FROM ProductImage pi WHERE pi.id = :imageId")
    Optional<ProductImage> findImageById(@Param("imageId") Long imageId);

	ProductImage save(ProductImage image);

	
	@Transactional
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("delete from ProductImage pi where pi.id = :id")
	void removeProductImage(Long id);
}