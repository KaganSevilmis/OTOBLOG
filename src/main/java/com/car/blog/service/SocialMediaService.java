package com.car.blog.service;

import com.car.blog.model.SocialMedia;
import com.car.blog.repository.SocialMediaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SocialMediaService {

    private final SocialMediaRepository socialMediaRepository;

    public List<SocialMedia> getSocialMedia() {
        return socialMediaRepository.findAll();
    }

    public SocialMedia CreateSocialMedia(SocialMedia newSocialMedia) {
        return socialMediaRepository.save(newSocialMedia);
    }

    public void deleteIl(Integer id) {
        socialMediaRepository.deleteById(id);
    }

    public SocialMedia getSocialMediaById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return socialMediaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SocialMedia not found"));
    }
}
