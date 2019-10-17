import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;

public class Bot extends TelegramLongPollingBot {

    static void main() {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        if (message.contains("/weather")) {
            String city = message.split(" ")[1];
            String appid = "&APPID=";
            String s = "http://api.openweathermap.org/data/2.5/weather?q=" + city + appid;
            try {
                sendMessage.setText(Weather_Class.getWeather(s));
            } catch (IOException e) {
               e.printStackTrace();
            }
        } else {
            sendMessage.setText(message);
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return "BotHelper";
    }

    public String getBotToken() {
        return "token";
    }
}
