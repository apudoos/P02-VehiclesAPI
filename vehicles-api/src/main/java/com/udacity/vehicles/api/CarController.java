package com.udacity.vehicles.api;


import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.service.CarService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Implements a REST-based controller for the Vehicles API.
 */
@RestController
@RequestMapping("/cars")
class CarController {

    private final CarService carService;
    private final CarResourceAssembler assembler;

    CarController(CarService carService, CarResourceAssembler assembler) {
        this.carService = carService;
        this.assembler = assembler;
    }

    /**
     * Creates a list to store any vehicles.
     * @return list of vehicles
     */
    @GetMapping (produces = { MediaType.APPLICATION_JSON_VALUE } )

    @ApiOperation(value = "Find All Cars", notes="Find all Cars in the inventory")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "Cars found"),
            @ApiResponse(code = 404,message = "Cars not found") })
    Resources<Resource<Car>> list() {
        List<Resource<Car>> resources = carService.list().stream().map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(resources,
                linkTo(methodOn(CarController.class).list()).withSelfRel());
    }

    /**
     * Gets information of a specific car by ID.
     * @param id the id number of the given vehicle
     * @return all information for the requested vehicle
     */
    @GetMapping(value="/{id}"
            ,produces = { MediaType.APPLICATION_JSON_VALUE })
    @ApiOperation(value = "Find Car By Id", notes="Find Car by id in the inventory")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "Car found"),
            @ApiResponse(code = 404,message = "Car not found") })
    Resource<Car> get(@PathVariable Long id) {
        /**
         * TODO: Use the `findById` method from the Car Service to get car information.
         * TODO: Use the `assembler` on that car and return the resulting output.
         *   Update the first line as part of the above implementing.
         */
        Car car = carService.findById(id);
        return assembler.toResource(car);
    }

    /**
     * Posts information to create a new vehicle in the system.
     * @param car A new vehicle to add to the system.
     * @return response that the new vehicle was added to the system
     * @throws URISyntaxException if the request contains invalid fields or syntax
     */
    @PostMapping (consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
                  produces = { MediaType.APPLICATION_JSON_VALUE }
                  )
    @ApiOperation(value = "Add a new Car", notes="Add a new Car")
    @ApiResponses(value = {
            @ApiResponse(code = 201,message = "New Car Added"),
            @ApiResponse(code = 404,message = "Car not Added") })
    ResponseEntity<?> post(@Valid @RequestBody Car car) throws URISyntaxException {
        /**
         * TODO: Use the `save` method from the Car Service to save the input car.
         * TODO: Use the `assembler` on that saved car and return as part of the response.
         *   Update the first line as part of the above implementing.
         */
        System.out.println(car.toString());
        Car carFromDB = carService.save(car);
        Resource<Car> resource = assembler.toResource(carFromDB);
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    /**
     * Updates the information of a vehicle in the system.
     * @param id The ID number for which to update vehicle information.
     * @param car The updated information about the related vehicle.
     * @return response that the vehicle was updated in the system
     */
    @PutMapping(value="/{id}" ,produces = { "application/hal+json" })
    @ApiOperation(value = "Updated a Car", notes="Updated a Car")
    @ApiResponses(value = {
            @ApiResponse(code = 201,message = "Car Updated"),
            @ApiResponse(code = 404,message = "Car not Updated") })
    ResponseEntity<?> put(@PathVariable Long id, @Valid @RequestBody Car car) {
        /**
         * TODO: Set the id of the input car object to the `id` input.
         * TODO: Save the car using the `save` method from the Car service
         * TODO: Use the `assembler` on that updated car and return as part of the response.
         *   Update the first line as part of the above implementing.
         */
        car.setId(id);
        Car carFromDB = carService.save(car);
        carFromDB.setId(id);
        Resource<Car> resource = assembler.toResource(carFromDB);
        return ResponseEntity.ok(resource);
    }

    /**
     * Removes a vehicle from the system.
     * @param id The ID number of the vehicle to remove.
     * @return response that the related vehicle is no longer in the system
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a Car", notes="Delete a Car from inventory")
    @ApiResponses(value = {
            @ApiResponse(code = 204,message = "Car Deleted"),
            @ApiResponse(code = 404,message = "Car not Deleted") })
    ResponseEntity<?> delete(@PathVariable Long id) {
        /**
         * TODO: Use the Car Service to delete the requested vehicle.
         */
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
