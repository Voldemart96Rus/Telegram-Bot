package com.telegramBOT.telegramBOT.Football;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Match {
    public ArrayList<String> goalscorers = new ArrayList();
    public ArrayList<String> cards = new ArrayList();
    public String event_date;
    public String event_final_result;
    public String event_stadium;
    public String country_name;
    public String event_away_team;
    public String event_home_team;
    public String event_status;
    public String league_round;
    public String away_team_logo;
    public String home_team_logo;
    public String league_name;
    public String league_season;

    public Match(JSONObject obj) {
        event_date = obj.getString("event_date");
        event_final_result = obj.getString("event_final_result");
        country_name = obj.getString("country_name");
        event_away_team = obj.getString("event_away_team");
        event_home_team = obj.getString("event_home_team");
        event_status = obj.getString("event_status");
        if (!event_status.equals("Finished")) event_status += " минута игры";
        else event_status = "Завершен";
        league_round = obj.getString("league_round");
        away_team_logo = obj.getString("away_team_logo");
        home_team_logo = obj.getString("home_team_logo");
        league_name = obj.getString("league_name");
        league_season = obj.getString("league_season");
        event_stadium = obj.getString("event_stadium");
        getGoalscorers(obj.getJSONArray("goalscorers"));
        getCards(obj.getJSONArray("cards"));
    }

    private void getGoalscorers(JSONArray arr) {
        for (Object el : arr) {
            JSONObject temp = new JSONObject(el.toString());
            String score = temp.getString("score");
            String time = temp.getString("time");
            String home_scorer = temp.getString("home_scorer");
            String away_scorer = temp.getString("away_scorer");
            goalscorers.add(new GoalScorers(score,time, home_scorer, away_scorer).toString());
       }
    }

    private void getCards(JSONArray arr) {
        for (Object el : arr) {
            JSONObject temp = new JSONObject(el.toString());
            String card = temp.getString("card");
            String time = temp.getString("time");
            String home_fault = temp.getString("home_fault");
            String away_fault = temp.getString("away_fault");
            cards.add(new Cards(card,time, home_fault, away_fault).toString());
        }
    }
    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append("Сезон лиги: " + league_season + "\nИмя лиги: " + league_name + "\n");
        str.append("Дата события: " + event_date + "\nCтрана: " + country_name + "\nРаунд " + league_round + "\nСтадион: " + event_stadium + "\n");
        str.append(event_home_team + " " + event_final_result + " " + event_away_team + "\n");
        str.append("Статус: " + event_status + "\n");

        if(goalscorers.size() != 0) {
            str.append("Голы: " + "\n");
            arrayToStr(str, goalscorers);
        }

        if (cards.size()!= 0) {
            str.append("Карточки: " + "\n");
            arrayToStr(str, cards);
        }

        return str.toString();
    }

    public void arrayToStr(StringBuffer str, ArrayList<String> arr) {
        for (String s :arr) {
            str.append(s);
        }
    }
}
