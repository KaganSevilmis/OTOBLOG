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
@RequestMapping("/socialmedia")
public class SocialMediaController {

    private final CarService carService;
    private final SocialMediaService socialMediaService;
    private final YorumlarService yorumlarService;


    @GetMapping
    public ResponseEntity<List<SocialMedia>> getCars() {
        return new ResponseEntity<>(socialMediaService.getSocialMedia(), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCar(@PathVariable Integer id) {
        return new ResponseEntity<>(getCarById(id), OK);


    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car newCar) {
        return new ResponseEntity<>(carService.createCar(newCar), CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Integer id, @RequestBody Car newCar) {
        Car oldCar = carService.getCarByID(id);
        if (oldCar != null) {
            oldCar.setIçerik(newCar.getIçerik());
            oldCar.setCreateDate(newCar.getCreateDate());
            oldCar.setBaşlıklar(newCar.getBaşlıklar());
            oldCar.setGörseller(newCar.getGörseller());

            // Control for null value in socialMedia
            if (newCar.getSocialMedia() != null) {

                  SocialMedia socialMedia = socialMediaService.getSocialMediaById(id);
                  socialMedia.setSocialMediaLogo(newCar.getSocialMedia().getSocialMediaLogo());
                  socialMedia.setSocialMediaName(newCar.getSocialMedia().getSocialMediaName());
                  socialMedia.setSocialMediaLink(newCar.getSocialMedia().getSocialMediaLink());
                oldCar.setSocialMedia(newCar.getSocialMedia());
            }
            if (newCar.getYorumlar() != null) {
                List<Yorumlar> updatedYorumlarList = new ArrayList<>();
                for (Yorumlar newYorum : newCar.getYorumlar()) {
                    Yorumlar yorumlar;
                    if (newYorum.getId() != null) {
                        yorumlar = yorumlarService.getYorumById(newYorum.getId());
                    } else {
                        yorumlar = new Yorumlar();
                    }
                    yorumlar.setYorumlarYorum(newYorum.getYorumlarYorum());
                    yorumlar.setYorumlarIsim(newYorum.getYorumlarIsim());
                    yorumlar.setYorumlarEmail(newYorum.getYorumlarEmail());
                    yorumlarService.CreateYorumlar(yorumlar);
                    updatedYorumlarList.add(yorumlar);
                }
                oldCar.setYorumlar(updatedYorumlarList);
            }

            oldCar.setYorumlar(newCar.getYorumlar());
            carService.createCar(oldCar);
            return new ResponseEntity<>(oldCar, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/{id}")
    public String deleteCar(@PathVariable Integer id) {
        return carService.deleteCar(id);

    }


    private Car getCarById(Integer id){
        return carService.getCarByID(id);

    }

    public CarService getCarService() {
        return carService;
    }
}



