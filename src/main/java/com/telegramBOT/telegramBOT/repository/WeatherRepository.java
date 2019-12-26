package com.telegramBOT.telegramBOT.repository;

import com.telegramBOT.telegramBOT.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, String> {
    Weather findByCity(String name);
}
