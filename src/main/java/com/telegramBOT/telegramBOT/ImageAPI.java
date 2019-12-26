package com.telegramBOT.telegramBOT;
;
import com.telegramBOT.telegramBOT.model.Image;
import com.telegramBOT.telegramBOT.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ImageAPI {
    @Autowired
    private ImageService imageService;
    private long countImage = 0;

    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
        countImage = imageService.count();
    }

    public Image getImage() {
        Random r = new Random();
        long id = r.nextInt((int)countImage)+1;
        return imageService.findById((long) id);
    }

    public void saveImage(String url) {
        imageService.createImage(new Image(url, ""));
    }
}
