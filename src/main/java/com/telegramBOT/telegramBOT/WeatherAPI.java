package com.telegramBOT.telegramBOT;

import com.telegramBOT.telegramBOT.model.Weather;
import com.telegramBOT.telegramBOT.service.WeatherService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

@Component
public class WeatherAPI {
    @Autowired
    private WeatherService weatherService;
    private  ArrayList<String> listCity = new ArrayList<>();

    public void setWeatherService(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public String editLetter(String city) {
        String correctCaseCityName = "";
        correctCaseCityName = correctCaseCityName + city.substring(0, 1).toUpperCase();
        for (int i = 1; i < city.length(); i++) {
            if (" ".equals(city.substring(i-1, i)) || "-".equals(city.substring(i-1, i)))
                correctCaseCityName = correctCaseCityName + city.substring(i, i+1).toUpperCase();
            else
                correctCaseCityName = correctCaseCityName + city.substring(i, i+1);
        }
        return correctCaseCityName;
    }

    public Weather getWeather(String city) throws IOException {
        city = editLetter(city);
        if (listCity.contains(city)) {
            return weatherService.findByCity(city);
        }
        else {
            Weather w = new Weather();
            w.setCity(city);
            return updateInformationCity(w);
        }
    }

    private Weather updateInformationCity(Weather w) throws IOException {

        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + w.getCity() + "&units=metric&appid=6fff53a641b9b9a799cfd6b079f5cd4e");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        JSONObject main = object.getJSONObject("main");

        String temp = main.getDouble("temp") + " C";
        String humidity = main.getDouble("humidity") + "%";
        String description = (String) object.getJSONArray("weather").getJSONObject(0).get("main");

        w.saveTDH(temp, humidity, description);
        weatherService.createWeather(w);
        listCity.add(w.getCity());

        return w;
    }

    public void updateListCity() {
        this.weatherService.findAll().forEach(it-> {
            listCity.add(it.getCity());
            try {
                Weather w = updateInformationCity(it);
                weatherService.createWeather(w);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
