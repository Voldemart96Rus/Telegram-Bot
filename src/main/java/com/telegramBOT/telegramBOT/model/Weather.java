package com.telegramBOT.telegramBOT.model;

import javax.persistence.*;

@Entity
@Table(name = "Weather")
public class  Weather {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String city;

    @Column
    private String temperature;

    @Column
    private String humidity;

    @Column
    private String description;

    public Weather() {
    }

    public Weather(String city, String temp, String humidity, String description) {
        this.city=city;
        this.temperature = temp;
        this.humidity = humidity;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getDescription() {
        return description;
    }

    public void saveTDH(String t, String h, String d) {
        description = d;
        temperature = t;
        humidity = h;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Weather that = (Weather) obj;
        if (!city.equals(that.city)) return false;

        return true;
    }

    @Override
    public String toString() {
        return "City: " + city + "\n" +
                "Temperature: " + temperature + "\n" +
                "Humidity: " + humidity  + "\n" +
                "Description: " + description + "\n";
    }
}