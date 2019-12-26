package com.telegramBOT.telegramBOT.service;

import com.telegramBOT.telegramBOT.model.Weather;
import com.telegramBOT.telegramBOT.repository.WeatherRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class WeatherService {
    @Autowired
    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public List<Weather> findAll(){
        return weatherRepository.findAll();
    }

    public Weather findByCity(String name){
        return weatherRepository.findByCity(name);
    }

    public void createWeather(Weather weather) {
        weatherRepository.save(weather);
    }
}
