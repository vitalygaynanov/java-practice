import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;

public class Test_Weather {
    @Test
    public void testWeatherAPI() throws IOException {
        Weather_Class weather = new Weather_Class();
        String expected = "weather is light intensity drizzle and temperature is 6";
        String actual = weather.getWeather("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSiteParser() {
        Weather_Class weather = new Weather_Class();
        String[] expected = new String[] {"There is null site"};
        String[] actual = new String[expected.length];
        String[] args = new String[] {null, "123.com"};
        try {
            for (int i = 0; i < expected.length; i++) {
                for (String element : args) {
                    actual[i] = weather.getWeather(element);
                }
            }
        } catch (Exception e) {
            for (String element : actual) {
                Assert.assertEquals(expected[0], element);
            }
        }
    }

    @Test
    public void testWeatherParser() {
        Weather_Class weather = new Weather_Class();
        String expected = "def";
        String[] args = new String[] {"abc:\"def\"", "abc:def", "abc:\"def", "abc:def\""};
        String[] actual = new String[4];
        for (int i = 0; i < args.length; i++) {
            actual[i] = weather.parser(args[i]);
        }
        for (String element : actual) {
            Assert.assertEquals(expected, element);
        }
        Assert.assertEquals("There is bad data", weather.parser(""));
    }

    @Test
    public void testWeatherHandler() {
        Weather_Class weather = new Weather_Class();
        String[] expected = new String[] {"There is no text", "There is bad data"};
        String[] args = new String[] {"", "foo"};
        String[] actual = new String[expected.length];
        for (int i = 0; i < expected.length; i++) {
            actual[i] = weather.weatherHandler(args[i]);
        }
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], actual[i]);
        }
    }
}