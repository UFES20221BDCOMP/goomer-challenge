package br.com.ufes.bd1.goomer.dto;

import br.com.ufes.bd1.goomer.model.ProductSale;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SaleDto {

    @JsonProperty("description")
    String description;

    @JsonProperty("price")
    BigDecimal price;

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
