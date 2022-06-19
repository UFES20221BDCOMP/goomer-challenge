package br.com.ufes.bd1.goomer.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value ="/restaurant", produces = "application/json")
public class RestaurantController {

    @GetMapping
    public String getRestaurants() {
        return "restaurants";
    }

    @GetMapping("/{id}")
    public String getRestaurant(@PathVariable Integer id) {
        return id.toString();
    }

    @PostMapping
    public String registerRestaurant(@RequestBody String body) {
        return body;
    }

    @PutMapping
    public String updateRestaurant(@RequestBody String body) {
        return body;
    }

    @DeleteMapping("/{id}")
    public String deleteRestaurant(@PathVariable Integer id) {
        return id.toString();
    }

    @GetMapping("/{id}/inventory")
    public String getInventory(@PathVariable Integer id,
                               @RequestParam Optional<String> category) {
        String retorno = id.toString();

        if (category.isPresent()) {
            retorno += " - " + category.get();
        }

        return retorno;
    }

}