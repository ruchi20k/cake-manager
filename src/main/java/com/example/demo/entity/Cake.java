package com.example.demo.entity;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Cake {
    private final int MAX_NAME_LENGTH = 500;
    private final int MAX_DESCRIPTION_LENGTH = 500;
    private final int MAX_IMAGE_URL_LENGTH = 500;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = MAX_NAME_LENGTH, name = "title")
    private String title;

    @Column(length = MAX_DESCRIPTION_LENGTH, name = "desc")
    private String desc;

    @Column(length = MAX_IMAGE_URL_LENGTH, name = "image")
    private String image;
    
    
}
