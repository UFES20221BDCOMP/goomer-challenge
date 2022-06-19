package br.com.ufes.bd1.goomer.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value ="/product", produces = "application/json")
public class ProductController {


    @GetMapping("/{id}")
    public String getProduct(@PathVariable Integer id) {
        return id.toString();
    }

    @PostMapping
    public String registerProduct(@RequestBody String body) {
        return body;
    }

    @PutMapping
    public String updateProduct(@RequestBody String body) {
        return body;
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        return id.toString();
    }
}