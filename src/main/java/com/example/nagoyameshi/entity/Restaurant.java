package com.example.nagoyameshi.entity;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import com.example.nagoyameshi.convert.ListToJsonConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "restaurants")
@Data
public class Restaurant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "restaurant_name")
	private String restaurantName;
	
	@Column(name = "image_name")
	private String imageName;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "min_price")
	private Integer minPrice;
	
	@Column(name = "max_price")
	private Integer maxPrice;
	
	@Column(name = "capacity")
	private Integer capacity;
	
	@Column(name = "opening_time")
	private LocalTime openingTime;
	
	@Column(name = "closing_time")
	private LocalTime closingTime;
	
	@Convert(converter = ListToJsonConverter.class)
    @Column(columnDefinition = "json")
    private List<String> closedDays;  // 例: "月", "火", "祝" などの文字列
	
	@Column(name = "postal_code")
	private String postalCode;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "created_at", insertable = false, updatable = false)
	private Timestamp createdAt;
	
	@Column(name = "updated_at", insertable = false, updatable = false)
	private Timestamp updatedAt;
	
	@OneToMany(mappedBy = "restaurant")
	private Set<RestaurantCategory> restaurantsCategory;
	
}