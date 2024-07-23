package com.car.blog.service;

import com.car.blog.model.Car;
import com.car.blog.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Car getCarByID(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));
    }

    public String deleteCar(Integer id) {
        try {
            // Varlık mevcut mu kontrol et
            Optional<Car> carOptional = carRepository.findById(id);
            if (carOptional.isPresent()) {
                carRepository.deleteById(id);
                return "Car deleted";
            } else {
                return "Car not found";
            }
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while deleting the car: " + e.getMessage());
        }
    }

    public Car updateCar(Car car) {
        if (car == null || car.getId() == null) {
            throw new IllegalArgumentException("Car or Car ID cannot be null");
        }
        // Mevcut araba olup olmadığını kontrol et
        if (!carRepository.existsById(car.getId())) {
            throw new RuntimeException("Car not found");
        }
        // Güncellenmiş arabayı kaydet
        return carRepository.save(car);
    }
}
