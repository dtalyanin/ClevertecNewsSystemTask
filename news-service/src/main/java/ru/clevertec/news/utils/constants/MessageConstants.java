package ru.clevertec.news.utils.constants;

public class MessageConstants {
    public static final String MIN_ID_MESSAGE = "Min ID value is 1";
    public static final String INCORRECT_TOKEN = "Incorrect token structure";

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
