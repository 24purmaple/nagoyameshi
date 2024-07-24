package com.example.nagoyameshi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.RestaurantCategory;

public interface RestaurantCategoryRepository extends JpaRepository<RestaurantCategory, Integer> {
	// レストランIDでレストランカテゴリを取得する
	public List<RestaurantCategory> findByRestaurantId(Integer restaurantId);
	//カテゴリ検索最小価格昇順
	@Query("SELECT rc FROM RestaurantCategory rc JOIN rc.restaurant r WHERE rc.category.id = :categoryId ORDER BY r.minPrice ASC")
	public Page<RestaurantCategory> findByCategoryIdOrderByRestaurantMinPriceAsc(Integer categoryId, Pageable pageable);
	//カテゴリ検索最小価格降順
	@Query("SELECT rc FROM RestaurantCategory rc JOIN rc.restaurant r WHERE rc.category.id = :categoryId ORDER BY r.minPrice DESC")
	public Page<RestaurantCategory> findByCategoryIdOrderByRestaurantMinPriceDesc(Integer categoryId, Pageable pageable);
	// カテゴリ検索最大価格昇順
	@Query("SELECT rc FROM RestaurantCategory rc JOIN rc.restaurant r WHERE rc.category.id = :categoryId ORDER BY r.maxPrice ASC")
    public Page<RestaurantCategory> findByCategoryIdOrderByRestaurantMaxPriceAsc(Integer categoryId, Pageable pageable); 
	// カテゴリ検索最大価格降順
    @Query("SELECT rc FROM RestaurantCategory rc JOIN rc.restaurant r WHERE rc.category.id = :categoryId ORDER BY r.maxPrice DESC")
    public Page<RestaurantCategory> findByCategoryIdOrderByRestaurantMaxPriceDesc(Integer categoryId, Pageable pageable); 
    //カテゴリ検索登録昇順
	@Query("SELECT rc FROM RestaurantCategory rc JOIN rc.restaurant r WHERE rc.category.id = :categoryId ORDER BY r.createdAt ASC")
	public Page<RestaurantCategory> findByCategoryIdOrderByRestaurantCreatedAtAsc(Integer categoryId, Pageable pageable);
	//カテゴリ検索登録降順
	@Query("SELECT rc FROM RestaurantCategory rc JOIN rc.restaurant r WHERE rc.category.id = :categoryId ORDER BY r.createdAt DESC")
	public Page<RestaurantCategory> findByCategoryIdOrderByRestaurantCreatedAtDesc(Integer categoryId, Pageable pageable);
	
	//レストランを削除する
	public void deleteByRestaurant(Restaurant restaurant);
	//カテゴリを削除する
	public void deleteByCategory(Category category);
}