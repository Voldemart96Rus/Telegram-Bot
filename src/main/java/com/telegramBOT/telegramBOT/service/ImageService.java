package com.telegramBOT.telegramBOT.service;

import com.telegramBOT.telegramBOT.model.Image;
import com.telegramBOT.telegramBOT.model.Weather;
import com.telegramBOT.telegramBOT.repository.ImageRepository;
import com.telegramBOT.telegramBOT.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    @Autowired
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<Image> findAll(){
        return imageRepository.findAll();
    }

    public Image findById(Long id){
        return imageRepository.findById(id);
    }

    public void createImage(Image image) {
        imageRepository.save(image);
    }

    public long count() {
        return imageRepository.count();
    }

}
