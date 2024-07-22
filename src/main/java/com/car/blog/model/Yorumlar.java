package com.car.blog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "yorumlar")
public class Yorumlar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 65535)
    private String yorumlarYorum;

    private String yorumlarIsim;

    private String yorumlarEmail;

    @ManyToOne
    @JoinColumn(name = "car_id")
    @JsonBackReference
    private Car car;
}
