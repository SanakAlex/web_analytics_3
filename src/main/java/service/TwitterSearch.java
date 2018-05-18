package service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import entity.Tweet;
import entity.TwitterResponse;
import exception.InvalidQueryException;
import com.google.gson.Gson;
import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class TwitterSearch {

    final static Logger infoLog = Logger.getLogger("infoLogger");
    private final static Logger reportsLog = Logger.getLogger("reportsLoger");
    final AtomicInteger counter = new AtomicInteger();
    Double counterRetweets = 0d;
    Double counterFavourites = 0d;

    public TwitterSearch() {

    }

    public abstract boolean saveTweets(List<Tweet> tweets);

    public void search(final String query, final long rateDelay) throws InvalidQueryException {
        TwitterResponse response;
        URL url = constructURL(query, null);
        boolean continueSearch = true;
        String minTweet = null;
        while ((response = executeSearch(url)) != null && continueSearch && !response.getTweets().isEmpty()) {
            if (minTweet == null) {
                minTweet = response.getTweets().get(0).getId();
            }
            continueSearch = saveTweets(response.getTweets());
            String maxTweet = response.getTweets().get(response.getTweets().size() - 1).getId();
            if (!minTweet.equals(maxTweet)) {
                try {
                    Thread.sleep(rateDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String maxPosition = "TWEET-" + maxTweet + "-" + minTweet;
                url = constructURL(query, maxPosition);
            } else {
                continueSearch = false;
                double avgRetweets = counterRetweets / counter.get();
                double avgFavourites = counterFavourites / counter.get();
                String message = "Statistics for search query '" + query + "' with tweets count: " + counter.get()
                        + "\nAverage retweets: " + avgRetweets
                        + "\nAverage favourites: " + avgFavourites;
                reportsLog.info(message);
                counter.set(0);
                counterRetweets = 0d;
                counterFavourites = 0d;
            }
        }

        if (!continueSearch) {
            double avgRetweets = counterRetweets / counter.get();
            double avgFavourites = counterFavourites / counter.get();
            String message = "Statistics for search query '" + query + "' with tweets count: " + counter.get()
                    + "\nAverage retweets: " + avgRetweets
                    + "\nAverage favourites: " + avgFavourites;
            reportsLog.info(message);
            counter.set(0);
            counterRetweets = 0d;
            counterFavourites = 0d;

        }
    }

    public static TwitterResponse executeSearch(final URL url) {
        try {
            HttpResponse<JsonNode> request = Unirest.get(url.toString()).asJson();
            JSONObject jsonObject = request.getBody().getObject();

            Gson gson = new Gson();
            return gson.fromJson(jsonObject.toString(), TwitterResponse.class);
        } catch (UnirestException e) {
            // If we get an IOException, sleep for 5 seconds and retry.
            System.err.println("Could not connect to Twitter. Retrying in 5 seconds.");
            try {
                Thread.sleep(5000);
                return executeSearch(url);
            } catch (InterruptedException e2) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public final static String TYPE_PARAM = "f";
    public final static String QUERY_PARAM = "q";
    public final static String SCROLL_CURSOR_PARAM = "max_position";
    public final static String TWITTER_SEARCH_URL = "https://twitter.com/i/search/timeline";

    public static URL constructURL(final String query, final String maxPosition) throws InvalidQueryException {
        if (query == null || query.isEmpty()) {
            throw new InvalidQueryException(query);
        }
        try {
            URIBuilder uriBuilder;
            uriBuilder = new URIBuilder(TWITTER_SEARCH_URL);
            uriBuilder.addParameter(QUERY_PARAM, query);
            uriBuilder.addParameter(TYPE_PARAM, "tweets");
            if (maxPosition != null) {
                uriBuilder.addParameter(SCROLL_CURSOR_PARAM, maxPosition);
            }
            return uriBuilder.build().toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
            throw new InvalidQueryException(query);
        }
    }
}
