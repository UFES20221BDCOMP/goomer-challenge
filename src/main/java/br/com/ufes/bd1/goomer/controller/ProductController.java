package br.com.ufes.bd1.goomer.controller;

import br.com.ufes.bd1.goomer.service.ProductService;
import br.com.ufes.bd1.goomer.dto.ProductDto;
import br.com.ufes.bd1.goomer.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value ="/product", produces = "application/json")
public class ProductController extends AbstractController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Integer id) {
        Product product = productService.getById(id);
        return ResponseEntity.ok(ProductDto.fromEntity(product));
    }

    @PostMapping
    public ResponseEntity<String> registerProduct(@Valid @RequestBody ProductDto productDto) throws URISyntaxException {
        Product product = productDto.toEntity();
        productService.save(product);

        return ResponseEntity.created(
                new URI("http://localhost:8080/product/" + product.getId())).build();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping
    public void updateProduct(@Valid @RequestBody ProductDto productDto) {
        Product product = productDto.toEntity();
        productService.update(product);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteById(id);
    }


}