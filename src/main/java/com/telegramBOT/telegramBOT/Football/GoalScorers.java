package com.telegramBOT.telegramBOT.Football;

public class GoalScorers {
    public String score = "";
    public String time = "";
    public String scorer = "";

    public GoalScorers(String score, String time, String home_scorer, String away_scorer) {
        this.score =score;
        this.time = time;
        if(away_scorer.equals("")){
            this.scorer = home_scorer;
        } else {
            this.scorer =away_scorer;
        }
    }

    @Override
    public String toString() {
        return "Гол " + score + ", минута гола: " + time + ", " + scorer+"\n";
    }
}
