package com.car.blog.controller;

import com.car.blog.model.Car;
import com.car.blog.model.SocialMedia;
import com.car.blog.model.Yorumlar;
import com.car.blog.service.CarService;
import com.car.blog.service.SocialMediaService;
import com.car.blog.service.YorumlarService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/yorumlar")
public class YorumlarController {

    private final CarService carService;
    private final SocialMediaService socialMediaService;
    private final YorumlarService yorumlarService;
    private List<Yorumlar> newYorumlar;

    @GetMapping
    public ResponseEntity<List<Yorumlar>> getCars() {
        return new ResponseEntity<>(yorumlarService.getYorumlar(), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Yorumlar> getYorumlar(@PathVariable Integer id) {
        return new ResponseEntity<>(yorumlarService.getYorumById(id), OK);
    }

    @PostMapping
    public ResponseEntity<Yorumlar> createCar(@RequestBody Yorumlar newYorumlar) {
        return new ResponseEntity<>(yorumlarService.CreateYorumlar(newYorumlar), CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Yorumlar> updateCar(@PathVariable Integer id, @RequestBody Yorumlar newYorumlar) {
        Yorumlar oldYorum = yorumlarService.getYorumById(id);
        if (oldYorum != null) {
           oldYorum.setYorumlarIsim(newYorumlar.getYorumlarIsim());
            oldYorum.setYorumlarYorum(newYorumlar.getYorumlarYorum());
            oldYorum.setYorumlarEmail(newYorumlar.getYorumlarYorum());


            // Yorumlar'ın null değerini kontrol et
            if (newYorumlar.getCar() != null) {
                oldYorum.setCar(newYorumlar.getCar());
                Car car = carService.getCarByID(newYorumlar.getCar().getId());
                car.getYorumlar().add(newYorumlar);
                carService.createCar(car);
            }else
                return null;
            }

            return new ResponseEntity<>(oldYorum, HttpStatus.OK);
        }

    @DeleteMapping("/{id}")
    public String deleteYorum(@PathVariable Integer id) {
        return "yorumlarService()";
    }

    private Car getCarById(Integer id) {
        return carService.getCarByID(id);
    }

    public CarService getCarService() {
        return carService;
    }

    }


