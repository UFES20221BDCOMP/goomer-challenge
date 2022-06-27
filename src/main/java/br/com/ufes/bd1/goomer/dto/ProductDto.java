package br.com.ufes.bd1.goomer.dto;

import br.com.ufes.bd1.goomer.model.Product;
import br.com.ufes.bd1.goomer.model.ProductCategory;
import br.com.ufes.bd1.goomer.model.Restaurant;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
public class ProductDto {

    @JsonProperty("id")
    Integer id;

    @NotBlank(message = "name must be provided")
    @JsonProperty("name")
    String name;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 10, fraction = 2)
    @NotNull(message = "price must be provided")
    @JsonProperty("price")
    BigDecimal price;

    @JsonProperty("image_path")
    String imagePath;

    @NotBlank(message = "category must be provided")
    @JsonProperty("category")
    String category;

    @NotNull(message = "restaurant id must be provided")
    @JsonProperty("restaurant_id")
    Integer restaurantId;

    @Valid
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
        product.setId(id);
        product.setDescription(name);
        product.setPrice(price);
        product.setImagePath(imagePath);
        product.setProductCategory(productCategory);
        product.setRestaurant(restaurant);
        if (Objects.nonNull(sale)) {
            product.setSale(sale.toEntity());
        }
        return product;
    }
}
