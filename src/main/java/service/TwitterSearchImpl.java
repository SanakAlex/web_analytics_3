package service;

import entity.Tweet;
import exception.InvalidQueryException;

import java.util.List;

public class TwitterSearchImpl extends TwitterSearch {

    @Override
    public boolean saveTweets(List<Tweet> tweets) {
        if(tweets!=null) {
            for (Tweet tweet : tweets) {
                String message = counter.getAndIncrement() + 1 + "[" + tweet.getCreatedAt() + "] "+tweet.getUserName()+" - " + tweet.getText();
                counterRetweets += tweet.getRetweets();
                counterFavourites += tweet.getFavourites();
                infoLog.info(message);
//                tweets.add(tweet);
                if (counter.get()==1000)
                    return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws InvalidQueryException {
        TwitterSearch twitterSearch = new TwitterSearchImpl();
        twitterSearch.search("Порошенко", 5);
        twitterSearch.search("Путин", 5);
        twitterSearch.search("sex", 5);
        twitterSearch.search("apple", 5);
        twitterSearch.search("KPI", 5);
        twitterSearch.search("God", 5);
    }
}
