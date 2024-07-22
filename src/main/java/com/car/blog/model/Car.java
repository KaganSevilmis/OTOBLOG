package com.car.blog.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Integer id;

    private Date createDate = new Date();

    @Column(length = 65535)
    private String içerik;

    @Column(length = 65535)
    private String başlıklar;

    @Column(length = 65535)
    private String görseller;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<SocialMedia> socialMediaList;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Yorumlar> yorumlar;
}
