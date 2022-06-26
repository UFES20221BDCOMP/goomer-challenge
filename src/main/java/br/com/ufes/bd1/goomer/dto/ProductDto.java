package br.com.ufes.bd1.goomer.dto;

import br.com.ufes.bd1.goomer.model.Product;
import br.com.ufes.bd1.goomer.model.ProductCategory;
import br.com.ufes.bd1.goomer.model.Restaurant;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class ProductDto {

    @JsonProperty("id")
    Integer id;

    @JsonProperty("name")
    String name;

    @JsonProperty("price")
    BigDecimal price;

    @JsonProperty("image_path")
    String imagePath;

    @JsonProperty("category")
    String category;

    @JsonProperty("restaurant_id")
    Integer restaurantId;

    @JsonProperty("sale")
    SaleDto sale;


    public static ProductDto fromEntity(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.id = product.getId();
        productDto.name = product.getDescription();
        productDto.price = product.getPrice();
        productDto.imagePath = product.getImagePath();
        productDto.category = product.getProductCategory().getName();
        productDto.restaurantId = product.getRestaurant().getId();
        productDto.sale = SaleDto.fromEntity(product.getSale());
        return productDto;
    }

    public Product toEntity() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(category);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        Product product = new Product();
        product.setDescription(name);
        product.setPrice(price);
        product.setImagePath(imagePath);
        product.setProductCategory(productCategory);
        product.setRestaurant(restaurant);
        product.setSale(sale.toEntity());
        return product;
    }
}
