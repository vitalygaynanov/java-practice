import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.File;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public static String parser(String s) {
        ClassLoader classLoader = Main.class.getClassLoader();
        URL resource = classLoader.getResource(".properties");
        String file = "";
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            file = new File(resource.getFile()).toString();
        }
        String[] text = file.split("\n");
        String[] result = new String[2];
        if (s.equals("token")) {
            result = text[0].split(" ");
        } else if (s.equals("appid")) {
            result = text[1].split(" ");
        }
        return result[1];
    }
}