package ru.clevertec.news.utils.constants;

public class MessageConstants {
    public static final String MIN_ID_MESSAGE = "Min ID value is 1";
    public static final String INCORRECT_TOKEN = "Incorrect token structure";
    public static final String EMPTY_NEWS_TITLE = "News title cannot be empty";
    public static final String TOO_LONG_NEWS_TITLE = "Max length of title is 200 characters";
    public static final String EMPTY_NEWS_TEXT = "News body cannot be empty";
    public static final String NULL_NEWS_USERNAME = "News must contain username";
    public static final String TOO_LONG_NEWS_USERNAME = "Max length of username is 50 characters";
    public static final String EMPTY_COMMENT_TEXT = "Comment cannot be empty";
    public static final String NULL_COMMENT_USERNAME = "Comment must contain username";
    public static final String NO_NEWS_FOR_COMMENT = "Comment must contain news ID";

    public static final String NEWS_ADDED = "News added successfully";
    public static final String NEWS_UPDATED = "News updated successfully";
    public static final String NEWS_DELETED = "News deleted successfully";

    public static final String COMMENT_ADDED = "Comment added successfully";
    public static final String COMMENT_UPDATED = "Comment updated successfully";
    public static final String COMMENT_DELETED = "Comment deleted successfully";

    public static final String NEWS_WITH_ID_NOT_FOUND = "News with ID not found in DB";
    public static final String COMMENT_NOT_FOUND = "Comment with ID not found in DB";
    public static final String NOT_PERMISSIONS = "User hasn't permissions";
    public static final String NOT_NEWS_OWNER = "User not owner of this news";
    public static final String NOT_COMMENT_OWNER = "User not owner of this comment";

    public static final String CANNOT_END = ": cannot ";
    public static final String WRONG_CACHE_CHOICE = "Wrong cache implementation (possible values are LFU and LRU)";
    public static final String WRONG_CACHE_CAPACITY = "Capacity for cache should be more than 0";

    public static final String FIELD_NOT_PRESENT = " is not present in ";
    public static final String CANNOT_GET_FIELD_VALUE = " is not accessible in ";
}
