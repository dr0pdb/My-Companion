package com.example.srv_twry.studentcompanion.Database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by srv_twry on 19/6/17.
 * The contract class for the SQlite database.
 */

public class DatabaseContract {

    // Constants for the database
    public static final String AUTHORITY= "com.example.srv_twry.studentcompanion";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_CONTESTS = "contests";
    public static final String PATH_SUBSCRIBED_CONTESTS = "subscribedContests";
    public static final String PATH_FLASH_CARDS_TOPICS = "flashCardTopics";


    public static final class ContestEntry implements BaseColumns{

        public static final Uri CONTENT_URI_CONTESTS = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CONTESTS).build();

        // Table name
        public static final String TABLE_NAME_CONTESTS = "contestsTable";

        //Table columns
        public static final String CONTEST_COLUMN_TITLE = "contestTitle";
        public static final String CONTEST_COLUMN_DESCRIPTION = "contestDescription";
        public static final String CONTEST_COLUMN_URL = "contestUrl";
        public static final String CONTEST_COLUMN_START_TIME = "startTime";
        public static final String CONTEST_COLUMN_END_TIME = "endTime";
    }

    public static final class SubscribedContestEntry implements BaseColumns{

        public static final Uri CONTENT_URI_SUBSCRIBED_CONTESTS = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SUBSCRIBED_CONTESTS).build();

        //Table name
        public static final String TABLE_NAME_SUBSCRIBED_CONTESTS = "subscribedContestsTable";

        //Table columns
        public static final String SUBSCRIBED_CONTEST_TITLE = "subscribedContestTitle";
        public static final String SUBSCRIBED_CONTEST_URL = "subscribedContestUrl";
        public static final String SUBSCRIBED_CONTEST_START_TIME = "subscribedStartTime";

    }

    public static final class FlashCardsTopicsEntry implements BaseColumns{

        public static final Uri CONTENT_URI_FLASH_CARDS_TOPICS = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FLASH_CARDS_TOPICS).build();

        //Table name
        public static final String TABLE_NAME_FLASH_CARDS_TOPICS = "flashCardsTopics";

        //Table columns
        public static final String FLASH_CARDS_TOPIC_NAME = "flashCardsTopicName";
        public static final String FLASH_CARDS_TOPIC_PRIORITY = "flashCardsTopicPriority";

    }
}
