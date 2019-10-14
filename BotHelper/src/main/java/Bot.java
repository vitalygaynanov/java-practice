import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Bot extends TelegramLongPollingBot {

    public static void main(String[] args) {
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
                sendMessage.setText(getWeather(s));
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

    private String getWeather(String site) throws IOException {
        URL url = null;
        try {
            url = new URL(site);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BufferedReader in = null;
        try {
            assert url != null;
            in = new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String input;
        StringBuilder html = new StringBuilder();
        assert in != null;
        while((input = in.readLine()) != null) {
            html.append(input);
        }
        return weatherHandler(html.toString());
    }

    private String weatherHandler(String text) {
        String[] data = text.split(",");
        String weather = "";
        String temperature = "";
        for (String datum : data) {
            if (datum.contains("description")) {
                weather = datum;
            } else if (datum.contains("temp_min")) {
                temperature = datum;
            }
        }
        weather = parser(weather);
        temperature = parser(temperature);
        int temp = (int)Math.round(Double.parseDouble(temperature) - 273.15);
        return "weather is " + weather + " and temperature is " + temp;
    }

    private String parser(String s) {
        String[] data = s.split(":");
        String result;
        if (data[1].contains("\"")) {
            result = data[1].replace("\"", "");
        } else {
            result = data[1];
        }
        return result;
    }
}
