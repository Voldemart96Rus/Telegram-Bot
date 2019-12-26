package com.telegramBOT.telegramBOT.Football;

public class Cards {
    public String card = "";
    public String time = "";
    public String fault = "";

    public Cards(String card, String time, String home_fault, String away_fault) {
        this.card =card;
        this.time = time;
        if(away_fault.equals("")){
            this.fault = home_fault;
        } else {
            this.fault =away_fault;
        }
    }

    @Override
    public String toString() {
        return "Время: " + time + ", " + card + ": " + fault + "\n";
    }
}
