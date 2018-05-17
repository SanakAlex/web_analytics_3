import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.Date;
import java.util.TimerTask;

public class ScheduledTask extends TimerTask {

    Date now;

    public void run() {
//        String url = "https://spring.io/docs";
//        try {
//            Connection connection = Jsoup.connect(url);
//            connection.userAgent("Mozilla");
//            connection.timeout(5000);
//            connection.cookie("cookiename", "val234");
//            connection.cookie("cookiename", "val234");
//            connection.referrer("http://google.com");
//            connection.header("headersecurity", "xyz123");
//            Document docCustomConn = connection.get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String username = "google";    // update

// get tweets as JSON
        URL url = null;
        try {
            url = new URL("https://twitter.com/i/search/timeline?f=tweets&q=+from%3Aurbandictionary+since%3A2018-05-13+until%3A2018-05-14");

//            ByteArrayOutputStream urlOutputStream = new ByteArrayOutputStream();
//            IOUtils.copy(url.openStream(), urlOutputStream);
//            String urlContents = urlOutputStream.toString();

// parse JSON
//            System.out.println(urlContents);
//            JSONObject jsonObject = new JSONObject(urlContents);

// use

//            Document doc = Jsoup.parse(jsonObject.getString("items_html"));


//                System.out.println(jsonObject.getString("items_html"));
//                System.out.println(jsonObject.getString("text"));
//                System.out.println(jsonObject.getString("created_at"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
