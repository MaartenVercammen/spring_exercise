package com.example.demo.controller;

import com.example.demo.model.Car;
import com.example.demo.service.TrafficService;
import java.time.LocalTime;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/traffic")
public class TrafficController {

    private final TrafficService trafficService;

    public TrafficController(TrafficService trafficService) {
        this.trafficService = trafficService;
    }

    @GetMapping("/CarsPassedBefore")
    public long carsPassedBefore(@RequestParam int hour, @RequestParam int minute){
        LocalTime time = LocalTime.of(hour, minute);
        return trafficService.carsPassedBefore(time);
    }

    @GetMapping("/CarsPassedInMinute")
    public long carsPassedInMinute(@RequestParam int hour, @RequestParam int minute){
        LocalTime time = LocalTime.of(hour, minute);
        return trafficService.carsPassedInMinute(time);
    }

    @GetMapping("/CarDensityPerMinute")
    public double carDensityPerMinute(@RequestParam int hour, @RequestParam int minute){
        LocalTime time = LocalTime.of(hour, minute);
        return trafficService.carDensityPerMinute(time);
    }

    @GetMapping("/fastestCar")
    public Car carsPassedThroughSection(){
        return trafficService.getFastestCar();
    }

    @GetMapping("/")
    public List<Car> getAll(){
        return trafficService.getAllCars();
    }


    @GetMapping("/overtakes/{plate}")
    public long getOvertakesByCar(@PathVariable String plate){
        return trafficService.overTakes(plate);
    }

    @PostMapping
    public Car addCar(@RequestBody Car car){
        return trafficService.createCar(car);
    }


}
