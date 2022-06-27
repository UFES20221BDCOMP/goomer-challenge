package br.com.ufes.bd1.goomer.controller;

import br.com.ufes.bd1.goomer.dto.RestaurantDto;
import br.com.ufes.bd1.goomer.model.Restaurant;
import br.com.ufes.bd1.goomer.service.RestaurantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<Object> doGet(@Valid @RequestParam Optional<Integer> id) {
        if (id.isPresent()) {
            Restaurant restaurant = restaurantService.getById(id.get());

            return ResponseEntity.ok(
                    RestaurantDto.fromEntity(restaurant));
        }
        else {
            List<RestaurantDto> restaurants = restaurantService.getAll()
                    .stream().map(RestaurantDto::fromEntity)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(restaurants);
        }
    }

    @PostMapping("/restaurant")
    public ResponseEntity<String> doPostRestaurant(@Valid @RequestBody RestaurantDto restaurantDto) throws URISyntaxException {
        Restaurant restaurant = restaurantDto.toEntity();
        restaurantService.save(restaurant);

        return ResponseEntity.created(new URI("http://localhost:8080?id=" + restaurant.getId())).build();
    }

    @PutMapping
    public ResponseEntity<String> doPut(@Valid @RequestBody RestaurantDto restaurantDto) {
        Restaurant restaurant = restaurantDto.toEntity();
        restaurantService.update(restaurant);

        return ResponseEntity.ok("Updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> doDelete(@PathVariable int id) {
        restaurantService.deleteById(id);

        return ResponseEntity.ok("Deleted");
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