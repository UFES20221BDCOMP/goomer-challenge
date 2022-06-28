package br.com.ufes.bd1.goomer.controller;

import br.com.ufes.bd1.goomer.dto.ProductDto;
import br.com.ufes.bd1.goomer.dto.RestaurantDto;
import br.com.ufes.bd1.goomer.model.Restaurant;
import br.com.ufes.bd1.goomer.service.RestaurantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value ="/restaurant", produces = "application/json")
public class RestaurantController extends AbstractController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants() {
        List<RestaurantDto> restaurants = restaurantService.getAll()
                .stream().map(RestaurantDto::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable Integer id) {
        Restaurant restaurant = restaurantService.getById(id);

        return ResponseEntity.ok(
                RestaurantDto.fromEntity(restaurant));
    }

    @PostMapping
    public ResponseEntity<String> registerRestaurant(@Valid @RequestBody RestaurantDto restaurantDto) throws URISyntaxException {
        Restaurant restaurant = restaurantDto.toEntity();
        restaurantService.save(restaurant);

        return ResponseEntity.created(
                new URI("http://localhost:8080/" + restaurant.getId())).build();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping
    public void updateRestaurant(@Valid @RequestBody RestaurantDto restaurantDto) {
        Restaurant restaurant = restaurantDto.toEntity();
        restaurantService.update(restaurant);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable int id) {
        restaurantService.deleteById(id);
    }

    @GetMapping("/{id}/menu")
    public ResponseEntity<List<ProductDto>> getProducts(@PathVariable Integer id) {

        return ResponseEntity
                .ok(restaurantService.getMenu(id)
                .stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList()));
    }

}