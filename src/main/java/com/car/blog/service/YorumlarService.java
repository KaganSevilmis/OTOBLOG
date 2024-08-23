package com.car.blog.service;

import com.car.blog.model.Yorumlar;
import com.car.blog.repository.YorumlarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class YorumlarService {

    private final YorumlarRepository yorumlarRepository;

    public List<Yorumlar> getYorumlar() {
        return yorumlarRepository.findAll();
    }

    public Yorumlar createYorumlar(Yorumlar newYorumlar) {
        return yorumlarRepository.save(newYorumlar);
    }

    public void deleteIl(Integer id) {
        yorumlarRepository.deleteById(id);
    }

    public Yorumlar getYorumById(Integer id) {
        return yorumlarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Yorum not found"));
    }

    public String deleteYorum(Integer id) {
        try {
            yorumlarRepository.deleteById(id);
            return "Yorum deleted";
        } catch (Exception e) {
            return "Failed to delete yorum: " + e.getMessage();
        }
    }
}
