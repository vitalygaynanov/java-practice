import org.junit.Assert;
import org.junit.Test;

public class Test_Bot {
    @Test
    public void testGetBotUsername() {
        String expected = "BotHelper";
        Bot bot = new Bot();
        String actual = bot.getBotUsername();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetBotToken() {
        String expected = "token";
        Bot bot = new Bot();
        String actual = bot.getBotToken();
        Assert.assertEquals(expected, actual);
    }
}
