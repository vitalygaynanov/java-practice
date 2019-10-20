import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Weather_Class {
    public static String getWeather(String site) throws IOException {
        URL url = null;
        try {
            url = new URL(site);
        } catch (MalformedURLException e) {
            return "There is null site";
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

    public static String weatherHandler(String text) {
        if (text.compareTo("") == 0) {
            return "There is no text";
        }
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
        int temp;
        try {
            temp = (int) Math.round(Double.parseDouble(temperature) - 273.15);
        } catch (Exception e) {
            return "There is bad data";
        }
        return "weather is " + weather + " and temperature is " + temp;
    }

    public static String parser(String s) {
        String[] data = s.split(":");
        String result;
        try {
            if (data[1].contains("\"")) {
                result = data[1].replace("\"", "");
            } else {
                result = data[1];
            }
        } catch (Exception e) {
            return "There is bad data";
        }
        return result;
    }
}