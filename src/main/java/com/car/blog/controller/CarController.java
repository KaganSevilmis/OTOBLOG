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
    public ResponseEntity<Car> updateCar(@PathVariable Integer id, @RequestBody Car newCar) {
        Car oldCar = carService.getCarByID(id);
        if (oldCar != null) {
            oldCar.setIçerik(newCar.getIçerik());
            oldCar.setCreateDate(newCar.getCreateDate());
            oldCar.setBaşlıklar(newCar.getBaşlıklar());

            // Görselleri güncelle
            if (newCar.getGörseller() != null) {
                oldCar.setGörseller(newCar.getGörseller());
            }
                  // Sosyal medyayı güncelle
            if (!oldCar.getSocialMediaList().isEmpty()) {
                List<SocialMedia> updatedSocialMediaList = new ArrayList<>();
                for (SocialMedia newSocialMedia : newCar.getSocialMediaList()) {
                    SocialMedia socialMedia;
                    if (newSocialMedia.getId() != null) {
                        socialMedia = socialMediaService.getSocialMediaById(newSocialMedia.getId());
                    } else {
                        socialMedia = new SocialMedia();
                    }
                    socialMedia.setSocialMediaLogo(newSocialMedia.getSocialMediaLogo());
                    socialMedia.setSocialMediaName(newSocialMedia.getSocialMediaName());
                    socialMedia.setSocialMediaLink(newSocialMedia.getSocialMediaLink());
                    socialMediaService.CreateSocialMedia(socialMedia);
                    updatedSocialMediaList.add(socialMedia);
                }
                oldCar.setSocialMediaList(updatedSocialMediaList);
            } else {
                List<SocialMedia> updatedSocialMediaList = new ArrayList<>();
                for (SocialMedia newSocialMedia : newCar.getSocialMediaList()) {
                    SocialMedia socialMedia = new SocialMedia();

                    socialMedia.setSocialMediaLogo(newSocialMedia.getSocialMediaLogo());
                    socialMedia.setSocialMediaName(newSocialMedia.getSocialMediaName());
                    socialMedia.setSocialMediaLink(newSocialMedia.getSocialMediaLink());
                    socialMedia.setCar(oldCar);
                    socialMediaService.CreateSocialMedia(socialMedia);
                    updatedSocialMediaList.add(socialMedia);
                }
                oldCar.setSocialMediaList(updatedSocialMediaList);
            }
            // Yorumları güncelle
            if (!oldCar.getYorumlar().isEmpty()) {
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
            }else {
                List<Yorumlar> updatedYorumlarList = new ArrayList<>();
                for (Yorumlar newYorum : newCar.getYorumlar()) {
                    Yorumlar yorumlar = new Yorumlar();

                    yorumlar.setYorumlarYorum(newYorum.getYorumlarYorum());
                    yorumlar.setYorumlarIsim(newYorum.getYorumlarIsim());
                    yorumlar.setYorumlarEmail(newYorum.getYorumlarEmail());
                    yorumlar.setCar(oldCar);
                    yorumlarService.CreateYorumlar(yorumlar);
                    updatedYorumlarList.add(yorumlar);
                }
                oldCar.setYorumlar(updatedYorumlarList);
            }

            Car updatedCar = carService.createCar(oldCar);
            return new ResponseEntity<>(updatedCar, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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