package br.com.ufes.bd1.goomer.controller;

import br.com.ufes.bd1.goomer.service.ProductService;
import br.com.ufes.bd1.goomer.dto.ProductDto;
import br.com.ufes.bd1.goomer.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value ="/product", produces = "application/json")
public class ProductController extends AbstractController{

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Object> doGet(@RequestParam Optional<Integer> id) {
        if (id.isPresent()) {
            Product product = productService.getById(id.get());
            return ResponseEntity.ok(ProductDto.fromEntity(product));
        }
        else {
            Collection<Product> products = productService.getAll();
            List<ProductDto> productDtos = products.stream().map(ProductDto::fromEntity).collect(Collectors.toList());

            return ResponseEntity.ok(productDtos);
        }
    }

    @PostMapping
    public ResponseEntity<String> doPost(@Valid @RequestBody ProductDto productDto) throws URISyntaxException {
        Product product = productDto.toEntity();
        productService.save(product);

        return ResponseEntity.created(new URI("http://localhost:8080/product/" + product.getId())).build();
    }

    @PutMapping
    public ResponseEntity<String> doPut(@Valid @RequestBody ProductDto productDto) throws URISyntaxException {
        Product product = productDto.toEntity();
        productService.update(product);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<String> doDelete(@RequestParam Integer id) {
        productService.deleteById(id);

        return ResponseEntity.ok().build();
    }


}