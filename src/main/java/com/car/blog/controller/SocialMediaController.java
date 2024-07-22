package com.car.blog.controller;

import com.car.blog.model.SocialMedia;
import com.car.blog.service.SocialMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/socialMedia")
public class SocialMediaController {

    @Autowired
    private SocialMediaService socialMediaService;

    @GetMapping
    public ResponseEntity<List<SocialMedia>> getAllSocialMedia() {
        List<SocialMedia> socialMediaList = socialMediaService.getSocialMedia();
        return ResponseEntity.ok(socialMediaList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocialMedia> getSocialMediaById(@PathVariable Integer id) {
        SocialMedia optionalSocialMedia = socialMediaService.getSocialMediaById(id);
        return new ResponseEntity<>(optionalSocialMedia, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SocialMedia> createSocialMedia(@RequestBody SocialMedia socialMedia) {
        SocialMedia createdSocialMedia = socialMediaService.CreateSocialMedia(socialMedia);
        return new ResponseEntity<>(createdSocialMedia, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SocialMedia> updateSocialMedia(@PathVariable Integer id, @RequestBody SocialMedia socialMedia) {
        SocialMedia optionalOldSocialMedia = socialMediaService.getSocialMediaById(id);
        if (optionalOldSocialMedia != null) {
            SocialMedia oldSocialMedia = optionalOldSocialMedia;
            oldSocialMedia.setSocialMediaLogo(socialMedia.getSocialMediaLogo());
            oldSocialMedia.setSocialMediaName(socialMedia.getSocialMediaName());
            oldSocialMedia.setSocialMediaLink(socialMedia.getSocialMediaLink());
            SocialMedia updatedSocialMedia = socialMediaService.CreateSocialMedia(oldSocialMedia); // Bu metodu yeniden adlandırmayı düşünün
            return ResponseEntity.ok(updatedSocialMedia);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSocialMedia(@PathVariable Integer id) {
        SocialMedia optionalSocialMedia = socialMediaService.getSocialMediaById(id);
        if (optionalSocialMedia != null) {
            socialMediaService.deletesocialMedia(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
