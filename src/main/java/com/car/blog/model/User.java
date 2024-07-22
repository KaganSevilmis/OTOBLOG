package com.car.blog.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

    @Data
    @Entity
    @Table(name = "users")
    public class User {

        @jakarta.persistence.Id
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "username")
        private String username;

        @Column(name = "email")
        private String email;

        public void setId(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        // getter ve setter'lar
    }


