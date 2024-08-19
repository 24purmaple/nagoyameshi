package com.example.nagoyameshi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.RestaurantCategory;
import com.example.nagoyameshi.form.RestaurantEditForm;
import com.example.nagoyameshi.form.RestaurantRegisterForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.RestaurantCategoryRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;

@Service
public class RestaurantService {
	private final RestaurantRepository restaurantRepository;
	private final RestaurantCategoryRepository restaurantCategoryRepository;
	private final CategoryRepository categoryRepository;
	
	public RestaurantService(RestaurantRepository restaurantRepository, RestaurantCategoryRepository restaurantCategoryRepository, CategoryRepository categoryRepository) {
		this.restaurantRepository = restaurantRepository;
		this.restaurantCategoryRepository = restaurantCategoryRepository;
		this.categoryRepository = categoryRepository;
	}
	
	@Transactional
	public void create(RestaurantRegisterForm restaurantRegisterForm) {
		Restaurant restaurant = new Restaurant();
		MultipartFile imageFile = restaurantRegisterForm.getImageFile();
		
		if (!imageFile.isEmpty()) {
			String imageName = imageFile.getOriginalFilename();	//元のファイル名を取得する
			String hashedImageName = generateNewFileName(imageName);
			Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
			copyImageFile(imageFile, filePath);
			restaurant.setImageName(hashedImageName);
		}
		
		restaurant.setRestaurantName(restaurantRegisterForm.getRestaurantName());
		restaurant.setDescription(restaurantRegisterForm.getDescription());
		restaurant.setMinPrice(restaurantRegisterForm.getMinPrice());
		restaurant.setMaxPrice(restaurantRegisterForm.getMaxPrice());
		restaurant.setCapacity(restaurantRegisterForm.getCapacity());
		restaurant.setOpeningTime(restaurantRegisterForm.getOpeningTime());
		restaurant.setClosingTime(restaurantRegisterForm.getClosingTime());
		restaurant.setClosedDays(restaurantRegisterForm.getClosedDays());
		restaurant.setPostalCode(restaurantRegisterForm.getPostalCode());
		restaurant.setAddress(restaurantRegisterForm.getAddress());
		restaurant.setPhoneNumber(restaurantRegisterForm.getPhoneNumber());		
		
		restaurantRepository.save(restaurant);
		
		//カテゴリ情報の設定
		List<Integer> categoryIds = restaurantRegisterForm.getCategoryIds();
		for(Integer categoryId : categoryIds) {
			Category category = categoryRepository.getReferenceById(categoryId);
			RestaurantCategory restaurantCategory = new RestaurantCategory();
			restaurantCategory.setRestaurant(restaurant);
			restaurantCategory.setCategory(category);
			restaurantCategoryRepository.save(restaurantCategory);
		}
				
	}
	
	@Transactional
	public void update(RestaurantEditForm restaurantEditForm) {
		Restaurant restaurant = restaurantRepository.getReferenceById(restaurantEditForm.getRestaurantId());
		MultipartFile imageFile = restaurantEditForm.getImageFile();
		
		if (!imageFile.isEmpty()) {
			String imageName = imageFile.getOriginalFilename();	//元のファイル名を取得する
			String hashedImageName = generateNewFileName(imageName);
			Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
			copyImageFile(imageFile, filePath);
			restaurant.setImageName(hashedImageName);
		}
		
		restaurant.setRestaurantName(restaurantEditForm.getRestaurantName());
		restaurant.setDescription(restaurantEditForm.getDescription());
		restaurant.setMinPrice(restaurantEditForm.getMinPrice());
		restaurant.setMaxPrice(restaurantEditForm.getMaxPrice());
		restaurant.setCapacity(restaurantEditForm.getCapacity());
		restaurant.setOpeningTime(restaurantEditForm.getOpeningTime());
		restaurant.setClosingTime(restaurantEditForm.getClosingTime());
		restaurant.setClosedDays(restaurantEditForm.getClosedDays());
		restaurant.setPostalCode(restaurantEditForm.getPostalCode());
		restaurant.setAddress(restaurantEditForm.getAddress());
		restaurant.setPhoneNumber(restaurantEditForm.getPhoneNumber());
		
		restaurantRepository.save(restaurant);
		
		//既存のカテゴリ情報を削除
		List<RestaurantCategory> existingCategories = restaurantCategoryRepository.findByRestaurantId(restaurant.getId());
		restaurantCategoryRepository.deleteAll(existingCategories);
		
		//カテゴリ情報の設定
		List<Integer> categoryIds = restaurantEditForm.getCategoryIds();
		for(Integer categoryId : categoryIds) {
			Category category = categoryRepository.getReferenceById(categoryId);
			RestaurantCategory restaurantCategory = new RestaurantCategory();
			restaurantCategory.setRestaurant(restaurant);
			restaurantCategory.setCategory(category);
			restaurantCategoryRepository.save(restaurantCategory);
		}
		
	}

