package com.example.demo.repository;

import com.example.demo.model.Car;
import org.springframework.data.repository.ListCrudRepository;

public interface CarRepository extends ListCrudRepository<Car, String> {

}
