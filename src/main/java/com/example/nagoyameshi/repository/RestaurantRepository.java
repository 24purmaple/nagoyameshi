package com.example.nagoyameshi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {	
	public Page<Restaurant> findByRestaurantNameLike(String keyword, Pageable pageable);
	public Page<Restaurant> findAllByOrderByMinPriceAsc(Pageable pageable);//全店舗検索最低価格昇順
	public Page<Restaurant> findAllByOrderByMinPriceDesc(Pageable pageable);//全店舗検索最低価格降順
	public Page<Restaurant> findAllByOrderByMaxPriceAsc(Pageable pageable); // 全店舗検索最高価格昇順
    public Page<Restaurant> findAllByOrderByMaxPriceDesc(Pageable pageable); // 全店舗検索最高価格降順
	public Page<Restaurant> findAllByOrderByCreatedAtAsc(Pageable pageable);//全店舗検索登録昇順
	public Page<Restaurant> findAllByOrderByCreatedAtDesc(Pageable pageable);//全店舗検索登録降順
	public Page<Restaurant> findByRestaurantNameLikeOrAddressLikeOrderByMinPriceAsc(String keyword, String address, Pageable pageable);//店舗名または住所検索最小価格昇順
	public Page<Restaurant> findByRestaurantNameLikeOrAddressLikeOrderByMinPriceDesc(String keyword, String address, Pageable pageable);//店舗名または住所検索最小価格降順
	public Page<Restaurant> findByRestaurantNameLikeOrAddressLikeOrderByMaxPriceAsc(String keyword, String address, Pageable pageable);//店舗名または住所検索最高価格昇順
	public Page<Restaurant> findByRestaurantNameLikeOrAddressLikeOrderByMaxPriceDesc(String keyword, String address, Pageable pageable);//店舗名または住所検索最高価格降順
	public Page<Restaurant> findByRestaurantNameLikeOrAddressLikeOrderByCreatedAtAsc(String keyword, String address, Pageable pageable);//店舗名または住所検索登録昇順
	public Page<Restaurant> findByRestaurantNameLikeOrAddressLikeOrderByCreatedAtDesc(String keyword, String address, Pageable pageable);//店舗名または住所検索登録降順
	public Page<Restaurant> findByMinPriceLessThanEqualOrderByMinPriceAsc(Integer minPrice, Pageable pageable);//予算検索最小価格昇順
	public Page<Restaurant> findByMinPriceLessThanEqualOrderByMinPriceDesc(Integer minPrice, Pageable pageable);//予算検索最小価格降順
	public Page<Restaurant> findByMaxPriceLessThanEqualOrderByMaxPriceAsc(Integer maxPrice, Pageable pageable);//予算検索最高価格昇順
	public Page<Restaurant> findByMaxPriceLessThanEqualOrderByMaxPriceDesc(Integer maxPrice, Pageable pageable);//予算検索最高価格降順
	public Page<Restaurant> findByMinPriceLessThanEqualOrderByCreatedAtAsc(Integer minPrice, Pageable pageable);//予算検索登録昇順
	public Page<Restaurant> findByMinPriceLessThanEqualOrderByCreatedAtDesc(Integer minPrice, Pageable pageable);//予算検索登録降順
}