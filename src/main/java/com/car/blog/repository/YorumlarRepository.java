package com.car.blog.repository;

import com.car.blog.model.Car;
import com.car.blog.model.Yorumlar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YorumlarRepository extends JpaRepository<Yorumlar, Integer> {
}
