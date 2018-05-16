import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Date;
import java.util.TimerTask;

public class ScheduledTask extends TimerTask {

    Date now;

    public void run() {
        String url = "https://spring.io/docs";
        try {
            Connection connection = Jsoup.connect(url);
            connection.userAgent("Mozilla");
            connection.timeout(5000);
            connection.cookie("cookiename", "val234");
            connection.cookie("cookiename", "val234");
            connection.referrer("http://google.com");
            connection.header("headersecurity", "xyz123");
            Document docCustomConn = connection.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
