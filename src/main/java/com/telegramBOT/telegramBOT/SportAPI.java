package com.telegramBOT.telegramBOT;

import com.telegramBOT.telegramBOT.Football.Match;
import com.telegramBOT.telegramBOT.model.FootballTeams;
import com.telegramBOT.telegramBOT.service.FootballTeamsService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

@Component
public class SportAPI {
    @Autowired
    private FootballTeamsService footballTeamsService;

    private String apiKey = "a045b4caa12af81e05fd07b9dc104752c4fd79f4bab32a5862474eccf3b126bd";

    public void setSportService(FootballTeamsService footballTeamsService) {
        this.footballTeamsService = footballTeamsService;
    }

    public void read() throws IOException {
        URL url = new URL("https://allsportsapi.com/api/football/?met=Countries&APIkey=" + apiKey);

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        JSONArray main = object.getJSONArray("result");
        for (Object el : main) {
            JSONObject temp = new JSONObject(el.toString());
            long country_key = temp.getLong("country_key");
            String country_name = temp.getString("country_name");
            FootballTeams f = new FootballTeams(country_key, country_name.toUpperCase());
            footballTeamsService.createFootballLeague(f);
        }
    }

    public ArrayList<String> getEventsByCountryName(String country) throws Exception {
        ArrayList<String> match = new ArrayList();
        String[] item = country.split(" ");
        Long id = footballTeamsService.findByName(item[0].toUpperCase()).getId();
        String date = getDate();
        URL url = null;
        switch (item.length) {
            case 1:
                url = new URL("https://allsportsapi.com/api/football/?met=Fixtures&APIkey="+ apiKey + "&from="+ date + "&to="+ date + "&countryId=" + id);
                break;
            case 2:
                checkDate(item[1]);
                url = new URL("https://allsportsapi.com/api/football/?met=Fixtures&APIkey="+ apiKey + "&from="+ item[1] + "&to="+ item[1] + "&countryId=" + id);
                break;
            case 3:
                checkDate(item[1]);
                checkDate(item[2]);
                url = new URL("https://allsportsapi.com/api/football/?met=Fixtures&APIkey="+ apiKey + "&from="+ item[1] + "&to="+ item[2] + "&countryId=" + id);
                break;
            default:
        }
        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        JSONArray main = object.getJSONArray("result");
        for (Object el : main) {
            JSONObject temp = new JSONObject(el.toString());
            Match t = new Match(temp);
            match.add(t.toString());
       }

        return match;
    }

    private void checkDate(String date) throws Exception {
        Pattern datePattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
        if(!datePattern.matcher(date).find()) {
            throw new Exception("Проверьте правильность данных");
        }

    }

    public String getDate() {
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
        return formatForDateNow.format(dateNow);
    }

}
