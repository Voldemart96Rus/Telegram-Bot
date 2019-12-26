package com.telegramBOT.telegramBOT.model;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String image;

    @Column
    private String description;

    public Image() {
    }

    public Image(String image, String description) {
        this.image=image;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description+ " " +image;
    }
}