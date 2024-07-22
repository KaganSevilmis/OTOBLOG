package com.car.blog.service;

import com.car.blog.model.Car;
import com.car.blog.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public Car createCar(Car newCar) {
        return carRepository.save(newCar);
    }

    public void deleteIl(Integer id) {
        carRepository.deleteById(id);
    }

    public Car getCarByID(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));
    }

    public String deleteCar(Integer id) {
        try {
            carRepository.deleteById(id);
            return "Car deleted";
        } catch (Exception e) {
            return "Error occurred while deleting the car";
        }
    }
}
