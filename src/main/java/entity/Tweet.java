package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tweet {

    private String id;
    private String text;
    private String userId;
    private String userName;
    private String userScreenName;
    private Date createdAt;
    private int retweets;
    private int favourites;

}
