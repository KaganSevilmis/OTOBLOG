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

    @ElementCollection
    @CollectionTable(name = "car_titles", joinColumns = @JoinColumn(name = "car_id"))
    @Column(name = "title", length = 65535) // Uzunluk ihtiyaca göre ayarlanabilir
    private List<String> başlıklar;

    @ElementCollection
    @CollectionTable(name = "car_images", joinColumns = @JoinColumn(name = "car_id"))
    @Column(name = "image_url" , length = 65535)
    private List<String> görseller; // Görseller URL'leri bir liste olarak saklanacak

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<SocialMedia> socialMediaList;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Yorumlar> yorumlar;
}
