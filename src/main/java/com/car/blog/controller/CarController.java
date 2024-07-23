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
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;
    private final SocialMediaService socialMediaService;
    private final YorumlarService yorumlarService;

    @GetMapping
    public ResponseEntity<List<Car>> getCars() {
        return new ResponseEntity<>(carService.getCars(), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCar(@PathVariable Integer id) {
        Car car = carService.getCarByID(id);
        if (car != null) {
            return new ResponseEntity<>(car, OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car newCar) {
        return new ResponseEntity<>(carService.createCar(newCar), CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCar(@PathVariable Integer id, @RequestBody Car newCar) {
        try {
            Car oldCar = carService.getCarByID(id);
            if (oldCar == null) {
                return new ResponseEntity<>("Car not found", HttpStatus.NOT_FOUND);
            }

            // Güncellemeleri yap
            oldCar.setIçerik(newCar.getIçerik());
            oldCar.setCreateDate(newCar.getCreateDate());

            // Başlıklar listesini güncelle
            if (newCar.getBaşlıklar() != null) {
                oldCar.setBaşlıklar(newCar.getBaşlıklar());
            }
            // Görselleri güncelle
            if (newCar.getGörseller() != null) {
                oldCar.setGörseller(newCar.getGörseller());
            }

            // Sosyal medya güncellemeleri
            List<SocialMedia> updatedSocialMediaList = new ArrayList<>();
            if (newCar.getSocialMediaList() != null) {
                for (SocialMedia newSocialMedia : newCar.getSocialMediaList()) {
                    SocialMedia socialMedia = newSocialMedia.getId() != null ?
                            socialMediaService.getSocialMediaById(newSocialMedia.getId()) : new SocialMedia();

                    socialMedia.setSocialMediaLogo(newSocialMedia.getSocialMediaLogo());
                    socialMedia.setSocialMediaName(newSocialMedia.getSocialMediaName());
                    socialMedia.setSocialMediaLink(newSocialMedia.getSocialMediaLink());
                    socialMedia.setCar(oldCar);
                    updatedSocialMediaList.add(socialMedia);
                }
                oldCar.setSocialMediaList(updatedSocialMediaList);
            }

            // Yorumlar güncellemeleri
            List<Yorumlar> updatedYorumlarList = new ArrayList<>();
            if (newCar.getYorumlar() != null) {
                for (Yorumlar newYorum : newCar.getYorumlar()) {
                    Yorumlar yorumlar = newYorum.getId() != null ?
                            yorumlarService.getYorumById(newYorum.getId()) : new Yorumlar();

                    yorumlar.setYorumlarYorum(newYorum.getYorumlarYorum());
                    yorumlar.setYorumlarIsim(newYorum.getYorumlarIsim());
                    yorumlar.setYorumlarEmail(newYorum.getYorumlarEmail());
                    yorumlar.setCar(oldCar);
                    updatedYorumlarList.add(yorumlar);
                }
                oldCar.setYorumlar(updatedYorumlarList);
            }

            // Araba güncelle
            Car updatedCar = carService.updateCar(oldCar);
            return new ResponseEntity<>(updatedCar, HttpStatus.OK);
        } catch (Exception e) {
            // Detaylı hata mesajı döndür
            return new ResponseEntity<>("An error occurred while updating the car: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable Integer id) {
        String result = carService.deleteCar(id);
        if ("Car deleted".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Car getCarById(Integer id) {
        return carService.getCarByID(id);
    }

    public CarService getCarService() {
        return carService;
    }
}