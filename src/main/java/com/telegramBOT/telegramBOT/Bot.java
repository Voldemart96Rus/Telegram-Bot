package com.telegramBOT.telegramBOT;

import com.telegramBOT.telegramBOT.model.Image;
import com.telegramBOT.telegramBOT.service.FootballTeamsService;
import com.telegramBOT.telegramBOT.service.ImageService;
import com.telegramBOT.telegramBOT.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class Bot extends TelegramLongPollingBot {
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private FootballTeamsService footballTeamsService;

    private WeatherAPI weatherAPI = new WeatherAPI();
    private ImageAPI imageAPI = new ImageAPI();
    private SportAPI sportAPI = new SportAPI();

    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMsgFootball(Message message, ArrayList<String> arr) {
        for (String el:arr) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            sendMessage.setChatId(message.getChatId().toString());
            sendMessage.setText(el);
            try {
                setButtons(sendMessage);
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMsgImg(Message message, Image img) {
        SendPhoto sendImage = new SendPhoto();

        sendImage.setChatId(message.getChatId().toString());
        sendImage.setPhoto(img.getImage());
        sendImage.setCaption(img.getDescription());
        try {
            execute(sendImage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @EventListener(ApplicationReadyEvent.class)
    private void initAPI() throws IOException {
        weatherAPI.setWeatherService(weatherService);
        weatherAPI.updateListCity();
        imageAPI.setImageService(imageService);
        sportAPI.setSportService(footballTeamsService);
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (message != null && message.hasText()) {
            String msg = message.getText().toLowerCase();
            Pattern weather = Pattern.compile("^/погода .*");
            if(weather.matcher(msg).find()) {
                try {
                    sendMsg(message, weatherAPI.getWeather(msg.replace("/погода ", "")).toString());
                    return;
                } catch (IOException e) {
                    sendMsg(message, "Город не найден!");
                    return;
                }
            }
            Pattern sport = Pattern.compile("^/футбол .*");
            if(sport.matcher(msg).find()) {
                try {
                    sendMsgFootball(message, sportAPI.getEventsByCountryName(msg.replace("/футбол ", "")));
                    return;
                } catch (Exception e) {
                    sendMsg(message, "Страна не найдена! Или проверьте коректность данных");
                    return;
                }
            }

            switch (msg) {
                case "/help":
                    StringBuffer echoHelp = new StringBuffer();
                    echoHelp.append("Команды бата:\n/погода {город} - показывает погоду в городе\n\n");
                    echoHelp.append("/футбол {cтрана} {дата (также и период можно указать, указывать через пробел Russia ГГГГ-ММ-ДД ГГГГ-ММ-ДД)} - показывает статистику по матчу, дату указывать в формате ГГГГ-ММ-ДД\n\n");
                    echoHelp.append("/image - показывае рандомную картинку =)\n");
                    sendMsg(message, echoHelp.toString());
                    break;
                case "/image":
                    sendMsgImg(message, imageAPI.getImage());
                    break;
                default:

            }
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("/help"));
        keyboardFirstRow.add(new KeyboardButton("/image"));

        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

    }

    public String getBotUsername() {
        return "TestTelegramN1bot";
    }

    public String getBotToken() {
        return "1000098726:AAFcLv7xEGy3ADZjKyuMXYBzxM1Ha8ti0qk";
    }
}
