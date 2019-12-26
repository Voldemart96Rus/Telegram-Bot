package com.telegramBOT.telegramBOT.repository;

import com.telegramBOT.telegramBOT.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
    Image findById(Long id);
}
