package br.com.ufes.bd1.goomer.dto;

import br.com.ufes.bd1.goomer.model.Restaurant;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantDto {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("image_path")
    private String imagePath;

    @JsonProperty("address")
    private AddressDto address;

    @JsonProperty("business_hours")
    private List<TimespanDto> businessHours;


    public Restaurant toEntity() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setName(name);
        restaurant.setImagePath(imagePath);
        restaurant.setAddress(address.toEntity());
        restaurant.setBusinessHours(businessHours.stream().map(TimespanDto::toEntity).collect(Collectors.toList()));
        return restaurant;
    }

    public static RestaurantDto fromEntity(Restaurant restaurant) {
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.id = restaurant.getId();
        restaurantDto.name = restaurant.getName();
        restaurantDto.imagePath = restaurant.getImagePath();
        restaurantDto.address = AddressDto.fromEntity(restaurant.getAddress());
        restaurantDto.businessHours = restaurant.getBusinessHours().stream().map(TimespanDto::fromEntity).collect(Collectors.toList());
        return restaurantDto;
    }
}