	// UUIDを使って生成したファイル名を返す
		//ファイル名の変更処理
	public String generateNewFileName(String fileName) {
		String[] fileNames = fileName.split("\\.");			//1.fileNameを「.」で区切る　区切ったものがfileNames (配列が{restaurant, png}になってる)
		for (int i = 0; i < fileNames.length - 1; i++) {	//2.fileNames（restaurant）がランダムなUUIDに書き変わる
			fileNames[i] = UUID.randomUUID().toString();
		}
		String hashedFileName = String.join(".", fileNames);//3.それに「.」「fileNames(png)」がくっついたものが hashedFileName
		return hashedFileName;								//4.それをreturnする
	}
	
	//画像ファイルを指定したファイルにコピーする
		//ファイルのコピー処理
	public void copyImageFile(MultipartFile imageFile, Path filePath) {
		try {
			Files.copy(imageFile.getInputStream(), filePath);	//MultipartFileインターフェースが提供するメソッド。ファイルの内容を読み取るためのInputStreamオブジェクトを取得する。
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Page<Restaurant> searchRestaurants(String keyword, String address, Integer categoryId, Integer price, String order, Pageable pageable) {
        Page<Restaurant> restaurantPage;

        if (keyword != null && !keyword.isEmpty()) {
            if (order != null && order.equals("minPriceAsc")) {
                restaurantPage = restaurantRepository.findByRestaurantNameLikeOrAddressLikeOrderByMinPriceAsc("%" + keyword + "%", "%" + address + "%", pageable);
            } else if (order != null && order.equals("maxPriceDesc")) {
                restaurantPage = restaurantRepository.findByRestaurantNameLikeOrAddressLikeOrderByMaxPriceDesc("%" + keyword + "%", "%" + address + "%", pageable);
            } else if (order != null && order.equals("createdAtAsc")) {
                restaurantPage = restaurantRepository.findByRestaurantNameLikeOrAddressLikeOrderByCreatedAtAsc("%" + keyword + "%", "%" + address + "%", pageable);
            } else {
                restaurantPage = restaurantRepository.findByRestaurantNameLikeOrAddressLikeOrderByCreatedAtDesc("%" + keyword + "%", "%" + address + "%", pageable);
            }
        } else if (categoryId != null) {
            if (order != null && order.equals("minPriceAsc")) {
                restaurantPage = restaurantCategoryRepository.findByCategoryIdOrderByRestaurantMinPriceAsc(categoryId, pageable).map(RestaurantCategory::getRestaurant);
            } else if (order != null && order.equals("maxPriceDesc")) {
                restaurantPage = restaurantCategoryRepository.findByCategoryIdOrderByRestaurantMaxPriceDesc(categoryId, pageable).map(RestaurantCategory::getRestaurant);
            } else if (order != null && order.equals("createdAtAsc")) {
                restaurantPage = restaurantCategoryRepository.findByCategoryIdOrderByRestaurantCreatedAtAsc(categoryId, pageable).map(RestaurantCategory::getRestaurant);
            } else {
                restaurantPage = restaurantCategoryRepository.findByCategoryIdOrderByRestaurantCreatedAtDesc(categoryId, pageable).map(RestaurantCategory::getRestaurant);
            }
        } else if (price != null) {
            if (order != null && order.equals("minPriceAsc")) {
                restaurantPage = restaurantRepository.findByMinPriceLessThanEqualOrderByMinPriceAsc(price, pageable);
            } else if (order != null && order.equals("maxPriceDesc")) {
                restaurantPage = restaurantRepository.findByMaxPriceLessThanEqualOrderByMaxPriceDesc(price, pageable);
            } else if (order != null && order.equals("createdAtAsc")) {
                restaurantPage = restaurantRepository.findByMinPriceLessThanEqualOrderByCreatedAtAsc(price, pageable);
            } else {
                restaurantPage = restaurantRepository.findByMinPriceLessThanEqualOrderByCreatedAtDesc(price, pageable);
            }
        } else if(keyword == null|| keyword.isEmpty() && categoryId == null &&price == null){
        	if (order != null && order.equals("minPriceAsc")) {
                restaurantPage = restaurantRepository.findAllByOrderByMinPriceAsc(pageable);
            } else if (order != null && order.equals("maxPriceDesc")) {
                restaurantPage = restaurantRepository.findAllByOrderByMaxPriceDesc(pageable);
            } else if (order != null && order.equals("createdAtAsc")) {
                restaurantPage = restaurantRepository.findAllByOrderByCreatedAtAsc(pageable);
            } else {
                restaurantPage = restaurantRepository.findAllByOrderByCreatedAtDesc(pageable);
            }
        } else {
            restaurantPage = restaurantRepository.findAll(pageable);
        }

        return restaurantPage;
    }

}