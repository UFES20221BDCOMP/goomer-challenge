package br.com.ufes.bd1.goomer.dto;

import br.com.ufes.bd1.goomer.model.ProductSale;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class SaleDto {

    @NotBlank(message = "sale description must be provided")
    @JsonProperty("description")
    String description;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 10, fraction = 2)
    @NotNull(message = "promotional price must be provided")
    @JsonProperty("price")
    BigDecimal price;

    @Valid
    @NotEmpty(message = "sale validity periods must be provided")
    @JsonProperty("sales_hours")
    List<TimespanDto> salesHours;


    public static SaleDto fromEntity(ProductSale sale) {
        if (Objects.isNull(sale)) {
            return null;
        }
        SaleDto saleDto = new SaleDto();
        saleDto.description = sale.getDescription();
        saleDto.price = sale.getPrice();
        saleDto.salesHours = sale.getSaleValidityPeriods().stream().map(TimespanDto::fromEntity).collect(Collectors.toList());
        return saleDto;
    }

    public ProductSale toEntity() {
        ProductSale sale = new ProductSale();
        sale.setDescription(description);
        sale.setPrice(price);
        sale.setSaleValidityPeriods(salesHours.stream().map(TimespanDto::toEntity).collect(Collectors.toList()));
        return sale;
    }
}
