package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TwitterResponse {

    private boolean has_more_items;
    private String items_html;
    private String min_position;
    private String refresh_cursor;
    private long focused_refresh_interval;

    public List<Tweet> getTweets() {
        final List<Tweet> tweets = new ArrayList<>();
        Document doc = Jsoup.parse(items_html);
        for(Element el : doc.select("li.js-stream-item")) {
            String id = el.attr("data-item-id");
            String text = null;
            String userId = null;
            String userScreenName = null;
            String userName = null;
            Date createdAt = null;
            int retweets = 0;
            int favourites = 0;
//            String text = el.select("p.tweet-text").text();
//            String userId = el.select("div.tweet").attr("data-user-id");
//            String userScreenName = el.select("div.tweet").attr("data-name");
//            String userName = el.select("div.tweet").attr("data-screen-name");
//            final String date = el.select("span._timestamp").attr("data-time-ms");
//            Date createdAt = new Date(Long.parseLong(date));
//            int retweets = Integer.parseInt(el.select("span.ProfileTweet-action--retweet &gt; span.ProfileTweet-actionCount")
//                    .attr("data-tweet-stat-count"));
//            int favourites = Integer.parseInt(el.select("span.ProfileTweet-action--favorite &gt; span.ProfileTweet-actionCount")
//                    .attr("data-tweet-stat-count"));
            try {
                text = el.select("p.tweet-text").text();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            try {
                userId = el.select("div.tweet").attr("data-user-id");
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            try {
                userName = el.select("div.tweet").attr("data-name");
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            try {
                userScreenName = el.select("div.tweet").attr("data-screen-name");
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            try {
                final String date = el.select("span._timestamp").attr("data-time-ms");
                if (date != null && !date.isEmpty()) {
                    createdAt = new Date(Long.parseLong(date));
                }
            } catch (NullPointerException | NumberFormatException e) {
                e.printStackTrace();
            }
            try {
                retweets = Integer.parseInt(el.select("span.ProfileTweet-action--retweet > span.ProfileTweet-actionCount")
                        .attr("data-tweet-stat-count"));
            } catch(NullPointerException e) {
                e.printStackTrace();
            }
            try {
                favourites = Integer.parseInt(el.select("span.ProfileTweet-action--favorite > span.ProfileTweet-actionCount")
                        .attr("data-tweet-stat-count"));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            Tweet tweet = new Tweet(
                    id,
                    text,
                    userId,
                    userName,
                    userScreenName,
                    createdAt,
                    retweets,
                    favourites
            );
            if (tweet.getId() != null) {
                tweets.add(tweet);
            }
        }
        return tweets;
    }

}
