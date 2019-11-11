import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class Bot extends TelegramLongPollingBot {

    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(messageParser(message));
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static String messageParser (String message){
        if (message.contains("/weather")) {
            Weather weather = new Weather();
            String city = message.split(" ")[1];
            String appid = "&APPID=" + Main.parser("appid");
            String s = "http://api.openweathermap.org/data/2.5/weather?q=" + city + appid;
            try {
                return weather.getWeather(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (message.equals("/help")) {
            return "It's help"; // write help message
        } else if (message.equals("/start")) {
		return "It's start"; // write start message
	}
        return message;
    }

    public String getBotUsername() {
        return "BotHelper";
    }

    public String getBotToken() {
        return Main.parser("token");
    }
}
