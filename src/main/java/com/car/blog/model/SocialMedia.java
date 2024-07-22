package com.car.blog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "social_media")
public class SocialMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 65535)
    private String socialMediaLogo;
    @Column(length = 65535)
    private String socialMediaName;
    @Column(length = 65535)
    private String socialMediaLink;

    @ManyToOne
    @JoinColumn(name = "car_id")
    @JsonBackReference
    private Car car;
}
