package com.example.demo.service;

import com.example.demo.exceptions.ResourceNoContentException;
import com.example.demo.model.Car;
import com.example.demo.repository.CarRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TrafficService {

    private final CarRepository carRepository;
    private final int LENGTH = 10;

    public TrafficService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public long carsPassedBefore(LocalTime time) {
        return carRepository.findAll().stream()
                .filter(dataLine -> dataLine.getOutTime().isBefore(time))
                .count();
    }

    public long carsPassedInMinute(LocalTime time) {
        return carRepository.findAll().stream()
                .filter(dataLine -> dataLine.getInTime().isAfter(time))
                .filter(dataLine -> dataLine.getInTime().isBefore(time.plusMinutes(1)))
                .count();
    }

    public double carDensityPerMinute(LocalTime time) {
        long cars = carsPassedThroughSection(time);
        return (double) cars / LENGTH;
    }

    private long carsPassedThroughSection(LocalTime time) {
        return carRepository.findAll().stream()
                .filter(dataLine -> dataLine.getInTime().isBefore(time.plusMinutes(1)))
                .filter(dataLine -> dataLine.getInTime().isAfter(time))
                .count();
    }

    public String getFastestPlate() {
        return getFastestCar().getLicencePlate();
    }

    public Car getFastestCar() {
        return carRepository.findAll().stream()
                .max((d1, d2) -> d1.getSpeed(LENGTH) - d2.getSpeed(LENGTH))
                .orElse(null);
    }

    public int getFastedSpeed() {
        return getFastestCar().getSpeed(LENGTH);
    }

    public double getPercentToFast() {
        double percent = ((double) carRepository.findAll().stream().filter(dataLine -> dataLine.getSpeed(LENGTH) > 90)
                .count() / getTotalCars()) * 100;
        return BigDecimal.valueOf(percent).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public long getTotalCars() {
        return carRepository.count();
    }

    public List<Car> getAllCars(){
        return carRepository.findAll();
    }

    public long overTakes(String plate) {
        Car fastestCar = carRepository.findById(plate).orElseThrow(ResourceNoContentException::new);
        return carRepository.findAll().stream()
                .filter(dataLine -> dataLine.getInTime().isBefore(fastestCar.getInTime()))
                .filter(dataLine -> dataLine.getOutTime().isAfter(fastestCar.getOutTime()))
                .count();
    }

    public Car createCar(Car car) {
        return carRepository.save(car);
    }
}
